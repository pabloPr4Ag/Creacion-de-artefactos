package com.pragma.pagos.domain.exception;

import java.time.Instant;
import java.util.UUID;

public final class ServicioNoDisponibleException extends RuntimeException {

    private final String codigoError;
    private final String nombreServicio;
    private final String operacion;
    private final Instant timestamp;
    private final UUID correlationId;
    private final boolean reintentable;
    private final int intentosRealizados;
    private final Throwable causaOriginal;

    public ServicioNoDisponibleException(String mensaje, String nombreServicio, String operacion) {
        super(mensaje);
        this.codigoError = "SERVICIO_NO_DISPONIBLE";
        this.nombreServicio = nombreServicio;
        this.operacion = operacion;
        this.timestamp = Instant.now();
        this.correlationId = null;
        this.reintentable = true;
        this.intentosRealizados = 1;
        this.causaOriginal = null;
    }

    public ServicioNoDisponibleException(String mensaje, String nombreServicio, String operacion, Throwable causa) {
        super(mensaje, causa);
        this.codigoError = "SERVICIO_NO_DISPONIBLE";
        this.nombreServicio = nombreServicio;
        this.operacion = operacion;
        this.timestamp = Instant.now();
        this.correlationId = null;
        this.reintentable = true;
        this.intentosRealizados = 1;
        this.causaOriginal = causa;
    }

    public ServicioNoDisponibleException(String mensaje, String codigoError, String nombreServicio,
                                         String operacion, UUID correlationId, boolean reintentable,
                                         int intentosRealizados, Throwable causa) {
        super(mensaje, causa);
        this.codigoError = codigoError;
        this.nombreServicio = nombreServicio;
        this.operacion = operacion;
        this.timestamp = Instant.now();
        this.correlationId = correlationId;
        this.reintentable = reintentable;
        this.intentosRealizados = intentosRealizados;
        this.causaOriginal = causa;
    }

    public String getCodigoError() {
        return codigoError;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public String getOperacion() {
        return operacion;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public UUID getCorrelationId() {
        return correlationId;
    }

    public boolean isReintentable() {
        return reintentable;
    }

    public int getIntentosRealizados() {
        return intentosRealizados;
    }

    public Throwable getCausaOriginal() {
        return causaOriginal;
    }

    public static ServicioNoDisponibleException paraMotorPagos(String operacion, Throwable causa) {
        return new ServicioNoDisponibleException(
                "El motor de pagos no está disponible para la operación: " + operacion,
                "MOTOR_PAGOS",
                operacion,
                causa
        );
    }

    public static ServicioNoDisponibleException paraAutenticacion(String operacion, Throwable causa) {
        return new ServicioNoDisponibleException(
                "El sistema de autenticación no está disponible para la operación: " + operacion,
                "SISTEMA_AUTENTICACION",
                operacion,
                causa
        );
    }

    public static ServicioNoDisponibleException paraNotificaciones(String operacion, Throwable causa) {
        return new ServicioNoDisponibleException(
                "El servicio de notificaciones no está disponible para la operación: " + operacion,
                "SERVICIO_NOTIFICACIONES",
                operacion,
                causa
        );
    }

    public String getMensajeCompleto() {
        StringBuilder sb = new StringBuilder();
        sb.append("ServicioNoDisponibleException{");
        sb.append("codigoError=").append(codigoError);
        sb.append(", nombreServicio=").append(nombreServicio);
        sb.append(", operacion=").append(operacion);
        sb.append(", timestamp=").append(timestamp);
        if (correlationId != null) {
            sb.append(", correlationId=").append(correlationId);
        }
        sb.append(", reintentable=").append(reintentable);
        sb.append(", intentosRealizados=").append(intentosRealizados);
        sb.append(", mensaje='").append(getMessage()).append("'}");
        return sb.toString();
    }

    @Override
    public String toString() {
        return getMensajeCompleto();
    }
}