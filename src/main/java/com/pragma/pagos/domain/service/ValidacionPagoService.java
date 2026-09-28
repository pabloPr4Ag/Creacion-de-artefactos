package com.pragma.pagos.domain.service;

import com.pragma.pagos.domain.exception.PagoInvalidoException;
import com.pragma.pagos.domain.model.EstadoPago;
import com.pragma.pagos.domain.model.Pago;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

public final class ValidacionPagoService {

    private static final Pattern IDEMPOTENCY_KEY_PATTERN = Pattern.compile(
            "^[A-Za-z0-9\\-_]{8,64}$"
    );

    private static final Pattern CLIENTE_ID_PATTERN = Pattern.compile(
            "^[A-Za-z0-9]{1,36}$"
    );

    private static final Pattern ORDEN_ID_PATTERN = Pattern.compile(
            "^[A-Za-z0-9\\-_]{1,48}$"
    );

    private static final BigDecimal MONTO_MINIMO = new BigDecimal("0.01");
    private static final BigDecimal MONTO_MAXIMO = new BigDecimal("999999.99");

    private static final List<String> MONEDAS_SOPORTADAS = List.of(
            "USD", "EUR", "COP", "MXN", "ARS", "BRL", "CLP", "PEN"
    );

    private static final int DESCRIPCION_MAX_LENGTH = 500;
    private static final int DESCRIPCION_MIN_LENGTH = 3;

    public ValidacionPagoService() {
    }

    public void validarPago(Pago pago) {
        List<String> errores = new ArrayList<>();

        validarIdempotencyKey(pago.getIdempotencyKey(), errores);
        validarMonto(pago.getMonto(), errores);
        validarMoneda(pago.getMoneda(), errores);
        validarClienteId(pago.getClienteId(), errores);
        validarOrdenId(pago.getOrdenId(), errores);
        validarDescripcion(pago.getDescripcion(), errores);

        if (!errores.isEmpty()) {
            throw PagoInvalidoException.conDetalles(
                    "El pago no pasó la validación: " + String.join("; ", errores),
                    "pago",
                    pago
            );
        }
    }

    public void validarIdempotencyKey(String idempotencyKey, List<String> errores) {
        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            errores.add("La clave de idempotencia es obligatoria");
            return;
        }

        if (!IDEMPOTENCY_KEY_PATTERN.matcher(idempotencyKey).matches()) {
            errores.add("La clave de idempotencia debe tener entre 8 y 64 caracteres alfanuméricos, guiones o guiones bajos");
        }

        if (idempotencyKey.length() < 8) {
            errores.add("La clave de idempotencia es demasiado corta (mínimo 8 caracteres)");
        }

        if (idempotencyKey.length() > 64) {
            errores.add("La clave de idempotencia es demasiado larga (máximo 64 caracteres)");
        }
    }

    public void validarMonto(BigDecimal monto, List<String> errores) {
        if (monto == null) {
            errores.add("El monto es obligatorio");
            return;
        }

        if (monto.compareTo(MONTO_MINIMO) < 0) {
            errores.add("El monto debe ser mayor o igual a " + MONTO_MINIMO);
        }

        if (monto.compareTo(MONTO_MAXIMO) > 0) {
            errores.add("El monto debe ser menor o igual a " + MONTO_MAXIMO);
        }

        if (monto.scale() > 2) {
            errores.add("El monto no puede tener más de 2 decimales");
        }

        if (monto.compareTo(BigDecimal.ZERO) == 0) {
            errores.add("El monto debe ser mayor a cero");
        }
    }

    public void validarMoneda(String moneda, List<String> errores) {
        if (moneda == null || moneda.isBlank()) {
            errores.add("La moneda es obligatoria");
            return;
        }

        String monedaUpper = moneda.toUpperCase();
        if (!MONEDAS_SOPORTADAS.contains(monedaUpper)) {
            errores.add("La moneda '" + moneda + "' no está soportada. Monedas válidas: " + MONEDAS_SOPORTADAS);
        }
    }

    public void validarClienteId(String clienteId, List<String> errores) {
        if (clienteId == null || clienteId.isBlank()) {
            errores.add("El ID del cliente es obligatorio");
            return;
        }

        if (!CLIENTE_ID_PATTERN.matcher(clienteId).matches()) {
            errores.add("El ID del cliente debe contener solo caracteres alfanuméricos (máximo 36 caracteres)");
        }
    }

    public void validarOrdenId(String ordenId, List<String> errores) {
        if (ordenId == null || ordenId.isBlank()) {
            errores.add("El ID de la orden es obligatorio");
            return;
        }

        if (!ORDEN_ID_PATTERN.matcher(ordenId).matches()) {
            errores.add("El ID de la orden contiene caracteres inválidos (máximo 48 caracteres, permite guiones y guiones bajos)");
        }
    }

    public void validarDescripcion(String descripcion, List<String> errores) {
        if (descripcion != null && !descripcion.isBlank()) {
            if (descripcion.length() < DESCRIPCION_MIN_LENGTH) {
                errores.add("La descripción debe tener al menos " + DESCRIPCION_MIN_LENGTH + " caracteres");
            }
            if (descripcion.length() > DESCRIPCION_MAX_LENGTH) {
                errores.add("La descripción no puede exceder " + DESCRIPCION_MAX_LENGTH + " caracteres");
            }
        }
    }

    public boolean esIdempotencyKeyValida(String idempotencyKey) {
        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            return false;
        }
        return IDEMPOTENCY_KEY_PATTERN.matcher(idempotencyKey).matches();
    }

    public boolean esMonedaSoportada(String moneda) {
        if (moneda == null || moneda.isBlank()) {
            return false;
        }
        return MONEDAS_SOPORTADAS.contains(moneda.toUpperCase());
    }

    public boolean esMontoValido(BigDecimal monto) {
        if (monto == null) {
            return false;
        }
        return monto.compareTo(MONTO_MINIMO) >= 0
                && monto.compareTo(MONTO_MAXIMO) <= 0
                && monto.compareTo(BigDecimal.ZERO) > 0
                && monto.scale() <= 2;
    }

    public void validarTransicionEstado(EstadoPago estadoActual, EstadoPago nuevoEstado, List<String> errores) {
        if (estadoActual == null || nuevoEstado == null) {
            errores.add("El estado actual y el nuevo estado son obligatorios");
            return;
        }

        if (!EstadoPago.esTransicionValida(estadoActual, nuevoEstado)) {
            errores.add("Transición de estado inválida: de " + estadoActual + " a " + nuevoEstado);
        }
    }

    public void validarCorrelationId(String correlationId, List<String> errores) {
        if (correlationId == null || correlationId.isBlank()) {
            errores.add("El correlation ID es obligatorio para trazabilidad");
            return;
        }

        try {
            UUID.fromString(correlationId);
        } catch (IllegalArgumentException e) {
            errores.add("El correlation ID debe ser un UUID válido");
        }
    }

    public List<String> obtenerMonedasSoportadas() {
        return new ArrayList<>(MONEDAS_SOPORTADAS);
    }

    public BigDecimal getMontoMinimo() {
        return MONTO_MINIMO;
    }

    public void validar(Pago pago) {
        validarPago(pago);
    }