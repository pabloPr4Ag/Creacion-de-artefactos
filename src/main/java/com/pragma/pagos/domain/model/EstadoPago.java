package com.pragma.pagos.domain.model;

/**
 * Enum que representa los estados posibles de un pago en el flujo de procesamiento.
 * Incluye transiciones válidas entre estados para garantizar consistencia.
 */
public enum EstadoPago {
    PENDIENTE,
    PROCESADO,
    FALLIDO,
    REINTENTO;

    /**
     * Valida si una transición de estado es permitida.
     * @param actual Estado actual del pago
     * @param nuevo Estado al que se quiere transicionar
     * @return true si la transición es válida, false en caso contrario
     */
    public static boolean esTransicionValida(EstadoPago actual, EstadoPago nuevo) {
        if (actual == null || nuevo == null) {
            return false;
        }

        return switch (actual) {
            case PENDIENTE -> nuevo == PROCESADO || nuevo == FALLIDO || nuevo == REINTENTO;
            case PROCESADO -> false; // Estado final
            case FALLIDO -> nuevo == REINTENTO;
            case REINTENTO -> nuevo == PROCESADO || nuevo == FALLIDO;
        };
    }
}