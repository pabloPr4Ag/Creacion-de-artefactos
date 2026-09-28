package com.pragma.pagos.infrastructure.client;


import com.pragma.pagos.domain.model.Pago;
import com.pragma.pagos.domain.exception.ServicioNoDisponibleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class ServicioNotificacionClient {

    private static final Logger log = LoggerFactory.getLogger(ServicioNotificacionClient.class);
    private static final String SERVICIO = "ServicioNotificacion";

    private final RestTemplate restTemplate;
    private final String endpointBase;
    private final String canalPreferido;

    public ServicioNotificacionClient(
            @Value("${integracion.notificaciones.url:http://localhost:8083/api/notificaciones}") String endpointBase,
            @Value("${integracion.notificaciones.canal-preferido:EMAIL}") String canalPreferido) {
        this.restTemplate = new RestTemplate();
        this.endpointBase = endpointBase;
        this.canalPreferido = canalPreferido;
    }

    public void enviarNotificacionPago(NotificacionPago notificacion) {
        log.info("Enviando notificación de pago {} al usuario={}, canal={}",
                notificacion.tipo(), notificacion.destinatario(), canalPreferido);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Correlation-Id", notificacion.correlationId());

        Map<String, Object> body = construirCuerpoNotificacion(notificacion);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            restTemplate.exchange(
                    endpointBase + "/enviar",
                    HttpMethod.POST,
                    request,
                    Map.class
            );
            log.info("Notificación enviada exitosamente: tipo={}, destinatario={}",
                    notificacion.tipo(), notificacion.destinatario());

        } catch (HttpClientErrorException e) {
            log.error("Error HTTP al enviar notificación a {}: status={}, body={}",
                    SERVICIO, e.getStatusCode(), e.getResponseBodyAsString());
            throw mapearErrorHttp(e, notificacion);
        } catch (Exception e) {
            log.error("Error inesperado al enviar notificación a {}: {}",
                    SERVICIO, e.getMessage(), e);
            throw new ServicioNoDisponibleException(
                    "Error de comunicación con " + SERVICIO + ": " + e.getMessage(),
                    "SERVICIO_INDISPONIBLE",
                    SERVICIO
            );
        }
    }

    public void enviarNotificacionMasiva(List<NotificacionPago> notificaciones) {
        if (notificaciones == null || notificaciones.isEmpty()) {
            log.debug("No hay notificaciones que enviar en lote");
            return;
        }

        log.info("Enviando {} notificaciones en lote", notificaciones.size());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        List<Map<String, Object>> body = notificaciones.stream()
                .map(this::construirCuerpoNotificacion)
                .toList();

        HttpEntity<List<Map<String, Object>>> request = new HttpEntity<>(body, headers);

        try {
            restTemplate.exchange(
                    endpointBase + "/enviar-lote",
                    HttpMethod.POST,
                    request,
                    Map.class
            );
            log.info("Notificaciones en lote enviadas: cantidad={}", notificaciones.size());

        } catch (HttpClientErrorException e) {
            log.error("Error HTTP al enviar notificaciones en lote a {}: status={}",
                    SERVICIO, e.getStatusCode());
            throw mapearErrorHttp(e, notificaciones.get(0));
        } catch (Exception e) {
            log.error("Error inesperado al enviar notificaciones en lote a {}: {}",
                    SERVICIO, e.getMessage(), e);
            throw new ServicioNoDisponibleException(
                    "Error de comunicación con " + SERVICIO + " en modo lote",
                    "SERVICIO_INDISPONIBLE",
                    SERVICIO
            );
        }
    }

    public boolean verificarDisponibilidad() {
        try {
            restTemplate.getForObject(endpointBase + "/health", Map.class);
            log.debug("{} disponible", SERVICIO);
            return true;
        } catch (Exception e) {
            log.warn("{} no disponible: {}", SERVICIO, e.getMessage());
            return false;
        }
    }

    private Map<String, Object> construirCuerpoNotificacion(NotificacionPago notificacion) {
        return Map.of(
                "canal", canalPreferido,
                "destinatario", notificacion.destinatario(),
                "tipo", notificacion.tipo(),
                "titulo", notificacion.titulo(),
                "mensaje", notificacion.mensaje(),
                "datosAdicionales", Map.of(
                        "pagoId", notificacion.pagoId(),
                        "monto", notificacion.monto() != null ? notificacion.monto().toPlainString() : "",
                        "estado", notificacion.estado()
                )
        );
    }

    public void enviarNotificacion(String correlationId, String tipo, String mensaje) {
        NotificacionPago notificacion = new NotificacionPago(
            correlationId,
            "DESTINATARIO_DESCONOCIDO", 
            tipo,
            "Notificación de Pago",
            mensaje,
            "N/A",
            "0.00",
            "N/A"
        );
        enviarNotificacionPago(notificacion);
    }
        if (status.value() == 400) {
            return new ServicioNoDisponibleException(
                    "Datos inválidos para notificación: " + cuerpo,
                    "NOTIFICACION_INVALIDA",
                    SERVICIO
            );
        } else if (status.value() == 404) {
            return new ServicioNoDisponibleException(
                    "Destinatario no encontrado para notificación: " + notificacion.destinatario(),
                    "DESTINATARIO_NO_ENCONTRADO",
                    SERVICIO
            );
        } else if (status.value() == 429) {
            log.warn("Rate limit alcanzado en {}, reintentando...", SERVICIO);
            throw new ServicioNoDisponibleException(
                    "Rate limit excedido en " + SERVICIO,
                    "RATE_LIMIT_EXCEDIDO",
                    SERVICIO
            );
        } else {
            return new ServicioNoDisponibleException(
                    "Error de " + SERVICIO + " (status " + status + ")",
                    "ERROR_SERVICIO_EXTERNO",
                    SERVICIO
            );
        }
    }

    public record NotificacionPago(
            String correlationId,
            String destinatario,
            String tipo,
            String titulo,
            String mensaje,
            String pagoId,
            String monto,
            String estado
    ) {
        public static NotificacionPago pagoExitoso(String correlationId, String destinatario,
                                                   String pagoId, String monto) {
            return new NotificacionPago(
                    correlationId,
                    destinatario,
                    "PAGO_EXITOSO",
                    "Pago procesado exitosamente",
                    "Tu pago por " + monto + " ha sido procesado correctamente. ID de transacción: " + pagoId,
                    pagoId,
                    monto,
                    "COMPLETADO"
            );
        }

        public static NotificacionPago pagoFallido(String correlationId, String destinatario,
                                                   String pagoId, String monto, String motivo) {
            return new NotificacionPago(
                    correlationId,
                    destinatario,
                    "PAGO_FALLIDO",
                    "Pago fallido",
                    "Tu pago por " + monto + " no pudo ser procesado. Motivo: " + motivo,
                    pagoId,
                    monto,
                    "FALLIDO"
            );
        }

        public static NotificacionPago pagoPendiente(String correlationId, String destinatario,
                                                     String pagoId, String monto) {
            return new NotificacionPago(
                    correlationId,
                    destinatario,
                    "PAGO_PENDIENTE",
                    "Pago en proceso",
                    "Tu pago por " + monto + " está siendo procesado. Te notificaremos cuando completes.",
                    pagoId,
                    monto,
                    "PENDIENTE"
            );
        }
    }
}