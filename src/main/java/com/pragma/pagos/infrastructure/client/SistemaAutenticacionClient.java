package com.pragma.pagos.infrastructure.client;

import com.pragma.pagos.domain.exception.PagoInvalidoException;
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

import java.util.Map;

@Component
public class SistemaAutenticacionClient {

    private static final Logger log = LoggerFactory.getLogger(SistemaAutenticacionClient.class);
    private static final String SERVICIO = "SistemaAutenticacion";

    private final RestTemplate restTemplate;
    private final String endpointBase;
    private final String apiKey;

    public SistemaAutenticacionClient(
            @Value("${integracion.autenticacion.url:http://localhost:8082/api/auth}") String endpointBase,
            @Value("${integracion.autenticacion.api-key:#{null}}") String apiKey) {
        this.restTemplate = new RestTemplate();
        this.endpointBase = endpointBase;
        this.apiKey = apiKey;
    }

    public ValidacionTokenResult validarToken(String token, String correlationId) {
        log.debug("Validando token para correlationId={}", correlationId);

        if (token == null || token.isBlank()) {
            throw PagoInvalidoException.conDetalles(
                    "Token de autenticación requerido",
                    "token",
                    null
            );
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Correlation-Id", correlationId);
        if (apiKey != null && !apiKey.isBlank()) {
            headers.set("X-API-Key", apiKey);
        }
        headers.set("Authorization", "Bearer " + token);

        Map<String, Object> body = Map.of(
                "token", token,
                "requiereRol", "USUARIO"
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> respuesta = restTemplate.exchange(
                    endpointBase + "/validar",
                    HttpMethod.POST,
                    request,
                    Map.class
            ).getBody();

            Boolean valido = (Boolean) respuesta.get("valido");
            String usuarioId = (String) respuesta.get("usuarioId");
            String rol = (String) respuesta.get("rol");

            log.info("Token validado para usuario={}, rol={}, valido={}", usuarioId, rol, valido);

            return new ValidacionTokenResult(valido, usuarioId, rol);

        } catch (HttpClientErrorException e) {
            log.error("Error HTTP al validar token en {}: status={}, body={}",
                    SERVICIO, e.getStatusCode(), e.getResponseBodyAsString());
            throw mapearErrorHttp(e);
        } catch (Exception e) {
            log.error("Error inesperado al validar token en {}: {}", SERVICIO, e.getMessage(), e);
            throw new ServicioNoDisponibleException(
                    "Error de comunicación con " + SERVICIO + ": " + e.getMessage(),
                    "SERVICIO_INDISPONIBLE",
                    SERVICIO
            );
        }
    }

    public boolean verificarPermiso(String usuarioId, String permiso, String correlationId) {
        log.debug("Verificando permiso {} para usuario={}", permiso, usuarioId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Correlation-Id", correlationId);
        if (apiKey != null && !apiKey.isBlank()) {
            headers.set("X-API-Key", apiKey);
        }

        Map<String, Object> body = Map.of(
                "usuarioId", usuarioId,
                "permiso", permiso
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> respuesta = restTemplate.exchange(
                    endpointBase + "/verificar-permiso",
                    HttpMethod.POST,
                    request,
                    Map.class
            ).getBody();

            Boolean tienePermiso = (Boolean) respuesta.get("tienePermiso");
            log.debug("Usuario={} tiene permiso {}: {}", usuarioId, permiso, tienePermiso);
            return Boolean.TRUE.equals(tienePermiso);

        } catch (HttpClientErrorException e) {
            log.error("Error HTTP al verificar permiso en {}: status={}",
                    SERVICIO, e.getStatusCode());
            return false;
        } catch (Exception e) {
            log.error("Error inesperado al verificar permiso en {}: {}", SERVICIO, e.getMessage(), e);
            throw new ServicioNoDisponibleException(
                    "Error al verificar permiso en " + SERVICIO,
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

    private RuntimeException mapearErrorHttp(HttpClientErrorException e) {
        HttpStatusCode status = e.getStatusCode();
        String cuerpo = e.getResponseBodyAsString();

        if (status.value() == 401) {
            return new PagoInvalidoException(
                    "Token de autenticación inválido o expirado",
                    "TOKEN_INVALIDO",
                    "token",
                    null
            );
        } else if (status.value() == 403) {
            return new PagoInvalidoException(
                    "Acceso denegado: permisos insuficientes",
                    "ACCESO_DENEGADO",
                    "permisos",
                    null
            );
        } else if (status.value() == 404) {
            return new PagoInvalidoException(
                    "Usuario no encontrado en " + SERVICIO,
                    "USUARIO_NO_ENCONTRADO",
                    "usuarioId",
                    null
            );
        } else {
            return new ServicioNoDisponibleException(
                    "Error de " + SERVICIO + " (status " + status + ")",
                    "ERROR_SERVICIO_EXTERNO",
                    SERVICIO
            );
        }
    }

    public record ValidacionTokenResult(boolean valido, String usuarioId, String rol) {
        public boolean tieneRol(String rolRequerido) {
            return valido && rol != null && rol.equalsIgnoreCase(rolRequerido);
        }
    }
}