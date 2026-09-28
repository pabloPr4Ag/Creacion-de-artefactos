# Fase 2: Diseño de Artefactos Reusables - Sistema de Pagos

## 1. Identificación del Artefacto Reutilizable
De acuerdo con los requisitos de la Fase 1 y la arquitectura del sistema, se ha identificado la necesidad de un artefacto transversal que gestione la resiliencia y la recuperación de fallos en las comunicaciones con servicios externos.

**Nombre del Artefacto:** `ResilienceOrchestrator` (Artefacto de Gestión de Resiliencia y Reintentos)

### Responsabilidades Específicas:
*   **Aislamiento de Fallos:** Implementar la lógica de *Circuit Breaker* para evitar que la caída de un servicio externo (Motor de Pagos, Autenticación, Notificaciones) afecte la disponibilidad general del sistema.
*   **Recuperación Automática:** Gestionar la política de reintentos mediante *Exponential Backoff* para errores transitorios.
*   **Estandarización de Errores:** Traducir excepciones técnicas de infraestructura en excepciones de dominio (`ServicioNoDisponibleException`).
*   **Trazabilidad:** Asegurar que cada intento de reintento mantenga el `Correlation ID` original para auditoría.

---

## 2. Estructura del Artefacto

### Contratos (Interfaces)
El artefacto se define mediante una interfaz genérica para permitir su uso en cualquier llamada a servicios externos:

```java
public interface ResilienceOrchestrator<T, R> {
    /**
     * Ejecuta una acción protegida por políticas de resiliencia.
     * @param action Funcional que representa la llamada al servicio externo.
     * @param context Metadatos de la transacción (Correlation ID, Idempotency Key).
     * @return Resultado de la operación.
     * @throws ServicioNoDisponibleException si el circuito está abierto o los reintentos fallan.
     */
    R execute(Supplier<R> action, ExecutionContext context);
}
```

### Parámetros
*   **Entrada (`ExecutionContext`):**
    *   `String correlationId`: Para trazabilidad.
    *   `String serviceName`: Nombre del servicio externo (para aplicar configuraciones específicas de Circuit Breaker).
    *   `int maxAttempts`: Número máximo de reintentos permitidos.
*   **Salida:** El tipo de retorno `R` definido por la acción ejecutada.

### Flujo Lógico Interno
1.  **Verificación de Estado:** El artefacto consulta el estado del *Circuit Breaker* para el `serviceName`.
2.  **Ejecución:** Si el circuito está cerrado, ejecuta la `action`.
3.  **Manejo de Errores:**
    *   Si ocurre un error transitorio $\rightarrow$ Aplica espera exponencial $\rightarrow$ Reintenta hasta `maxAttempts`.
    *   Si ocurre un error fatal $\rightarrow$ Lanza excepción inmediata.
4.  **Actualización de Estado:** Reporta el éxito o fallo al *Circuit Breaker* para ajustar los umbrales de apertura.

---

## 3. Diseño de Patrones de Reutilización

### Configuración e Instanciación
*   **Inyección de Dependencias:** El artefacto se registrará como un `@Bean` de Spring, permitiendo que cualquier cliente de infraestructura (`MotorPagosClient`, etc.) lo inyecte.
*   **Configuración Externa:** Las políticas (tiempos de espera, umbrales de falla) se cargarán desde el archivo `application.yml` utilizando `@ConfigurationProperties`.

### Nivel de Acoplamiento
*   **Bajo Acoplamiento:** El artefacto es agnóstico al negocio. No conoce qué es un "Pago" o una "Notificación", solo conoce "Acciones" y "Resultados".
*   **Extensibilidad:** Se puede extender la implementación para soportar diferentes estrategias de reintento (por ejemplo, cambiar *Exponential Backoff* por *Fixed Interval*) sin afectar a los clientes que lo consumen.

---

## 4. Justificación de la Decisión
Se eligió el `ResilienceOrchestrator` como el único artefacto reutilizable debido a que la **Resiliencia (RNF-02)** y la **Recuperación de Fallos (RF-06)** son requisitos críticos transversales. Implementar esta lógica repetidamente en cada cliente de API generaría duplicidad de código y dificultad de mantenimiento. Al centralizarlo, garantizamos que todos los servicios externos se comporten bajo el mismo estándar de estabilidad.
