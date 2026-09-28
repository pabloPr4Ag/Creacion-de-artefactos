package com.pragma.pagos.domain.model;


import com.pragma.pagos.domain.exception.PagoInvalidoException;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Modelo canónico que representa un pago en el dominio.
 * Incluye validaciones de negocio como idempotencia, montos válidos y estados permitidos.
 */
public final class Pago {
    private final UUID id;
    private final String idempotencyKey;
    @NotNull(message = "El monto no puede ser nulo")
    @Positive(message = "El monto debe ser positivo")
    private final BigDecimal monto;
    @NotNull(message = "La moneda no puede ser nula")
    @Size(min = 3, max = 3, message = "La moneda debe tener 3 caracteres")
    private final String moneda;
    @NotNull(message = "El estado no puede ser nulo")
    private final EstadoPago estado;
    private final String correlationId;
    private final LocalDateTime fechaCreacion;
    private final LocalDateTime fechaActualizacion;
    private final String descripcion;
    private final String clienteId;
    private final String ordenId;

    /**
     * Constructor principal con todas las validaciones de negocio.
     * @param idempotencyKey Clave de idempotencia para evitar duplicados
     * @param monto Monto del pago (debe ser positivo)
     * @param moneda Código de moneda (3 caracteres)
     * @param estado Estado inicial del pago
     * @param correlationId Identificador de trazabilidad
     * @param descripcion Descripción opcional del pago
     * @param clienteId Identificador del cliente
     * @param ordenId Identificador de la orden asociada
     * @throws PagoInvalidoException si alguna validación falla
     */
    public Pago(String idempotencyKey, BigDecimal monto, String moneda, EstadoPago estado,
                String correlationId, String descripcion, String clienteId, String ordenId) {
        this.id = UUID.randomUUID();
        this.idempotencyKey = Objects.requireNonNull(idempotencyKey, "La clave de idempotencia no puede ser nula");
        this.monto = Objects.requireNonNull(monto, "El monto no puede ser nulo");
        this.moneda = Objects.requireNonNull(moneda, "La moneda no puede ser nula");
        this.estado = Objects.requireNonNull(estado, "El estado no puede ser nulo");
        this.correlationId = Objects.requireNonNull(correlationId, "El correlationId no puede ser nulo");
        this.descripcion = descripcion;
        this.clienteId = Objects.requireNonNull(clienteId, "El clienteId no puede ser nulo");
        this.ordenId = Objects.requireNonNull(ordenId, "El ordenId no puede ser nulo");

        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new PagoInvalidoException("El monto debe ser positivo");
        }
        if (moneda.length() != 3) {
            throw new PagoInvalidoException("La moneda debe tener exactamente 3 caracteres");
        }

        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }

    /**
     * Constructor para reconstrucción desde persistencia o eventos.
     * @param id Identificador único del pago
     * @param idempotencyKey Clave de idempotencia
     * @param monto Monto del pago
     * @param moneda Código de moneda
     * @param estado Estado del pago
     * @param correlationId Identificador de trazabilidad
     * @param fechaCreacion Fecha de creación
     * @param fechaActualizacion Fecha de última actualización
     * @param descripcion Descripción del pago
     * @param clienteId Identificador del cliente
     * @param ordenId Identificador de la orden
     */
    public Pago(UUID id, String idempotencyKey, BigDecimal monto, String moneda, EstadoPago estado,
                String correlationId, LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion,
                String descripcion, String clienteId, String ordenId) {
        this.id = Objects.requireNonNull(id, "El ID no puede ser nulo");
        this.idempotencyKey = Objects.requireNonNull(idempotencyKey, "La clave de idempotencia no puede ser nula");
        this.monto = Objects.requireNonNull(monto, "El monto no puede ser nulo");
        this.moneda = Objects.requireNonNull(moneda, "La moneda no puede ser nula");
        this.estado = Objects.requireNonNull(estado, "El estado no puede ser nulo");
        this.correlationId = Objects.requireNonNull(correlationId, "El correlationId no puede ser nulo");
        this.fechaCreacion = Objects.requireNonNull(fechaCreacion, "La fecha de creación no puede ser nula");
        this.fechaActualizacion = Objects.requireNonNull(fechaActualizacion, "La fecha de actualización no puede ser nula");
        this.descripcion = descripcion;
        this.clienteId = Objects.requireNonNull(clienteId, "El clienteId no puede ser nulo");
        this.ordenId = Objects.requireNonNull(ordenId, "El ordenId no puede ser nulo");
    }

    public UUID getId() {
        return id;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public String getMoneda() {
        return moneda;
    }

    public EstadoPago getEstado() {
        return estado;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getClienteId() {
        return clienteId;
    }

    public String getOrdenId() {
        return ordenId;
    }

    /**
     * Crea una nueva instancia del pago con un estado actualizado.
     * @param nuevoEstado Nuevo estado del pago
     * @return Nueva instancia de Pago con el estado actualizado
     */
    public Pago conEstado(EstadoPago nuevoEstado) {
        return new Pago(
                this.id,
                this.idempotencyKey,
                this.monto,
                this.moneda,
                nuevoEstado,
                this.correlationId,
                this.fechaCreacion,
                LocalDateTime.now(),
                this.descripcion,
                this.clienteId,
                this.ordenId
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pago pago = (Pago) o;
        return id.equals(pago.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Pago{"
                + "id=" + id
                + ", idempotencyKey='" + idempotencyKey + '\''
                + ", monto=" + monto
                + ", moneda='" + moneda + '\''
                + ", estado=" + estado
                + ", correlationId='" + correlationId + '\''
                + ", fechaCreacion=" + fechaCreacion
                + ", fechaActualizacion=" + fechaActualizacion
                + ", descripcion='" + descripcion + '\''
                + ", clienteId='" + clienteId + '\''
                + ", ordenId='" + ordenId + '\''
                + '}';
    }
}