package com.pragma.pagos.application;

import com.pragma.pagos.domain.exception.PagoInvalidoException;
import com.pragma.pagos.domain.exception.ServicioNoDisponibleException;
import com.pragma.pagos.domain.model.EstadoPago;
import com.pragma.pagos.domain.model.Pago;
import com.pragma.pagos.domain.service.ValidacionPagoService;
import com.pragma.pagos.domain.service.RecuperacionFalloService;
import com.pragma.pagos.infrastructure.client.MotorPagosClient;
import com.pragma.pagos.infrastructure.client.SistemaAutenticacionClient;
import com.pragma.pagos.infrastructure.client.ServicioNotificacionClient;
import com.pragma.pagos.infrastructure.producer.EventoPagoProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrquestadorPagoTest {

    @Mock
    private MotorPagosClient motorPagosClient;

    @Mock
    private SistemaAutenticacionClient sistemaAutenticacionClient;

    @Mock
    private ServicioNotificacionClient servicioNotificacionClient;

    @Mock
    private EventoPagoProducer eventoPagoProducer;

    @Mock
    private ValidacionPagoService validacionPagoService;

    @Mock
    private RecuperacionFalloService recuperacionFalloService;

    private OrquestadorPago orquestadorPago;

    @BeforeEach
    void setUp() {
        orquestadorPago = new OrquestadorPago(
            motorPagosClient,
            sistemaAutenticacionClient,
            servicioNotificacionClient,
            eventoPagoProducer,
            validacionPagoService,
            recuperacionFalloService
        );
    }

    @Nested
    @DisplayName("Flujo exitoso de pago")
    class FlujoExitoso {

        @Test
        @DisplayName("Debe completar el flujo de pago exitosamente")
        void debeCompletarFlujoExitoso() {
            Pago pagoOriginal = crearPagoValido();
            String tokenAutenticacion = "Bearer token-valido-123";
            Map<String, Object> respuestaMotor = Map.of(
                "estado", "APROBADO",
                "codigoAutorizacion", "AUTH-998877"
            );

            when(sistemaAutenticacionClient.validarToken(anyString())).thenReturn(true);
            when(motorPagosClient.procesarPago(any(Pago.class))).thenReturn(respuestaMotor);
            doNothing().when(eventoPagoProducer).publicar(any(Pago.class));
            doNothing().when(servicioNotificacionClient).enviarNotificacion(anyString(), anyString());

            Pago resultado = orquestadorPago.ejecutar(pagoOriginal, tokenAutenticacion);

            assertNotNull(resultado);
            assertEquals(EstadoPago.APROBADO, resultado.getEstado());
            assertEquals("AUTH-998877", resultado.getDescripcion());

            verify(sistemaAutenticacionClient).validarToken(tokenAutenticacion);
            verify(motorPagosClient).procesarPago(any(Pago.class));
            verify(eventoPagoProducer).publicar(any(Pago.class));
            verify(servicioNotificacionClient).enviarNotificacion(anyString(), anyString());
        }

        @Test
        @DisplayName("Debe validar idempotency key para evitar pagos duplicados")
        void debeValidarIdempotencyKey() {
            String idempotencyKey = "idem-unico-123";
            Pago pagoOriginal = crearPagoValido();

            when(sistemaAutenticacionClient.validarToken(anyString())).thenReturn(true);
            when(motorPagosClient.procesarPago(any(Pago.class))).thenReturn(Map.of("estado", "APROBADO"));

            orquestadorPago.ejecutar(pagoOriginal, "token");
            orquestadorPago.ejecutar(pagoOriginal, "token");

            verify(motorPagosClient, times(1)).procesarPago(any(Pago.class));
        }
    }

    @Nested
    @DisplayName("Validaciones de entrada")
    class ValidacionesEntrada {

        @Test
        @DisplayName("Debe lanzar excepción cuando autenticación falla")
        void debeFallarConAutenticacionInvalida() {
            Pago pago = crearPagoValido();
            String tokenInvalido = "Bearer token-invalido";

            when(sistemaAutenticacionClient.validarToken(tokenInvalido)).thenReturn(false);

            assertThrows(ServicioNoDisponibleException.class, () ->
                orquestadorPago.ejecutar(pago, tokenInvalido)
            );

            verify(motorPagosClient, never()).procesarPago(any(Pago.class));
        }

        @Test
        @DisplayName("Debe validar el pago antes de procesarlo")
        void debeValidarPagoAntesDeProcesar() {
            Pago pagoInvalido = crearPagoValido();
            String token = "Bearer token-valido";

            doThrow(new PagoInvalidoException("Monto inválido", "MONTO_INVALIDO", "monto", -100))
                .when(validacionPagoService).validar(any(Pago.class));

            assertThrows(PagoInvalidoException.class, () ->
                orquestadorPago.ejecutar(pagoInvalido, token)
            );

            verify(motorPagosClient, never()).procesarPago(any(Pago.class));
        }
    }

    @Nested
    @DisplayName("Manejo de fallos")
    class ManejoFallos {

        @Test
        @DisplayName("Debe reintentar cuando el motor de pagos falla temporalmente")
        void debeReintentarEnFalloTemporal() {
            Pago pago = crearPagoValido();
            Map<String, Object> respuestaExitosa = Map.of("estado", "APROBADO");

            when(sistemaAutenticacionClient.validarToken(anyString())).thenReturn(true);
            when(motorPagosClient.procesarPago(any(Pago.class)))
                .thenThrow(new RuntimeException("Error temporal"))
                .thenReturn(respuestaExitosa);

            Pago resultado = orquestadorPago.ejecutar(pago, "Bearer token");

            assertNotNull(resultado);
            verify(motorPagosClient, times(2)).procesarPago(any(Pago.class));
        }

        @Test
        @DisplayName("Debe notificar cuando el pago es rechazado")
        void debeNotificarRechazo() {
            Pago pago = crearPagoValido();
            Map<String, Object> respuestaRechazado = Map.of(
                "estado", "RECHAZADO",
                "motivo", "Fondos insuficientes"
            );

            when(sistemaAutenticacionClient.validarToken(anyString())).thenReturn(true);
            when(motorPagosClient.procesarPago(any(Pago.class))).thenReturn(respuestaRechazado);
            doNothing().when(servicioNotificacionClient).enviarNotificacion(anyString(), anyString());

            Pago resultado = orquestadorPago.ejecutar(pago, "Bearer token");

            assertEquals(EstadoPago.RECHAZADO, resultado.getEstado());
            verify(servicioNotificacionClient).enviarNotificacion(
                eq(pago.getClienteId()),
                anyString()
            );
        }

        @Test
        @DisplayName("Debe publicar evento de pago fallido en DLQ")
        void debePublicarEnDLQCuandoFallaDefinitivamente() {
            Pago pago = crearPagoValido();

            when(sistemaAutenticacionClient.validarToken(anyString())).thenReturn(true);
            when(motorPagosClient.procesarPago(any(Pago.class)))
                .thenThrow(new RuntimeException("Error permanente"));
            doNothing().when(recuperacionFalloService).registrarFallo(any(Pago.class), any(Exception.class));

            assertThrows(RuntimeException.class, () ->
                orquestadorPago.ejecutar(pago, "Bearer token")
            );

            verify(recuperacionFalloService).registrarFallo(eq(pago), any(Exception.class));
        }
    }

    @Nested
    @DisplayName("Trazabilidad con correlation ID")
    class Trazabilidad {

        @Test
        @DisplayName("Debe propagar correlation ID a través del flujo")
        void debePropagarCorrelationId() {
            String correlationId = UUID.randomUUID().toString();
            Pago pago = crearPagoValido();
            ArgumentCaptor<Pago> pagoCaptor = ArgumentCaptor.forClass(Pago.class);

            when(sistemaAutenticacionClient.validarToken(anyString(),anyString())).thenReturn(true);
            when(motorPagosClient.procesarPago(any(Pago.class))).thenReturn(Map.of("estado", "APROBADO"));

            orquestadorPago.ejecutar(pago, "Bearer token");

            verify(motorPagosClient).procesarPago(pagoCaptor.capture());
            assertEquals(correlationId, pagoCaptor.getValue().getCorrelationId());
        }

        @Test
        @DisplayName("Debe generar correlation ID si no existe")
        void debeGenerarCorrelationId() {
            Pago pagoSinCorrelation = new Pago(
                UUID.randomUUID(),
                "idem-001",
                new BigDecimal("100.00"),
                "USD",
                EstadoPago.PENDIENTE,
                null,
                LocalDateTime.now(),
                LocalDateTime.now(),
                "Pago sin correlation",
                "cliente-001",
                "orden-001"
            );

            when(sistemaAutenticacionClient.validarToken(anyString(),anyString())).thenReturn(true);
            when(motorPagosClient.procesarPago(any(Pago.class))).thenReturn(Map.of("estado", "APROBADO"));

            Pago resultado = orquestadorPago.ejecutar(pagoSinCorrelation, "Bearer token");

            assertNotNull(resultado.getCorrelationId());
            assertFalse(resultado.getCorrelationId().isEmpty());
        }
    }

    private Pago crearPagoValido() {
        return new Pago(
            UUID.randomUUID(),
            "idem-" + System.nanoTime(),
            new BigDecimal("1500.00"),
            "USD",
            EstadoPago.PENDIENTE,
            "corr-" + UUID.randomUUID().toString(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            "Pago orden #12345",
            "cliente-001",
            "orden-001"
        );
    }
}
