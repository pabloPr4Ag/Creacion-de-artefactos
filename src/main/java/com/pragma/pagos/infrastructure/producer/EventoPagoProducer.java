package com.pragma.pagos.infrastructure.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.pagos.domain.model.EstadoPago;
import com.pragma.pagos.domain.model.Pago;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventoPagoProducer {

    private static final String TOPIC_PAGOS = "pagos.eventos";
    private static final String TOPIC_NOTIFICACIONES = "notificaciones.pago";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void enviarEventoEstadoCambiado(Pago pago, EstadoPago estadoAnterior) {
        Map<String, Object> evento = new HashMap<>();
        evento.put("eventType", "ESTADO_CAMBIADO");
        evento.put("pagoId", pago.getId().toString());
        evento.put("idempotencyKey", pago.getIdempotencyKey());
        evento.put("estadoAnterior", estadoAnterior.name());
        evento.put("estadoNuevo", pago.getEstado().name());
        evento.put("monto", pago.getMonto().toPlainString());
        evento.put("moneda", pago.getMoneda());
        evento.put("clienteId", pago.getClienteId());
        evento.put("ordenId", pago.getOrdenId());
        evento.put("correlationId", pago.getCorrelationId());
        evento.put("timestamp", Instant.now().toString());

        String key = pago.getIdempotencyKey();
        String payload = serializar(evento);

        if (payload == null) {
            log.error("Error al serializar evento para pago {}, no se envía al topic", pago.getId());
            return;
        }

        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(TOPIC_PAGOS, key, payload);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Error al enviar evento de cambio de estado para pago {}: {}",
                        pago.getId(), ex.getMessage(), ex);
            } else {
                log.info("Evento de cambio de estado enviado - pagoId: {}, partition: {}, offset: {}",
                        pago.getId(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            }
        });
    }

    public void enviarEventoPagoCreado(Pago pago) {
        Map<String, Object> evento = new HashMap<>();
        evento.put("eventType", "PAGO CREADO");
        evento.put("pagoId", pago.getId().toString());
        evento.put("idempotencyKey", pago.getIdempotencyKey());
        evento.put("monto", pago.getMonto().toPlainString());
        evento.put("moneda", pago.getMoneda());
        evento.put("clienteId", pago.getClienteId());
        evento.put("ordenId", pago.getOrdenId());
        evento.put("correlationId", pago.getCorrelationId());
        evento.put("timestamp", Instant.now().toString());

        String key = pago.getIdempotencyKey();
        String payload = serializar(evento);

        if (payload == null) {
            log.error("Error al serializar evento de creación para pago {}, no se envía al topic", pago.getId());
            return;
        }

        kafkaTemplate.send(TOPIC_PAGOS, key, payload)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Error al enviar evento de creación para pago {}: {}",
                                pago.getId(), ex.getMessage(), ex);
                    } else {
                        log.info("Evento de creación enviado - pagoId: {}, partition: {}, offset: {}",
                                pago.getId(),
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    }
                });
    }

    public void enviarEventoNotificacion(Pago pago, String tipoNotificacion) {
        Map<String, Object> evento = new HashMap<>();
        evento.put("eventType", tipoNotificacion);
        evento.put("pagoId", pago.getId().toString());
        evento.put("clienteId", pago.getClienteId());
        evento.put("monto", pago.getMonto().toPlainString());
        evento.put("moneda", pago.getMoneda());
        evento.put("estado", pago.getEstado().name());
        evento.put("correlationId", pago.getCorrelationId());
        evento.put("timestamp", Instant.now().toString());

        String payload = serializar(evento);

        if (payload == null) {
            log.error("Error al serializar notificación para pago {}, no se envía", pago.getId());
            return;
        }

        kafkaTemplate.send(TOPIC_NOTIFICACIONES, pago.getClienteId(), payload)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Error al enviar notificación para pago {}: {}",
                                pago.getId(), ex.getMessage(), ex);
                    } else {
                        log.info("Notificación enviada - tipo: {}, pagoId: {}",
                                tipoNotificacion, pago.getId());
                    }
                });
    }

    public void publicarEventoPago(Map<String, Object> evento) {
        String key = (String) evento.getOrDefault("correlationId", UUID.randomUUID().toString());
        String payload = serializar(evento);

        if (payload == null) {
            log.error("Error al serializar evento, no se envía al topic");
            return;
        }

        kafkaTemplate.send(TOPIC_PAGOS, key, payload)
            .whenComplete((result, ex) -> {
                if (ex != null) {
                    log.error("Error al publicar evento de pago: {}", ex.getMessage());
                } else {
                    log.info("Evento de pago publicado - partition: {}, offset: {}", 
                        result.getRecordMetadata().partition(), 
                        result.getRecordMetadata().offset());
                }
            });
    }
}