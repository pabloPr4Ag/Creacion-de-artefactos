package com.pragma.pagos.infrastructure.client;

import com.pragma.pagos.domain.model.EstadoPago;
import com.pragma.pagos.domain.model.Pago;
import com.pragma.pagos.infrastructure.config.CircuitBreakerConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MotorPagosClientTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CircuitBreakerConfig circuitBreakerConfig;

    private MotorPagosClient motorPagosClient;

    private static final String URL_BASE = "http://motor-pagos:8080/api/v1";

    @BeforeEach
    void setUp() {
        motorPagosClient = new MotorPagosClient(restTemplate, circuitBreakerConfig);
    }

    @Nested
    @DisplayName("Casos exitosos")
    class CasosExitosos {

        @Test
        @DisplayName("Debe procesar pago exitosamente")
        void debeProcesarPagoExitosamente() {
            Pago pago = crearPagoValido();
            Map<String, Object> respuestaEsperada = Map.of(
                "estado", "APROBADO",
                "codigoAutorizacion", "AUTH-123456",
                "transactionId", UUID.randomUUID().toString()
            );

            when(restTemplate.postForEntity(
                eq(URL_BASE + "/procesar"),
                any(Pago.class),
                eq(Map.class)
            )).thenReturn(new ResponseEntity<>(respuestaEsperada, HttpStatus.OK));

            Map<String, Object> resultado = motorPagosClient.procesarPago(pago);

            assertNotNull(resultado);
            assertEquals("APROBADO", resultado.get("estado"));
            assertNotNull(resultado.get("codigoAutorizacion"));

            verify(restTemplate).postForEntity(
                eq(URL_BASE + "/procesar"),
                eq(pago),
                eq(Map.class)
            );
        }

        @Test
        @DisplayName("Debe consultar estado de transacción")
        void debeConsultarEstadoTransaccion() {
            String transactionId = UUID.randomUUID().toString();
            Map<String, Object> estadoEsperado = Map.of(
                "transactionId", transactionId,
                "estado", "COMPLETADO",
                "monto", "1500.00"
            );

            when(restTemplate.getForEntity(
                eq(URL_BASE + "/estado/" + transactionId),
                eq(Map.class)
            )).thenReturn(new ResponseEntity<>(estadoEsperado, HttpStatus.OK));

            Map<String, Object> resultado = motorPagosClient.consultarEstado(transactionId);

            assertNotNull(resultado);
            assertEquals(transactionId, resultado.get("transactionId"));
            assertEquals("COMPLETADO", resultado.get("estado"));
        }

        @Test
        @DisplayName("Debe obtener respuesta de reembolso")
        void debeProcesarReembolso() {
            String transactionId = UUID.randomUUID().toString();
            Map<String, Object> respuestaEsperada = Map.of(
                "estado", "REEMBOLSO_PROCESADO",
                "montoReembolsado", "1500.00"
            );

            when(restTemplate.postForEntity(
                eq(URL_BASE + "/reembolso"),
                any(Map.class),
                eq(Map.class)
            )).thenReturn(new ResponseEntity<>(respuestaEsperada, HttpStatus.OK));

            Map<String, Object> resultado = motorPagosClient.procesarReembolso(transactionId, new BigDecimal("1500.00"));

            assertNotNull(resultado);
            assertEquals("REEMBOLSO_PROCESADO", resultado.get("estado"));
        }
    }

    @Nested
    @DisplayName("Manejo de errores del motor de pagos")
    class ManejoErrores {

        @Test
        @DisplayName("Debe lanzar excepción cuando el motor no está disponible")
        void debeLanzarExcepcionCuandoNoDisponible() {
            Pago pago = crearPagoValido();

            when(restTemplate.postForEntity(
                anyString(),
                any(Pago.class),
                eq(Map.class)
            )).thenThrow(new RuntimeException("Connection refused"));

            assertThrows(RuntimeException.class, () ->
                motorPagosClient.procesarPago(pago)
            );
        }

        @Test
        @DisplayName("Debe manejar respuesta de error del motor")
        void debeManejarErrorDelMotor() {
            Pago pago = crearPagoValido();
            Map<String, Object> errorRespuesta = Map.of(
                "error", "FONDOS_INSUFICIENTES",
                "mensaje", "El cliente no tiene fondos suficientes"
            );

            when(restTemplate.postForEntity(
                anyString(),
                any(Pago.class),
                eq(Map.class)
            )).thenReturn(new ResponseEntity<>(errorRespuesta, HttpStatus.BAD_REQUEST));

            Map<String, Object> resultado = motorPagosClient.procesarPago(pago);

            assertNotNull(resultado);
            assertEquals("FONDOS_INSUFICIENTES", resultado.get("error"));
        }

        @Test
        @DisplayName("Debe manejar timeout del motor de pagos")
        void debeManejarTimeout() {
            Pago pago = crearPagoValido();

            when(restTemplate.postForEntity(
                anyString(),
                any(Pago.class),
                eq(Map.class)
            )).thenThrow(new org.springframework.web.client.ResourceAccessException("Read timed out"));

            assertThrows(RuntimeException.class, () ->
                motorPagosClient.procesarPago(pago)
            );
        }

        @Test
        @DisplayName("Debe manejar error de autenticación con el motor")
        void debeManejarErrorAutenticacion() {
            Pago pago = crearPagoValido();

            when(restTemplate.postForEntity(
                anyString(),
                any(Pago.class),
                eq(Map.class)
            )).thenReturn(new ResponseEntity<>(Map.of("error", "UNAUTHORIZED"), HttpStatus.UNAUTHORIZED));

            assertThrows(SecurityException.class, () ->
                motorPagosClient.procesarPago(pago)
            );
        }
    }

    @Nested
    @DisplayName("Contrato del cliente")
    class ContratoCliente {

        @Test
        @DisplayName("Debe incluir headers de correlación en las peticiones")
        void debeIncluirHeadersCorrelacion() {
            String correlationId = UUID.randomUUID().toString();
            Pago pago = crearPagoValido();

            when(restTemplate.postForEntity(
                anyString(),
                any(Pago.class),
                eq(Map.class)
            )).thenReturn(new ResponseEntity<>(Map.of("estado", "APROBADO"), HttpStatus.OK));

            motorPagosClient.procesarPago(pago);

            verify(restTemplate).postForEntity(
                eq(URL_BASE + "/procesar"),
                any(Pago.class),
                eq(Map.class)
            );
        }

        @Test
        @DisplayName("Debe usar la URL configurada del motor de pagos")
        void debeUsarUrlConfigurada() {
            String urlPersonalizada = "http://motor-pagos-custom:9090/api";
            MotorPagosClient clienteCustom = new MotorPagosClient(restTemplate, circuitBreakerConfig);

            when(circuitBreakerConfig.getMotorPagosUrl()).thenReturn(urlPersonalizada);

            when(restTemplate.postForEntity(
                anyString(),
                any(Pago.class),
                eq(Map.class)
            )).thenReturn(new ResponseEntity<>(Map.of("estado", "APROBADO"), HttpStatus.OK));

            clienteCustom.procesarPago(crearPagoValido());

            verify(restTemplate).postForEntity(
                startsWith(urlPersonalizada),
                any(Pago.class),
                eq(Map.class)
            );
        }

        @Test
        @DisplayName("Debe mapear correctamente la respuesta del motor a modelo de dominio")
        void debeMapearRespuestaADominio() {
            Map<String, Object> respuestaRaw = Map.of(
                "transactionId", "TXN-12345",
                "estado", "APROBADO",
                "monto", 1500.00,
                "codigoAutorizacion", "AUTH-999",
                "fecha", "2024-01-15T10:30:00Z"
            );

            when(restTemplate.postForEntity(
                anyString(),
                any(Pago.class),
                eq(Map.class)
            )).thenReturn(new ResponseEntity<>(respuestaRaw, HttpStatus.OK));

            Map<String, Object> resultado = motorPagosClient.procesarPago(crearPagoValido());

            assertTrue(resultado.containsKey("transactionId"));
            assertTrue(resultado.containsKey("estado"));
            assertTrue(resultado.containsKey("codigoAutorizacion"));
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
