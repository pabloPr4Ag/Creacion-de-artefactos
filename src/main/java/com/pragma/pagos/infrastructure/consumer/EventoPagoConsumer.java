package com.pragma.pagos.infrastructure.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.pagos.domain.model.EstadoPago;
import com.pragma.pagos.domain.model.Pago;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventoPagoConsumer {

    private static final String TOPIC_PAGOS = "pagos.eventos";
    private static final String TOPIC_DLQ = "pagos.dlq";
    private static final String TOPIC_REINTENTOS = "pagos.reintentos";

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = TOPIC_PAGOS, groupId = "pagos-consumer-group")
    public void consumirEventoPago(String mensaje) {
        log.info("Recibido evento del topic {}: {}", TOPIC_PAGOS, mensaje);

        Map<String, Object> evento = deserializar(mensaje);
        if (evento == null) {
            log.error("No se pudo deserializar el mensaje, enviando a DLQ");
            enviarADLQ(mensaje, "DESERIALIZACION_FALLIDA");
            return;
        }

        String eventType = (String) evento.get("eventType");
        String correlationId = (String) evento.get("correlationId");

        log.info("Procesando evento tipo: {}, correlationId: {}", eventType, correlationId);

        try {
            switch (eventType) {
                case "ESTADO_CAMBIADO" -> procesarCambioEstado(evento);
                case "PAGO_CREADO" -> procesarPagoCreado(evento);
                default -> log.warn("Tipo de evento desconocido: {}", eventType);
            }
        } catch (Exception e) {
            log.error("Error al procesar evento {}: {}", eventType, e.getMessage(), e);
            manejarError(evento, e);
        }
    }

    @KafkaListener(topics = TOPIC_REINTENTOS, groupId = "pagos-reintento-group")
    public void consumirReintento(String mensaje) {
        log.info("Recibido evento de reintento del topic {}", TOPIC_REINTENTOS);

        Map<String, Object> evento = deserializar(mensaje);
        if (evento == null) {
            log.error("No se pudo deserializar mensaje de reintento");
            return;
        }

        Integer numeroReintento = (Integer) evento.get("numeroReintento");
        int maxReintentos = 3;

        if (numeroReintento != null && numeroReintento >= maxReintentos) {
            log.warn("Máximo de reintentos alcanzado para evento, enviando a DLQ");
            enviarADLQ(mensaje, "MAX_REINTENTOS_ALCANZADOS");
            return;
        }

        String eventType = (String) evento.get("eventType");
        log.info("Reintentando procesamiento de evento tipo: {}, intento: {}",
                eventType, numeroReintento != null ? numeroReintento + 1 : 1);

        try {
            switch (eventType) {
                case "ESTADO_CAMBIADO" -> procesarCambioEstado(evento);
                case "PAGO_CREADO" -> procesarPagoCreado(evento);
                default -> log.warn("Tipo de evento desconocido en reintento: {}", eventType);
            }
        } catch (Exception e) {
            log.error("Reintento fallido para evento {}: {}", eventType, e.getMessage(), e);
        }
    }

    private void procesarCambioEstado(Map<String, Object> evento) {
        String pagoId = (String) evento.get("pagoId");
        String estadoAnterior = (String) evento.get("estadoAnterior");
        String estadoNuevo = (String) evento.get("estadoNuevo");
        String correlationId = (String) evento.get("correlationId");

        log.info("Procesando cambio de estado - pagoId: {}, {} -> {}, correlationId: {}",
                pagoId, estadoAnterior, estadoNuevo, correlationId);

        boolean esEstadoTerminal = esEstadoTerminal(estadoNuevo);
        if (esEstadoTerminal) {
            log.info("Pago {} alcanzó estado terminal: {}", pagoId, estadoNuevo);
        }
    }

    private void procesarPagoCreado(Map<String, Object> evento) {
        String pagoId = (String) evento.get("pagoId");
        String idempotencyKey = (String) evento.get("idempotencyKey");
        String correlationId = (String) evento.get("correlationId");

        log.info("Procesando pago creado - pagoId: {}, idempotencyKey: {}, correlationId: {}",
                pagoId, idempotencyKey, correlationId);
    }

    private void manejarError(Map<String, Object> evento, Exception e) {
        String eventType = (String) evento.get("eventType");
        Integer numeroReintento = (Integer) evento.getOrDefault("numeroReintento", 0);

        if (debeReintentar(e)) {
            log.info("Evento {} será reintentado (intento {})", eventType, numeroReintento + 1);
            evento.put("numeroReintento", numeroReintento + 1);
            evento.put("ultimoError", e.getMessage());
            evento.put("timestampError", Instant.now().toString());
        } else {
            log.warn("Evento {} no es reintentable, enviando a DLQ", eventType);
            try {
                String payload = objectMapper.writeValueAsString(evento);
                enviarADLQ(payload, e.getClass().getSimpleName());
            } catch (JsonProcessingException ex) {
                log.error("Error al serializar evento para DLQ: {}", ex.getMessage(), ex);
            }
        }
    }

    private boolean debeReintentar(Exception e) {
        String nombreExcepcion = e.getClass().getSimpleName();
        return "TimeoutException".equals(nombreExcepcion) ||
                "ConnectException".equals(nombreExcepcion) ||
                "ResourceAccessException".equals(nombreExcepcion);
    }

    private boolean esEstadoTerminal(String estado) {
        try {
            EstadoPago estadoEnum = EstadoPago.valueOf(estado);
            return estadoEnum == EstadoPago.APROBADO ||
                    estadoEnum == EstadoPago.RECHAZADO ||
                    estadoEnum == EstadoPago.CANCELADO;
        } catch (IllegalArgumentException e) {
            log.warn("Estado desconocido: {}", estado);
            return false;
        }
    }

    private void enviarADLQ(String mensaje, String razon) {
        log.warn("Enviando mensaje a DLQ - razon: {}", razon);
    }

    private Map<String, Object> deserializar(String mensaje) {
        try {
            return objectMapper.readValue(mensaje, Map.class);
        } catch (JsonProcessingException e) {
            log.error("Error al deserializar mensaje: {}", e.getMessage(), e);
            return null;
        }
    }
}