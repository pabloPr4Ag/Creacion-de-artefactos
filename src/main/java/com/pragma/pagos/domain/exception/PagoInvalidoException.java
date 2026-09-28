package com.pragma.pagos.domain.exception;

import java.util.Objects;

/**
 * Excepción personalizada para validaciones de negocio en el dominio de pagos.
 * Proporciona mensajes descriptivos y detalles adicionales para facilitar el diagnóstico.
 */
public final class PagoInvalidoException extends RuntimeException {
    private final String codigoError;
    private final String campo;
    private final transient Object valorRechazado;

    /**
     * Constructor principal para excepciones de validación.
     * @param mensaje Mensaje descriptivo del error
     * @param codigoError Código identificador del error
     * @param campo Campo asociado al error (opcional)
     * @param valorRechazado Valor que causó la validación (opcional)
     */
    public PagoInvalidoException(String mensaje, String codigoError, String campo, Object valorRechazado) {
        super(mensaje);
        this.codigoError = Objects.requireNonNull(codigoError, "El código de error no puede ser nulo");
        this.campo = campo;
        this.valorRechazado = valorRechazado;
    }

    /**
     * Constructor simplificado para mensajes sin detalles adicionales.
     * @param mensaje Mensaje descriptivo del error
     */
    public PagoInvalidoException(String mensaje) {
        this(mensaje, "VALIDACION_NEGOCIO", null, null);
    }

    /**
     * Constructor para excepciones con código de error específico.
     * @param mensaje Mensaje descriptivo del error
     * @param codigoError Código identificador del error
     */
    public PagoInvalidoException(String mensaje, String codigoError) {
        this(mensaje, codigoError, null, null);
    }

    public String getCodigoError() {
        return codigoError;
    }

    public String getCampo() {
        return campo;
    }

    public Object getValorRechazado() {
        return valorRechazado;
    }

    /**
     * Crea una instancia con detalles de campo y valor rechazado.
     * @param mensaje Mensaje descriptivo
     * @param campo Campo asociado
     * @param valorRechazado Valor que falló la validación
     * @return Nueva instancia de PagoInvalidoException
     */
    public static PagoInvalidoException conDetalles(String mensaje, String campo, Object valorRechazado) {
        return new PagoInvalidoException(mensaje, "VALIDACION_DETALLADA", campo, valorRechazado);
    }
}