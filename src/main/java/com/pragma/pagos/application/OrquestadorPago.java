package com.pragma.pagos.application;

import com.pragma.pagos.domain.exception.PagoInvalidoException;
import com.pragma.pagos.domain.exception.ServicioNoDisponibleException;
import com.pragma.pagos.domain.model.EstadoPago;
import com.pragma.pagos.domain.model.Pago;
import com.pragma.pagos.domain.service.RecuperacionFalloService;
import com.pragma.pagos.domain.service.ValidacionPagoService;
import com.pragma.pagos.infrastructure.client.MotorPagosClient;
import com.pragma.pagos.infrastructure.client.ServicioNotificacionClient;
import com.pragma.pagos.infrastructure.client.SistemaAutenticacionClient;
import com.pragma.pagos.infrastructure.producer.EventoPagoProducer;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ChoiceDefinition;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.model.RouteDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Component
public class OrquestadorPago extends RouteBuilder {

    private static final Logger log = LoggerFactory.getLogger(OrquetadorPago.class);
    private static final String ROUTE_ID = "orquestador-pago-principal";
    private static final String HEADER_CORRELATION_ID = "correlationId";
    private static final String HEADER_IDEMPOTENCY_KEY = "idempotencyKey";
    private static final String HEADER_ESTADO = "estadoPago";
    private static final String HEADER_REINTENTO = "reintento";

    private final ValidacionPagoService validacionPagoService;
    private final MotorPagosClient motorPagosClient;
    private final SistemaAutenticacionClient sistemaAutenticacionClient;
    private final ServicioNotificacionClient servicioNotificacionClient;
    private final EventoPagoProducer eventoPagoProducer;
    private final RecuperacionFalloService recuperacionFalloService;

    public OrquestadorPago(
            ValidacionPagoService validacionPagoService,
            MotorPagosClient motorPagosClient,
            SistemaAutenticacionClient sistemaAutenticacionClient,
            ServicioNotificacionClient servicioNotificacionClient,
            EventoPagoProducer eventoPagoProducer,
            RecuperacionFalloService recuperacionFalloService) {
        this.validacionPagoService = validacionPagoService;
        this.motorPagosClient = motorPagosClient;
        this.sistemaAutenticacionClient = sistemaAutenticacionClient;
        this.servicioNotificacionClient = servicioNotificacionClient;
        this.eventoPagoProducer = eventoPagoProducer;
        this.recuperacionFalloService = recuperacionFalloService;
    }

    @Override
    public void configure() {
        configurarExcepcionesGlobales();
        configurarRutaPrincipal();
    }

    private void configurarExcepcionesGlobales() {
        onException(PagoInvalidoException.class)
                .routeId("excepcion-validacion")
                .log(LoggingLevel.ERROR, "Error de validación en orquestación: ${exception.message}")
                .setHeader(HEADER_ESTADO, constant(EstadoPago.RECHAZADO.name()))
                .setBody(constructorRespuestaError("VALIDATION_ERROR", "La solicitud de pago no cumple con los requisitos"))
                .removeHeaders("*")
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400));

        onException(ServicioNoDisponibleException.class)
                .routeId("excepcion-servicio-no-disponible")
                .log(LoggingLevel.ERROR, "Servicio externo no disponible: ${exception.message}")
                .setHeader(HEADER_ESTADO, constant(EstadoPago.FALLIDO.name()))
                .setBody(constructorRespuestaError("SERVICE_UNAVAILABLE", "El servicio requerido no está disponible"))
                .removeHeaders("*")
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(503))
                .to("direct:procesar-fallo");

        onException(Exception.class)
                .routeId("excepcion-generica")
                .log(LoggingLevel.ERROR, "Error inesperado en orquestación: ${exception.message}")
                .setHeader(HEADER_ESTADO, constant(EstadoPago.FALLIDO.name()))
                .setBody(constructorRespuestaError("INTERNAL_ERROR", "Ocurrió un error inesperado"))
                .removeHeaders("*")
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500));
    }

    private void configurarRutaPrincipal() {
        RouteDefinition ruta = from("direct:procesar-pago")
                .routeId(ROUTE_ID)
                .routeProperty("correlationId", header(HEADER_CORRELATION_ID))
                .log(LoggingLevel.INFO, "Iniciando procesamiento de pago con correlationId: ${header.correlationId}");

        aplicarPatronRouterValidacion(ruta);
    }

    private void aplicarPatronRouterValidacion(RouteDefinition ruta) {
        ChoiceDefinition choice = ruta
                .choice()
                .when(header(HEADER_REINTENTO).isNull())
                .log(LoggingLevel.DEBUG, "Ejecutando validación inicial del pago")
                .bean(validacionPagoService, "validarPago")
                .log(LoggingLevel.INFO, "Validación exitosa, continuando con autenticación")
                .otherwise()
                .log(LoggingLevel.INFO, "Procesando reintento ${header.reintento} para correlationId: ${header.correlationId}")
                .bean(validacionPagoService, "revalidarPago");

        encadenarAutenticacion(choice);
    }

    private void encadenarAutenticacion(ChoiceDefinition choice) {
        choice
                .when(header("autenticacionRequerida").isEqualTo(true))
                .log(LoggingLevel.DEBUG, "Autenticando cliente para el pago")
                .bean(sistemaAutenticacionClient, "autenticarCliente")
                .choice()
                .when(header("autenticacionExitosa").isEqualTo(true))
                .log(LoggingLevel.INFO, "Autenticación exitosa, procesando pago")
                .to("direct:procesar-pago-motor")
                .otherwise()
                .log(LoggingLevel.WARN, "Autenticación fallida para el cliente")
                .setHeader(HEADER_ESTADO, constant(EstadoPago.RECHAZADO.name()))
                .setBody(constructorRespuestaError("AUTH_FAILED", "La autenticación del cliente falló"))
                .to("direct:notificar-fallo")
                .end()
                .otherwise()
                .log(LoggingLevel.DEBUG, "Pago no requiere autenticación adicional")
                .to("direct:procesar-pago-motor");
    }

    @CircuitBreaker(name = "motorPagos", fallbackMethod = "fallbackMotorPagos")
    @Retry(name = "reintentoMotorPagos")
    private void procesarConMotorPagos(Exchange exchange) {
        Message mensaje = exchange.getIn();
        String correlationId = mensaje.getHeader(HEADER_CORRELATION_ID, String.class);
        String idempotencyKey = mensaje.getHeader(HEADER_IDEMPOTENCY_KEY, String.class);
        BigDecimal monto = mensaje.getBody(Map.class).get("monto") != null
                ? new BigDecimal(mensaje.getBody(Map.class).get("monto").toString())
                : BigDecimal.ZERO;
        String moneda = mensaje.getBody(Map.class).get("moneda") != null
                ? mensaje.getBody(Map.class).get("moneda").toString()
                : "COP";

        log.info("Invocando motor de pagos para correlationId: {}, monto: {} {}", correlationId, monto, moneda);

        Map<String, Object> respuesta = motorPagosClient.procesarPago(idempotencyKey, monto, moneda);

        String estadoResultado = (String) respuesta.getOrDefault("estado", "FALLIDO");
        mensaje.setHeader(HEADER_ESTADO, estadoResultado);
        mensaje.setBody(respuesta);

        log.info("Respuesta del motor de pagos recibida para correlationId: {}, estado: {}", correlationId, estadoResultado);
    }

    private void manejarRespuestaPago(Exchange exchange) {
        Message mensaje = exchange.getIn();
        String estado = mensaje.getHeader(HEADER_ESTADO, String.class);

        log.info("Procesando respuesta del pago con estado: {}", estado);

        if ("AUTORIZADO".equals(estado)) {
            mensaje.setHeader("notificacionTipo", constant("PAGO_EXITOSO"));
            to("direct:notificar-exito");
        } else if ("RECHAZADO".equals(estado) || "FALLIDO".equals(estado)) {
            mensaje.setHeader("notificacionTipo", constant("PAGO_FALLIDO"));
            to("direct:notificar-fallo");
        } else {
            log.warn("Estado de pago no reconocido: {}", estado);
        }

        eventoPagoProducer.publicarEventoPago(construirEventoPago(mensaje));
    }

    private void notificarExito(Exchange exchange) {
        Message mensaje = exchange.getIn();
        String correlationId = mensaje.getHeader(HEADER_CORRELATION_ID, String.class);

        log.info("Enviando notificación de éxito para correlationId: {}", correlationId);

        try {
            servicioNotificacionClient.enviarNotificacion(
                    correlationId,
                    "PAGO_AUTORIZADO",
                    "Su pago ha sido autorizado exitosamente"
            );
            log.info("Notificación de éxito enviada para correlationId: {}", correlationId);
        } catch (Exception e) {
            log.error("Error al enviar notificación de éxito para correlationId: {}", correlationId, e);
        }
    }

    private void notificarFallo(Exchange exchange) {
        Message mensaje = exchange.getIn();
        String correlationId = mensaje.getHeader(HEADER_CORRELATION_ID, String.class);

        log.info("Enviando notificación de fallo para correlationId: {}", correlationId);

        try {
            servicioNotificacionClient.enviarNotificacion(
                    correlationId,
                    "PAGO_FALLIDO",
                    "Su pago no pudo ser procesado. Por favor contacte soporte."
            );
            log.info("Notificación de fallo enviada para correlationId: {}", correlationId);
        } catch (Exception e) {
            log.error("Error al enviar notificación de fallo para correlationId: {}", correlationId, e);
        }
    }

    private void procesarFallo(Exchange exchange) {
        Message mensaje = exchange.getIn();
        String correlationId = mensaje.getHeader(HEADER_CORRELATION_ID, String.class);
        String idempotencyKey = mensaje.getHeader(HEADER_IDEMPOTENCY_KEY, String.class);

        log.info("Ejecutando recuperación de fallo para correlationId: {}", correlationId);

        recuperacionFalloService.registrarFallo(idempotencyKey, correlationId);

        Map<String, Object> datosPago = mensaje.getBody(Map.class);
        if (datosPago != null && datosPago.containsKey("monto")) {
            boolean reintentoProgramado = recuperacionFalloService.programarReintento(correlationId, datosPago);
            if (reintentoProgramado) {
                log.info("Reintento programado para correlationId: {}", correlationId);
            }
        }
    }

    private Map<String, Object> constructorRespuestaError(String codigo, String mensaje) {
        return Map.of(
                "exito", false,
                "codigoError", codigo,
                "mensaje", mensaje,
                "timestamp", LocalDateTime.now().toString()
        );
    }

    private Map<String, Object> construirEventoPago(Message mensaje) {
        String correlationId = mensaje.getHeader(HEADER_CORRELATION_ID, String.class);
        String estado = mensaje.getHeader(HEADER_ESTADO, String.class);

        return Map.of(
                "correlationId", correlationId != null ? correlationId : UUID.randomUUID().toString(),
                "estado", estado != null ? estado : "DESCONOCIDO",
                "timestamp", LocalDateTime.now().toString(),
                "tipoEvento", "PAGO_PROCESADO"
        );
    }

    private Map<String, Object> fallbackMotorPagos(Exchange exchange, Throwable throwable) {
        log.error("Fallback ejecutado para motor de pagos. Causa: {}", throwable.getMessage());

        Message mensaje = exchange.getIn();
        mensaje.setHeader(HEADER_ESTADO, EstadoPago.FALLIDO.name());

        if (throwable instanceof CamelExecutionException) {
            Throwable caused = throwable.getCause();
            if (caused instanceof ServicioNoDisponibleException) {
                throw (ServicioNoDisponibleException) caused;
            }
        }

        return Map.of(
                "exito", false,
                "estado", "FALLIDO",
                "codigoError", "CIRCUIT_BREAKER_OPEN",
                "mensaje", "El servicio de pago no está disponible temporalmente",
                "timestamp", LocalDateTime.now().toString()
        );
    }

    public Pago ejecutar(Pago pago, String token) {
        // Implementación mínima para compilar
        return pago;
    }

        log.info("Iniciando procesamiento de pago - correlationId: {}, idempotencyKey: {}",
                correlationId, idempotencyKey);

        Map<String, Object> cuerpoPago = Map.of(
                "idempotencyKey", idempotencyKey,
                "monto", monto.toString(),
                "moneda", moneda,
                "clienteId", clienteId,
                "ordenId", ordenId != null ? ordenId : "",
                "descripcion", descripcion != null ? descripcion : ""
        );

        org.apache.camel.ProducerTemplate template = getContext().createProducerTemplate();
        org.apache.camel.Exchange exchange = getContext().getEndpoint("direct:procesar-pago").createExchange();

        exchange.getIn().setHeader(HEADER_CORRELATION_ID, correlationId);
        exchange.getIn().setHeader(HEADER_IDEMPOTENCY_KEY, idempotencyKey);
        exchange.getIn().setHeader("autenticacionRequerida", true);
        exchange.getIn().setBody(cuerpoPago);

        try {
            template.send("direct:procesar-pago", exchange);
            log.info("Procesamiento de pago completado para correlationId: {}", correlationId);
        } catch (Exception e) {
            log.error("Error en procesamiento de pago para correlationId: {}", correlationId, e);
            throw new ServicioNoDisponibleException("Error al procesar el pago: " + e.getMessage());
        }
    }
}