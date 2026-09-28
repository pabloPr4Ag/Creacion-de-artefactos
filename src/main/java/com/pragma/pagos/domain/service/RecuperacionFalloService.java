package com.pragma.pagos.domain.service;

import com.pragma.pagos.domain.exception.ServicioNoDisponibleException;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

public final class RecuperacionFalloService {

    private static final int MAX_INTENTOS_DEFAULT = 3;
    private static final long INTERVALO_BASE_MILLIS = 1000L;
    private static final double MULTIPLICADOR_BACKOFF = 2.0;
    private static final double JITTER_FACTOR = 0.1;

    private final int maxIntentos;
    private final long intervaloBaseMillis;
    private final double multiplicadorBackoff;
    private final double jitterFactor;

    public RecuperacionFalloService() {
        this(MAX_INTENTOS_DEFAULT, INTERVALO_BASE_MILLIS, MULTIPLICADOR_BACKOFF, JITTER_FACTOR);
    }

    public RecuperacionFalloService(int maxIntentos, long intervaloBaseMillis,
                                    double multiplicadorBackoff, double jitterFactor) {
        this.maxIntentos = validarMaxIntentos(maxIntentos);
        this.intervaloBaseMillis = validarIntervaloBase(intervaloBaseMillis);
        this.multiplicadorBackoff = multiplicadorBackoff;
        this.jitterFactor = jitterFactor;
    }

    public <T> ResultadoRecuperacion<T> ejecutarConRecuperacion(
            Supplier<T> operacion,
            String nombreServicio,
            String operacionNombre,
            UUID correlationId) {

        List<AttemptResult<T>> intentos = new ArrayList<>();
        int intentoActual = 1;
        T resultado = null;
        Exception ultimaExcepcion = null;
        boolean exitoso = false;

        while (intentoActual <= maxIntentos && !exitoso) {
            long tiempoEspera = calcularTiempoEspera(intentoActual);

            if (intentoActual > 1) {
                try {
                    Thread.sleep(tiempoEspera);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return new ResultadoRecuperacion<>(
                            false, null, intentos,
                            new ServicioNoDisponibleException(
                                    "Recuperación interrumpida",
                                    nombreServicio,
                                    operacionNombre,
                                    e
                            )
                    );
                }
            }

            Instant inicioIntento = Instant.now();
            try {
                resultado = operacion.get();
                exitoso = true;
                ultimaExcepcion = null;

                intentos.add(new AttemptResult<>(intentoActual, inicioIntento, Instant.now(),
                        true, resultado, null));

            } catch (Exception e) {
                ultimaExcepcion = e;
                boolean reintentable = esExcepcionReintentable(e);

                intentos.add(new AttemptResult<>(intentoActual, inicioIntento, Instant.now(),
                        false, null, e));

                if (!reintentable || intentoActual >= maxIntentos) {
                    break;
                }
            }

            intentoActual++;
        }

        return new ResultadoRecuperacion<>(exitoso, resultado, intentos, ultimaExcepcion);
    }

    public long calcularTiempoEspera(int numeroIntento) {
        if (numeroIntento <= 1) {
            return 0;
        }

        double exponencial = Math.pow(multiplicadorBackoff, numeroIntento - 2);
        long tiempoBase = (long) (intervaloBaseMillis * exponencial);

        long jitter = (long) (tiempoBase * jitterFactor * (Math.random() * 2 - 1));
        long tiempoFinal = tiempoBase + jitter;

        return Math.max(0, tiempoFinal);
    }

    public <T> T ejecutarConRecuperacionSimple(
            Supplier<T> operacion,
            String nombreServicio,
            String operacionNombre) {

        ResultadoRecuperacion<T> resultado = ejecutarConRecuperacion(
                operacion, nombreServicio, operacionNombre, null
        );

        if (!resultado.isExitoso()) {
            throw new ServicioNoDisponibleException(
                    "Operación fallida después de " + maxIntentos + " intentos: " +
                            resultado.getUltimaExcepcion().getMessage(),
                    nombreServicio,
                    operacionNombre,
                    resultado.getUltimaExcepcion()
            );
        }

        return resultado.getResultado();
    }

    public boolean esExcepcionReintentable(Exception excepcion) {
        if (excepcion == null) {
            return false;
        }

        if (excepcion instanceof ServicioNoDisponibleException servicioNoDisponible) {
            return servicioNoDisponible.isReintentable();
        }

        String nombreClase = excepcion.getClass().getSimpleName().toLowerCase();

        return nombreClase.contains("timeout") ||
                nombreClase.contains("connection") ||
                nombreClase.contains("temporary") ||
                nombreClase.contains("unavailable") ||
                nombreClase.contains("retryable");
    }

    public int getMaxIntentos() {
        return maxIntentos;
    }

    public Duration getIntervaloBase() {
        return Duration.ofMillis(intervaloBaseMillis);
    }

    public double getMultiplicadorBackoff() {
        return multiplicadorBackoff;
    }

    public double getJitterFactor() {
        return jitterFactor;
    }

    private int validarMaxIntentos(int maxIntentos) {
        if (maxIntentos < 1) {
            return 1;
        }
        if (maxIntentos > 10) {
            return 10;
        }
        return maxIntentos;
    }

    private long validarIntervaloBase(long intervalo) {
        if (intervalo < 100) {
            return 100;
        }
        if (intervalo > 30000) {
            return 30000;
        }
        return intervalo;
    }

    public void registrarFallo(String idempotencyKey, String correlationId) {
        // Implementación mínima para compilar
        System.out.println("Registrando fallo para " + idempotencyKey + " / " + correlationId);
    }

    public boolean programarReintento(String correlationId, Map<String, Object> datosPago) {
        // Implementación mínima para compilar
        System.out.println("Programando reintento para " + correlationId);
        return true;
    }        private final T resultado;
        private final List<AttemptResult<T>> intentos;
        private final Exception ultimaExcepcion;

        public ResultadoRecuperacion(boolean exitoso, T resultado,
                                     List<AttemptResult<T>> intentos, Exception ultimaExcepcion) {
            this.exitoso = exitoso;
            this.resultado = resultado;
            this.intentos = List.copyOf(intentos);
            this.ultimaExcepcion = ultimaExcepcion;
        }

        public boolean isExitoso() {
            return exitoso;
        }

        public T getResultado() {
            return resultado;
        }

        public List<AttemptResult<T>> getIntentos() {
            return intentos;
        }

        public Exception getUltimaExcepcion() {
            return ultimaExcepcion;
        }

        public int getNumeroIntentos() {
            return intentos.size();
        }

        public Duration getTiempoTotal() {
            if (intentos.isEmpty()) {
                return Duration.ZERO;
            }

            Instant inicio = intentos.get(0).getInicio();
            Instant fin = intentos.get(intentos.size() - 1).getFin();
            return Duration.between(inicio, fin);
        }
    }

    public static final class AttemptResult<T> {
        private final int numeroIntento;
        private final Instant inicio;
        private final Instant fin;
        private final boolean exitoso;
        private final T resultado;
        private final Exception excepcion;

        public AttemptResult(int numeroIntento, Instant inicio, Instant fin,
                             boolean exitoso, T resultado, Exception excepcion) {
            this.numeroIntento = numeroIntento;
            this.inicio = inicio;
            this.fin = fin;
            this.exitoso = exitoso;
            this.resultado = resultado;
            this.excepcion = excepcion;
        }

        public int getNumeroIntento() {
            return numeroIntento;
        }

        public Instant getInicio() {
            return inicio;
        }

        public Instant getFin() {
            return fin;
        }

        public boolean isExitoso() {
            return exitoso;
        }

        public T getResultado() {
            return resultado;
        }

        public Exception getExcepcion() {
            return excepcion;
        }

        public Duration getDuracion() {
            return Duration.between(inicio, fin);
        }
    }
}