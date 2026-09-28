package com.pragma.pagos.domain.service;

import com.pragma.pagos.domain.exception.PagoInvalidoException;
import com.pragma.pagos.domain.model.EstadoPago;
import com.pragma.pagos.domain.model.Pago;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ValidacionPagoServiceTest {

    private ValidacionPagoService validacionService;

    @BeforeEach
    void setUp() {
        validacionService = new ValidacionPagoService();
    }

    @Nested
    @DisplayName("Casos felices - Validación de datos válidos")
    class CasosFelices {

        @Test
        @DisplayName("Debe validar correctamente un pago con todos los datos válidos")
        void debeValidarPagoCompleto() {
            Pago pago = new Pago(
                UUID.randomUUID(),
                "idem-12345",
                new BigDecimal("1500.00"),
                "USD",
                EstadoPago.PENDIENTE,
                "corr-001",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "Pago de orden #12345",
                "cliente-001",
                "orden-001"
            );

            assertDoesNotThrow(() -> validacionService.validar(pago));
        }

        @Test
        @DisplayName("Debe aceptar monto en diferentes monedas")
        void debeAceptarMultiplesMonedas() {
            String[] monedasValidas = {"USD", "EUR", "COP", "MXN", "PEN"};

            for (String moneda : monedasValidas) {
                Pago pago = crearPagoConMoneda(moneda);
                assertDoesNotThrow(() -> validacionService.validar(pago),
                    "La moneda " + moneda + " debería ser válida");
            }
        }

        @Test
        @DisplayName("Debe aceptar pagos con monto máximo")
        void debeAceptarMontoMaximo() {
            Pago pago = new Pago(
                UUID.randomUUID(),
                "idem-max",
                new BigDecimal("999999.99"),
                "USD",
                EstadoPago.PENDIENTE,
                "corr-002",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "Monto máximo",
                "cliente-001",
                "orden-001"
            );

            assertDoesNotThrow(() -> validacionService.validar(pago));
        }

        @Test
        @DisplayName("Debe aceptar idempotency key con formato UUID")
        void debeAceptarIdempotencyKeyUuid() {
            String uuidKey = UUID.randomUUID().toString();
            Pago pago = crearPagoConIdempotencyKey(uuidKey);

            assertDoesNotThrow(() -> validacionService.validar(pago));
        }
    }

    @Nested
    @DisplayName("Edge Cases - Validación de casos límite")
    class EdgeCases {

        @Test
        @DisplayName("Debe rechazar monto negativo")
        void debeRechazarMontoNegativo() {
            Pago pago = new Pago(
                UUID.randomUUID(),
                "idem-001",
                new BigDecimal("-100.00"),
                "USD",
                EstadoPago.PENDIENTE,
                "corr-003",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "Pago negativo",
                "cliente-001",
                "orden-001"
            );

            PagoInvalidoException exception = assertThrows(
                PagoInvalidoException.class,
                () -> validacionService.validar(pago)
            );

            assertEquals("MONTO_INVALIDO", exception.getCodigoError());
            assertEquals("monto", exception.getCampo());
        }

        @Test
        @DisplayName("Debe rechazar monto cero")
        void debeRechazarMontoCero() {
            Pago pago = new Pago(
                UUID.randomUUID(),
                "idem-002",
                BigDecimal.ZERO,
                "USD",
                EstadoPago.PENDIENTE,
                "corr-004",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "Pago cero",
                "cliente-001",
                "orden-001"
            );

            PagoInvalidoException exception = assertThrows(
                PagoInvalidoException.class,
                () -> validacionService.validar(pago)
            );

            assertEquals("MONTO_INVALIDO", exception.getCodigoError());
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"  ", "\t", "\n"})
        @DisplayName("Debe rechazar idempotency key nula o vacía")
        void debeRechazarIdempotencyKeyInvalida(String idempotencyKey) {
            Pago pago = new Pago(
                UUID.randomUUID(),
                idempotencyKey,
                new BigDecimal("100.00"),
                "USD",
                EstadoPago.PENDIENTE,
                "corr-005",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "Pago test",
                "cliente-001",
                "orden-001"
            );

            PagoInvalidoException exception = assertThrows(
                PagoInvalidoException.class,
                () -> validacionService.validar(pago)
            );

            assertEquals("IDEMPOTENCY_KEY_INVALIDA", exception.getCodigoError());
            assertEquals("idempotencyKey", exception.getCampo());
        }

        @ParameterizedTest
        @CsvSource({
            "XYZ, Moneda inválida",
            "123, Moneda con números",
            "US, Moneda muy corta",
            "USDD, Moneda muy larga"
        })
        @DisplayName("Debe rechazar monedas inválidas")
        void debeRechazarMonedaInvalida(String moneda, String descripcion) {
            Pago pago = crearPagoConMoneda(moneda);

            PagoInvalidoException exception = assertThrows(
                PagoInvalidoException.class,
                () -> validacionService.validar(pago),
                descripcion
            );

            assertEquals("MONEDA_INVALIDA", exception.getCodigoError());
        }

        @Test
        @DisplayName("Debe rechazar cliente ID nulo")
        void debeRechazarClienteIdNulo() {
            Pago pago = new Pago(
                UUID.randomUUID(),
                "idem-003",
                new BigDecimal("100.00"),
                "USD",
                EstadoPago.PENDIENTE,
                "corr-006",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "Pago test",
                null,
                "orden-001"
            );

            PagoInvalidoException exception = assertThrows(
                PagoInvalidoException.class,
                () -> validacionService.validar(pago)
            );

            assertEquals("CLIENTE_ID_OBLIGATORIO", exception.getCodigoError());
        }

        @Test
        @DisplayName("Debe rechazar orden ID nulo")
        void debeRechazarOrdenIdNulo() {
            Pago pago = new Pago(
                UUID.randomUUID(),
                "idem-004",
                new BigDecimal("100.00"),
                "USD",
                EstadoPago.PENDIENTE,
                "corr-007",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "Pago test",
                "cliente-001",
                null
            );

            PagoInvalidoException exception = assertThrows(
                PagoInvalidoException.class,
                () -> validacionService.validar(pago)
            );

            assertEquals("ORDEN_ID_OBLIGATORIO", exception.getCodigoError());
        }
    }

    @Nested
    @DisplayName("Transiciones de estado")
    class TransicionesEstado {

        @Test
        @DisplayName("Debe permitir transición de PENDIENTE a PROCESANDO")
        void debePermitirTransicionPendienteAProcesando() {
            assertTrue(EstadoPago.esTransicionValida(EstadoPago.PENDIENTE, EstadoPago.PROCESANDO));
        }

        @Test
        @DisplayName("Debe permitir transición de PROCESANDO a APROBADO")
        void debePermitirTransicionProcesandoAAprobado() {
            assertTrue(EstadoPago.esTransicionValida(EstadoPago.PROCESANDO, EstadoPago.APROBADO));
        }

        @Test
        @DisplayName("Debe permitir transición de PROCESANDO a RECHAZADO")
        void debePermitirTransicionProcesandoARechazado() {
            assertTrue(EstadoPago.esTransicionValida(EstadoPago.PROCESANDO, EstadoPago.RECHAZADO));
        }

        @Test
        @DisplayName("Debe rechazar transición directa de PENDIENTE a APROBADO")
        void debeRechazarTransicionDirecta() {
            assertFalse(EstadoPago.esTransicionValida(EstadoPago.PENDIENTE, EstadoPago.APROBADO));
        }

        @Test
        @DisplayName("Debe rechazar transición de APROBADO a cualquier estado")
        void debeRechazarTransicionDesdeAprobado() {
            assertFalse(EstadoPago.esTransicionValida(EstadoPago.APROBADO, EstadoPago.PENDIENTE));
            assertFalse(EstadoPago.esTransicionValida(EstadoPago.APROBADO, EstadoPago.RECHAZADO));
        }
    }

    private Pago crearPagoConMoneda(String moneda) {
        return new Pago(
            UUID.randomUUID(),
            "idem-" + System.nanoTime(),
            new BigDecimal("100.00"),
            moneda,
            EstadoPago.PENDIENTE,
            "corr-" + System.nanoTime(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            "Pago test",
            "cliente-001",
            "orden-001"
        );
    }

    private Pago crearPagoConIdempotencyKey(String key) {
        return new Pago(
            UUID.randomUUID(),
            key,
            new BigDecimal("100.00"),
            "USD",
            EstadoPago.PENDIENTE,
            "corr-" + System.nanoTime(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            "Pago test",
            "cliente-001",
            "orden-001"
        );
    }
}
