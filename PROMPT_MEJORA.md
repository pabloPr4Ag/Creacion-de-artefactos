# Prompt para Mejorar el Codigo Base

Copia y pega el contenido del bloque de abajo en un asistente de IA (Claude, ChatGPT)
para obtener un ZIP con el proyecto completo y arrancable.

Si preferis trabajar en tu editor con un agente local (Claude Code, Cursor, Copilot), usa `AGENTS.md` en vez de este archivo: dice lo mismo pero para que escriba los archivos en disco.

## Las dos reglas que no se negocian

1. **Completa el boilerplate.** Todo lo que el proyecto necesita para compilar y arrancar: manifiesto de dependencias, punto de entrada, configuracion, capa de interfaz, y las capas del patron arquitectonico declarado. Eso es andamiaje y es tu trabajo.
2. **NO resuelvas el reto.** Los entregables de las fases son el trabajo de la persona. El hueco pedagogico se deja como esta: el proyecto arranca, pero lo que el reto pide implementar NO esta implementado.

Dicho de otra forma: si algo impide compilar, arreglalo. Si algo es logica de negocio incompleta, validaciones ausentes, un secreto hardcodeado o un patron mejorable, dejalo exactamente como esta — es lo que la persona tiene que encontrar.

## Superficie de practica — NO resuelvas

Estos archivos SON el ejercicio de la persona. No los implementes; deja stubs.

- `src/main/java/com/pragma/pagos/infrastructure/config/CircuitBreakerConfig.java` — El topic pide resiliencia: este archivo es el ejercicio.

## Lo que le falta a este proyecto

Esto NO lo tenes que adivinar: salio de comparar el proyecto contra la arquitectura declarada del reto y de un analisis estatico del codigo. Completalo TODO.

### Referencias colgando en el codigo que si esta

Cada una rompe la compilacion:

- `src/main/java/com/pragma/pagos/application/OrquestadorPago.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/pagos/infrastructure/client/MotorPagosClient.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/pagos/infrastructure/client/SistemaAutenticacionClient.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/pagos/infrastructure/client/ServicioNotificacionClient.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/pagos/application/OrquestadorPago.java` — `EventoPagoProducer.publicarEventoPago`: Se invoca `publicarEventoPago` sobre `EventoPagoProducer`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/pagos/application/OrquestadorPago.java` — `ServicioNotificacionClient.enviarNotificacion`: Se invoca `enviarNotificacion` sobre `ServicioNotificacionClient`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/pagos/application/OrquestadorPago.java` — `RecuperacionFalloService.registrarFallo`: Se invoca `registrarFallo` sobre `RecuperacionFalloService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/pagos/application/OrquestadorPago.java` — `RecuperacionFalloService.programarReintento`: Se invoca `programarReintento` sobre `RecuperacionFalloService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/pagos/infrastructure/client/ServicioNotificacionClient.java` — `NotificacionPago.tipo`: Se invoca `tipo` sobre `NotificacionPago`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/pagos/infrastructure/client/ServicioNotificacionClient.java` — `NotificacionPago.destinatario`: Se invoca `destinatario` sobre `NotificacionPago`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/pagos/infrastructure/client/ServicioNotificacionClient.java` — `NotificacionPago.correlationId`: Se invoca `correlationId` sobre `NotificacionPago`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/pagos/infrastructure/client/ServicioNotificacionClient.java` — `NotificacionPago.titulo`: Se invoca `titulo` sobre `NotificacionPago`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/pagos/infrastructure/client/ServicioNotificacionClient.java` — `NotificacionPago.mensaje`: Se invoca `mensaje` sobre `NotificacionPago`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/pagos/infrastructure/client/ServicioNotificacionClient.java` — `NotificacionPago.pagoId`: Se invoca `pagoId` sobre `NotificacionPago`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/pagos/infrastructure/client/ServicioNotificacionClient.java` — `NotificacionPago.monto`: Se invoca `monto` sobre `NotificacionPago`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/pagos/infrastructure/client/ServicioNotificacionClient.java` — `NotificacionPago.estado`: Se invoca `estado` sobre `NotificacionPago`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/pagos/infrastructure/producer/EventoPagoProducer.java` — `EstadoPago.name`: Se invoca `name` sobre `EstadoPago`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/pagos/domain/service/ValidacionPagoServiceTest.java` — `ValidacionPagoService.validar`: Se invoca `validar` sobre `ValidacionPagoService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/pagos/application/OrquestadorPagoTest.java` — `OrquestadorPago.ejecutar`: Se invoca `ejecutar` sobre `OrquestadorPago`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/pagos/infrastructure/client/MotorPagosClientTest.java` — `MotorPagosClient.consultarEstado`: Se invoca `consultarEstado` sobre `MotorPagosClient`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/pagos/infrastructure/client/MotorPagosClientTest.java` — `MotorPagosClient.procesarReembolso`: Se invoca `procesarReembolso` sobre `MotorPagosClient`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/pagos/infrastructure/client/MotorPagosClientTest.java` — `CircuitBreakerConfig.getMotorPagosUrl`: Se invoca `getMotorPagosUrl` sobre `CircuitBreakerConfig`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `pom.xml` — `org.apache.camel:camel-spring-boot-bom@4.4.0`: org.apache.camel:camel-spring-boot-bom declara la version 4.4.0, pero Maven Central respondio que esa version no existe. Es una version inventada: reemplazala por una version publicada real, o si no se conoce con certeza, usa el mecanismo centralizado del ecosistema (BOM/parent/platform/version catalog) y no declares una version individual.

## Como saber que terminaste

```bash
npx --yes @redocly/cli lint openapi.yaml
```

Ese comando corriendo sin errores es la definicion de "listo".

---

```
## Briefing del reto (autoridad)
Este bloque manda sobre los archivos adjuntos. El stack y el rol salen de AQUÍ, no de un topic genérico ni de markdown placeholder.

### Perfil
Chapter Integración, Especialidad Desarrollador, Tecnología API, Senior

### Brecha de conocimiento
Implementa y crea artefactos reutilizable para el equipo que ayuda en proceso de tareas complejas

### Misión / candidato
Candidato con experiencia en arquitectura SOA y patrones de integración, trabaja en equipo de desarrollo distribuido

### Reto
- Tema: creacion de artefactos
- Seniority: senior-l2
- Tipo: practical
- Título: Diseño y Creación de Artefactos Reusables en Arquitectura SOA
- Tiempo estimado: 20 horas

### Fases (trabajo del HUMANO — PROHIBIDO completarlas)
No implementes estos entregables. Dejalos como hueco pedagógico. El asistente solo materializa el proyecto arrancable para que el participante pueda trabajar.
- Fase 1: Identificación de Requisitos y Restricciones — objetivo: Comprender los requisitos funcionales y no funcionales del dominio, así como las restricciones existentes. — entregable (NO resolver): Documento de requisitos y restricciones del dominio.
- Fase 2: Diseño de Artefactos Reusables — objetivo: Diseñar artefactos que puedan ser reutilizados en diferentes contextos y que cumplan con los requisitos identificados. — entregable (NO resolver): Documento de diseño de artefactos reusables.
- Fase 3: Implementación y Validación de Artefactos — objetivo: Implementar los artefactos diseñados y validar su funcionamiento en el contexto del dominio. — entregable (NO resolver): Artefactos implementados y validados, junto con el reporte de pruebas.
- Fase 4: Refactorización y Optimización — objetivo: Refactorizar y optimizar los artefactos para mejorar su rendimiento y mantenibilidad. — entregable (NO resolver): Artefactos refactorizados y optimizados, junto con la documentación de los cambios realizados.

Eres un asistente experto en análisis, corrección y generación de archivos de cualquier tipo:
código fuente, documentación, hojas de cálculo, documentos Word, configuraciones, entre otros.
Voy a enviarte una cadena de texto que contiene uno o más archivos. Cada archivo está delimitado por un marcador con el siguiente formato:
// === ARCHIVO: ruta/del/archivo.extension ===
o también puede aparecer como:
## === ARCHIVO: ruta/del/archivo.extension ===
Lo que sigue al marcador puede ser:

El contenido real del archivo (código, texto, YAML, etc.)
Una descripción en lenguaje natural de lo que debe contener el archivo


TU TAREA
PASO 0 — ¿Esto es un proyecto o una carcasa?
Antes de extraer archivos, leé el Briefing (si está) y diagnosticá el adjunto.

Es CARCASA si ocurre CUALQUIERA de estas:
- No hay manifiesto de dependencias del stack del briefing (manifest.json de VTEX IO / package.json / pom.xml / build.gradle / requirements.txt / go.mod / *.tf / *.csproj, según corresponda)
- Hay un "binario" que en realidad es un comentario ("no puede ser mostrado como texto plano", placeholder .fig/.docx vacío)
- Los markdowns ya completan entregables de fases posteriores ("se implementó fade-in", lista de áreas ya resuelta)

Si es CARCASA:
- MATERIALIZÁ un proyecto que arranca en el stack del briefing (VTEX IO Store Framework, Angular, Terraform, pytest, Nest, etc.). Incluí manifiesto, punto de entrada y capa de interfaz reales.
- NO copies los markdowns de "solución" como si fueran el producto. Son ruido de generación.
- NO resuelvas las fases del briefing (están marcadas PROHIBIDO). Dejá el hueco pedagógico: el flujo existe, las microinteracciones/calidad/infra que el reto pide NO están hechas.
- Después seguí al PASO 5 (ZIP).

Si es un proyecto REAL (manifiesto + código que compila o arranca):
- Seguí PASO 1 en adelante. 🔴 compilación sí. 🟡 pedagógico no.

PASO 1 — Detección y extracción
Identifica todos los archivos presentes en la cadena. Para cada archivo extrae:

Su ruta completa (ej: src/main/java/com/pragma/Service.java)
Su contenido o descripción

PASO 2 — Clasificación por tipo
Clasifica cada archivo en una de estas categorías:
A) Código fuente (Java, Python, TypeScript, JavaScript, Kotlin, etc.)
B) Configuración / documentación (YAML, properties, Markdown, JSON, txt, etc.)
C) Excel (.xlsx, .xls, .csv)
D) Word (.docx, .doc)
E) Otro tipo de archivo binario o especial
PASO 3 — Clasificación de errores en código fuente

Objetivo prioritario: que el proyecto compile. No corrijas flujo de negocio ni lógica funcional.

Antes de modificar cualquier archivo de código fuente, clasifica cada problema encontrado en una de estas dos categorías:
🔴 ERROR DE COMPILACIÓN — corregir siempre
Son errores que impiden que el proyecto arranque, sin valor pedagógico:

Import faltante o incorrecto
Clase, método o variable referenciada que no existe en ningún archivo del proyecto
Error de sintaxis
Anotación con atributos inválidos
Dependencia ausente en pom.xml, package.json, etc.
Archivo referenciado que no existe y debe ser creado con implementación mínima

→ CORREGIR estos errores.
🟡 PROBLEMA FUNCIONAL O DE CALIDAD — preservar siempre
Son problemas que no impiden compilar. Pueden ser intencionales para el aprendizaje:

Clave secreta hardcodeada ("secret", "password123")
API deprecada que funciona pero tiene reemplazo moderno
Lógica de negocio incorrecta o incompleta
Código redundante o de baja legibilidad
Falta de validaciones en flujo de negocio
Patrones de diseño incorrectos pero funcionales
Concurrencia no segura
Configuración funcional pero no óptima

→ PRESERVAR tal cual. No corregir, no mejorar, no comentar.
PASO 4 — Procesamiento según tipo de archivo
Tipo A — Código fuente
Aplica únicamente las correcciones clasificadas como 🔴 ERROR DE COMPILACIÓN.
No alteres ningún elemento clasificado como 🟡 PROBLEMA FUNCIONAL O DE CALIDAD.
Si falta un archivo referenciado, créalo con la implementación mínima necesaria para compilar.
Tipo B — Configuración / documentación
Extrae el contenido tal cual, sin modificaciones salvo errores evidentes de sintaxis
(ej: YAML mal indentado).
Tipo C — Excel (.xlsx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un archivo Excel funcional con:

Fila de encabezados en negrita con color de fondo distintivo
Columnas con ancho ajustado al contenido
Tipos de dato correctos por columna
Validaciones si la descripción lo indica
Hojas nombradas descriptivamente si hay más de una
Filas de ejemplo si no hay datos reales

Tipo D — Word (.docx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un documento Word funcional con:

Estilos de título (Título 1, Título 2) para jerarquía de secciones
Fuente legible (Calibri o equivalente), tamaño 11-12pt para cuerpo
Márgenes estándar
Tabla de contenido si tiene múltiples secciones
Tablas con encabezados en negrita si aplica

Tipo E — Otro
Genera el archivo con el contenido o estructura más apropiada según la descripción.
PASO 5 — Exportación en ZIP
Empaqueta todos los archivos en un único archivo ZIP descargable respetando exactamente
la estructura de rutas indicada por los marcadores.
El ZIP debe incluir:

Archivos de código con únicamente los errores de compilación corregidos
Archivos de configuración y documentación sin cambios
Archivos nuevos creados para resolver dependencias de compilación faltantes
Archivos Excel y Word generados desde descripción

IMPORTANTE: El ZIP debe estar listo para descargar al finalizar. No preguntes si el usuario
quiere generarlo. Simplemente genera el archivo y proporciona el enlace de descarga; No debes desplegar en el chat el resumen de lo que arreglaste al Zip, solo entregalo.

REGLAS IMPORTANTES

No omitas ningún archivo aunque no tenga errores ni modificaciones
Respeta los nombres y rutas exactas indicadas por los marcadores
Si un archivo no tiene marcador claro, infiere el nombre desde su contenido
Si la cadena contiene solo documentación, placeholders o binarios fake, NO la reproduzcas:
aplicá PASO 0 (materializar el proyecto del briefing). Reproducir la carcasa es un fallo.
No agregues texto después del enlace de descarga del ZIP
No preguntes si el usuario quiere el ZIP: simplemente generalo siempre
Si detectas que falta un archivo de configuración necesario para compilar
(pom.xml, package.json, requirements.txt, build.gradle, etc.), créalo e inclúyelo
inferiendo su contenido desde los imports y frameworks detectados en el código
Nunca corrijas problemas 🟡 aunque parezcan obvios o fáciles de mejorar.
El participante que recibirá este proyecto los debe encontrar y resolver él mismo.


INPUT
Aquí está la cadena con los archivos:

// === ARCHIVO: pom.xml ===
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.6</version>
        <relativePath/>
    </parent>

    <groupId>com.pragma</groupId>
    <artifactId>pagos</artifactId>
    <version>1.0.0</version>
    <name>pagos</name>
    <description>Plataforma de gestión de pagos en línea</description>

    <properties>
        <java.version>21</java.version>
        <camel.version>4.4.0</camel.version>
        <resilience4j.version>2.1.0</resilience4j.version>
    </properties>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.apache.camel</groupId>
                <artifactId>camel-spring-boot-bom</artifactId>
                <version>${camel.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <dependencies>
        <!-- Spring Boot -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.kafka</groupId>
            <artifactId>spring-kafka</artifactId>
            <version>3.2.0</version>
        </dependency>

        <!-- Apache Camel -->
        <dependency>
            <groupId>org.apache.camel</groupId>
            <artifactId>camel-spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.camel</groupId>
            <artifactId>camel-jackson</artifactId>
        </dependency>

        <!-- Resilience4j -->
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-spring-boot2</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>

        <!-- Lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.32</version>
            <scope>provided</scope>
        </dependency>

        <!-- Testing -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.apache.camel</groupId>
            <artifactId>camel-test-spring</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <version>5.5.0</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>21</source>
                    <target>21</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>

// === ARCHIVO: src/main/java/com/pragma/pagos/PagosApplication.java ===
package com.pragma.pagos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import com.pragma.pagos.infrastructure.config.CircuitBreakerConfig;

@SpringBootApplication
@Import(CircuitBreakerConfig.class)
public class PagosApplication {
    public static void main(String[] args) {
        SpringApplication.run(PagosApplication.class, args);
    }
}

// === ARCHIVO: src/main/resources/application.yml ===
spring:
  application:
    name: pagos
  kafka:
    bootstrap-servers: ${KAFKA_BOOTSTRAP_SERVERS:localhost:9092}
    consumer:
      group-id: pagos-group
      auto-offset-reset: earliest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer

camel:
  springboot:
    main-run-controller: true
    name: pagos-camel
  component:
    kafka:
      brokers: ${KAFKA_BOOTSTRAP_SERVERS:localhost:9092}
      groupId: pagos-group

resilience4j:
  circuitbreaker:
    configs:
      default:
        slidingWindowType: COUNT_BASED
        slidingWindowSize: 10
        minimumNumberOfCalls: 5
        permittedNumberOfCallsInHalfOpenState: 3
        automaticTransitionFromOpenToHalfOpenEnabled: true
        waitDurationInOpenState: 5s
        failureRateThreshold: 50
        eventConsumerBufferSize: 10
        recordExceptions:
          - org.springframework.web.client.HttpServerErrorException
          - java.io.IOException
          - java.util.concurrent.TimeoutException
    instances:
      motorPagos:
        baseConfig: default
        waitDurationInOpenState: 10s
      autenticacion:
        baseConfig: default
      notificacion:
        baseConfig: default
  retry:
    configs:
      default:
        maxRetryAttempts: 3
        waitDuration: 1s
        retryExceptions:
          - org.springframework.web.client.HttpServerErrorException
          - java.io.IOException
          - java.util.concurrent.TimeoutException
    instances:
      motorPagos:
        baseConfig: default
      autenticacion:
        baseConfig: default
      notificacion:
        baseConfig: default

services:
  endpoints:
    motorPagos: ${MOTOR_PAGOS_ENDPOINT:http://localhost:8081/api/pagos}
    autenticacion: ${AUTH_ENDPOINT:http://localhost:8082/api/auth}
    notificacion: ${NOTIFICACION_ENDPOINT:http://localhost:8083/api/notifications}

logging:
  level:
    org.apache.camel: INFO
    com.pragma.pagos: DEBUG
    org.springframework: INFO
    io.github.resilience4j: DEBUG

// === ARCHIVO: src/main/java/com/pragma/pagos/domain/model/Pago.java ===
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

// === ARCHIVO: src/main/java/com/pragma/pagos/domain/model/EstadoPago.java ===
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

// === ARCHIVO: src/main/java/com/pragma/pagos/domain/exception/PagoInvalidoException.java ===
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

// === ARCHIVO: src/main/java/com/pragma/pagos/domain/exception/ServicioNoDisponibleException.java ===
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

// === ARCHIVO: src/main/java/com/pragma/pagos/domain/service/ValidacionPagoService.java ===
package com.pragma.pagos.domain.service;

import com.pragma.pagos.domain.exception.PagoInvalidoException;
import com.pragma.pagos.domain.model.EstadoPago;
import com.pragma.pagos.domain.model.Pago;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

public final class ValidacionPagoService {

    private static final Pattern IDEMPOTENCY_KEY_PATTERN = Pattern.compile(
        "^[A-Za-z0-9\\-_]{8,64}$"
    );

    private static final Pattern CLIENTE_ID_PATTERN = Pattern.compile(
        "^[A-Za-z0-9]{1,36}$"
    );

    private static final Pattern ORDEN_ID_PATTERN = Pattern.compile(
        "^[A-Za-z0-9\\-_]{1,48}$"
    );

    private static final BigDecimal MONTO_MINIMO = new BigDecimal("0.01");
    private static final BigDecimal MONTO_MAXIMO = new BigDecimal("999999.99");

    private static final List<String> MONEDAS_SOPORTADAS = List.of(
        "USD", "EUR", "COP", "MXN", "ARS", "BRL", "CLP", "PEN"
    );

    private static final int DESCRIPCION_MAX_LENGTH = 500;
    private static final int DESCRIPCION_MIN_LENGTH = 3;

    public ValidacionPagoService() {
    }

    public void validarPago(Pago pago) {
        List<String> errores = new ArrayList<>();

        validarIdempotencyKey(pago.getIdempotencyKey(), errores);
        validarMonto(pago.getMonto(), errores);
        validarMoneda(pago.getMoneda(), errores);
        validarClienteId(pago.getClienteId(), errores);
        validarOrdenId(pago.getOrdenId(), errores);
        validarDescripcion(pago.getDescripcion(), errores);

        if (!errores.isEmpty()) {
            throw PagoInvalidoException.conDetalles(
                "El pago no pasó la validación: " + String.join("; ", errores),
                "pago",
                pago
            );
        }
    }

    public void validarIdempotencyKey(String idempotencyKey, List<String> errores) {
        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            errores.add("La clave de idempotencia es obligatoria");
            return;
        }

        if (!IDEMPOTENCY_KEY_PATTERN.matcher(idempotencyKey).matches()) {
            errores.add("La clave de idempotencia debe tener entre 8 y 64 caracteres alfanuméricos, guiones o guiones bajos");
        }

        if (idempotencyKey.length() < 8) {
            errores.add("La clave de idempotencia es demasiado corta (mínimo 8 caracteres)");
        }

        if (idempotencyKey.length() > 64) {
            errores.add("La clave de idempotencia es demasiado larga (máximo 64 caracteres)");
        }
    }

    public void validarMonto(BigDecimal monto, List<String> errores) {
        if (monto == null) {
            errores.add("El monto es obligatorio");
            return;
        }

        if (monto.compareTo(MONTO_MINIMO) < 0) {
            errores.add("El monto debe ser mayor o igual a " + MONTO_MINIMO);
        }

        if (monto.compareTo(MONTO_MAXIMO) > 0) {
            errores.add("El monto debe ser menor o igual a " + MONTO_MAXIMO);
        }

        if (monto.scale() > 2) {
            errores.add("El monto no puede tener más de 2 decimales");
        }

        if (monto.compareTo(BigDecimal.ZERO) == 0) {
            errores.add("El monto debe ser mayor a cero");
        }
    }

    public void validarMoneda(String moneda, List<String> errores) {
        if (moneda == null || moneda.isBlank()) {
            errores.add("La moneda es obligatoria");
            return;
        }

        String monedaUpper = moneda.toUpperCase();
        if (!MONEDAS_SOPORTADAS.contains(monedaUpper)) {
            errores.add("La moneda '" + moneda + "' no está soportada. Monedas válidas: " + MONEDAS_SOPORTADAS);
        }
    }

    public void validarClienteId(String clienteId, List<String> errores) {
        if (clienteId == null || clienteId.isBlank()) {
            errores.add("El ID del cliente es obligatorio");
            return;
        }

        if (!CLIENTE_ID_PATTERN.matcher(clienteId).matches()) {
            errores.add("El ID del cliente debe contener solo caracteres alfanuméricos (máximo 36 caracteres)");
        }
    }

    public void validarOrdenId(String ordenId, List<String> errores) {
        if (ordenId == null || ordenId.isBlank()) {
            errores.add("El ID de la orden es obligatorio");
            return;
        }

        if (!ORDEN_ID_PATTERN.matcher(ordenId).matches()) {
            errores.add("El ID de la orden contiene caracteres inválidos (máximo 48 caracteres, permite guiones y guiones bajos)");
        }
    }

    public void validarDescripcion(String descripcion, List<String> errores) {
        if (descripcion != null && !descripcion.isBlank()) {
            if (descripcion.length() < DESCRIPCION_MIN_LENGTH) {
                errores.add("La descripción debe tener al menos " + DESCRIPCION_MIN_LENGTH + " caracteres");
            }
            if (descripcion.length() > DESCRIPCION_MAX_LENGTH) {
                errores.add("La descripción no puede exceder " + DESCRIPCION_MAX_LENGTH + " caracteres");
            }
        }
    }

    public boolean esIdempotencyKeyValida(String idempotencyKey) {
        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            return false;
        }
        return IDEMPOTENCY_KEY_PATTERN.matcher(idempotencyKey).matches();
    }

    public boolean esMonedaSoportada(String moneda) {
        if (moneda == null || moneda.isBlank()) {
            return false;
        }
        return MONEDAS_SOPORTADAS.contains(moneda.toUpperCase());
    }

    public boolean esMontoValido(BigDecimal monto) {
        if (monto == null) {
            return false;
        }
        return monto.compareTo(MONTO_MINIMO) >= 0 
            && monto.compareTo(MONTO_MAXIMO) <= 0
            && monto.compareTo(BigDecimal.ZERO) > 0
            && monto.scale() <= 2;
    }

    public void validarTransicionEstado(EstadoPago estadoActual, EstadoPago nuevoEstado, List<String> errores) {
        if (estadoActual == null || nuevoEstado == null) {
            errores.add("El estado actual y el nuevo estado son obligatorios");
            return;
        }

        if (!EstadoPago.esTransicionValida(estadoActual, nuevoEstado)) {
            errores.add("Transición de estado inválida: de " + estadoActual + " a " + nuevoEstado);
        }
    }

    public void validarCorrelationId(String correlationId, List<String> errores) {
        if (correlationId == null || correlationId.isBlank()) {
            errores.add("El correlation ID es obligatorio para trazabilidad");
            return;
        }

        try {
            UUID.fromString(correlationId);
        } catch (IllegalArgumentException e) {
            errores.add("El correlation ID debe ser un UUID válido");
        }
    }

    public List<String> obtenerMonedasSoportadas() {
        return new ArrayList<>(MONEDAS_SOPORTADAS);
    }

    public BigDecimal getMontoMinimo() {
        return MONTO_MINIMO;
    }

    public BigDecimal getMontoMaximo() {
        return MONTO_MAXIMO;
    }
}

// === ARCHIVO: src/main/java/com/pragma/pagos/domain/service/RecuperacionFalloService.java ===
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

    public static final class ResultadoRecuperacion<T> {
        private final boolean exitoso;
        private final T resultado;
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

// === ARCHIVO: src/main/resources/schemas/pago-request.xsd ===
<?xml version="1.0" encoding="UTF-8"?>
<xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema"
           xmlns:tns="http://pragma.com/pagos/request"
           targetNamespace="http://pragma.com/pagos/request"
           elementFormDefault="qualified"
           attributeFormDefault="unqualified">

    <xs:element name="PagoRequest" type="tns:PagoRequestType"/>

    <xs:complexType name="PagoRequestType">
        <xs:sequence>
            <xs:element name="idempotencyKey" type="xs:string" minOccurs="1" maxOccurs="1">
                <xs:annotation>
                    <xs:documentation>Clave de idempotencia para evitar pagos duplicados. Formato UUID recomendado.</xs:documentation>
                </xs:annotation>
            </xs:element>
            <xs:element name="monto" type="tns:MontoType" minOccurs="1" maxOccurs="1"/>
            <xs:element name="clienteId" type="xs:string" minOccurs="1" maxOccurs="1">
                <xs:annotation>
                    <xs:documentation>Identificador único del cliente que realiza el pago.</xs:documentation>
                </xs:annotation>
            </xs:element>
            <xs:element name="ordenId" type="xs:string" minOccurs="0" maxOccurs="1">
                <xs:annotation>
                    <xs:documentation>Identificador de la orden asociada al pago. Opcional para pagos directos.</xs:documentation>
                </xs:annotation>
            </xs:element>
            <xs:element name="descripcion" type="xs:string" minOccurs="0" maxOccurs="1">
                <xs:annotation>
                    <xs:documentation>Descripción libre del propósito del pago.</xs:documentation>
                </xs:annotation>
            </xs:element>
            <xs:element name="metadata" type="tns:MetadataType" minOccurs="0" maxOccurs="1">
                <xs:annotation>
                    <xs:documentation>Metadatos adicionales para el pago.</xs:documentation>
                </xs:annotation>
            </xs:element>
        </xs:sequence>
    </xs:complexType>

    <xs:complexType name="MontoType">
        <xs:sequence>
            <xs:element name="valor" type="xs:decimal" minOccurs="1" maxOccurs="1">
                <xs:annotation>
                    <xs:documentation>Valor numérico del pago con hasta 2 decimales.</xs:documentation>
                </xs:annotation>
            </xs:element>
            <xs:element name="moneda" type="tns:MonedaType" minOccurs="1" maxOccurs="1"/>
        </xs:sequence>
    </xs:complexType>

    <xs:simpleType name="MonedaType">
        <xs:restriction base="xs:string">
            <xs:enumeration value="COP">
                <xs:annotation>
                    <xs:documentation>Peso Colombiano</xs:documentation>
                </xs:annotation>
            </xs:enumeration>
            <xs:enumeration value="USD">
                <xs:annotation>
                    <xs:documentation>Dólar Estadounidense</xs:documentation>
                </xs:annotation>
            </xs:enumeration>
            <xs:enumeration value="EUR">
                <xs:annotation>
                    <xs:documentation>Euro</xs:documentation>
                </xs:annotation>
            </xs:enumeration>
        </xs:restriction>
    </xs:simpleType>

    <xs:complexType name="MetadataType">
        <xs:sequence>
            <xs:element name="entry" type="tns:MetadataEntryType" minOccurs="0" maxOccurs="unbounded"/>
        </xs:sequence>
    </xs:complexType>

    <xs:complexType name="MetadataEntryType">
        <xs:sequence>
            <xs:element name="key" type="xs:string" minOccurs="1" maxOccurs="1"/>
            <xs:element name="value" type="xs:string" minOccurs="1" maxOccurs="1"/>
        </xs:sequence>
    </xs:complexType>

    <xs:annotation>
        <xs:documentation>Esquema XSD para validación de requests de pago en la plataforma Pragma.</xs:documentation>
    </xs:annotation>
</xs:schema>

// === ARCHIVO: src/main/resources/schemas/pago-response.xsd ===
<?xml version="1.0" encoding="UTF-8"?>
<xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema"
           xmlns:tns="http://pragma.com/pagos/response"
           targetNamespace="http://pragma.com/pagos/response"
           elementFormDefault="qualified"
           attributeFormDefault="unqualified">

    <xs:element name="PagoResponse" type="tns:PagoResponseType"/>

    <xs:complexType name="PagoResponseType">
        <xs:sequence>
            <xs:element name="id" type="xs:string" minOccurs="1" maxOccurs="1">
                <xs:annotation>
                    <xs:documentation>Identificador único del pago generado por el sistema.</xs:documentation>
                </xs:annotation>
            </xs:element>
            <xs:element name="idempotencyKey" type="xs:string" minOccurs="1" maxOccurs="1">
                <xs:annotation>
                    <xs:documentation>Clave de idempotencia originales del request.</xs:documentation>
                </xs:annotation>
            </xs:element>
            <xs:element name="monto" type="tns:MontoType" minOccurs="1" maxOccurs="1"/>
            <xs:element name="estado" type="tns:EstadoPagoType" minOccurs="1" maxOccurs="1"/>
            <xs:element name="correlationId" type="xs:string" minOccurs="1" maxOccurs="1">
                <xs:annotation>
                    <xs:documentation>ID de correlación para trazabilidad distribuida.</xs:documentation>
                </xs:annotation>
            </xs:element>
            <xs:element name="fechaCreacion" type="xs:dateTime" minOccurs="1" maxOccurs="1">
                <xs:annotation>
                    <xs:documentation>Fecha y hora de creación del pago en formato ISO 8601.</xs:documentation>
                </xs:annotation>
            </xs:element>
            <xs:element name="fechaActualizacion" type="xs:dateTime" minOccurs="1" maxOccurs="1">
                <xs:annotation>
                    <xs:documentation>Fecha y hora de última actualización del pago.</xs:documentation>
                </xs:annotation>
            </xs:element>
            <xs:element name="descripcion" type="xs:string" minOccurs="0" maxOccurs="1"/>
            <xs:element name="clienteId" type="xs:string" minOccurs="1" maxOccurs="1"/>
            <xs:element name="ordenId" type="xs:string" minOccurs="0" maxOccurs="1"/>
            <xs:element name="errores" type="tns:ErroresType" minOccurs="0" maxOccurs="1">
                <xs:annotation>
                    <xs:documentation>Lista de errores si el pago falló.</xs:documentation>
                </xs:annotation>
            </xs:element>
        </xs:sequence>
    </xs:complexType>

    <xs:complexType name="MontoType">
        <xs:sequence>
            <xs:element name="valor" type="xs:decimal" minOccurs="1" maxOccurs="1"/>
            <xs:element name="moneda" type="xs:string" minOccurs="1" maxOccurs="1"/>
        </xs:sequence>
    </xs:complexType>

    <xs:simpleType name="EstadoPagoType">
        <xs:restriction base="xs:string">
            <xs:enumeration value="PENDIENTE">
                <xs:annotation>
                    <xs:documentation>Pago creado pero aún no procesado.</xs:documentation>
                </xs:annotation>
            </xs:enumeration>
            <xs:enumeration value="AUTORIZADO">
                <xs:annotation>
                    <xs:documentation>Pago autorizado por el motor de pagos.</xs:documentation>
                </xs:annotation>
            </xs:enumeration>
            <xs:enumeration value="RECHAZADO">
                <xs:annotation>
                    <xs:documentation>Pago rechazado por el motor de pagos.</xs:documentation>
                </xs:annotation>
            </xs:enumeration>
            <xs:enumeration value="FALLIDO">
                <xs:annotation>
                    <xs:documentation>Pago falló por error técnico.</xs:documentation>
                </xs:annotation>
            </xs:enumeration>
            <xs:enumeration value="PROCESANDO">
                <xs:annotation>
                    <xs:documentation>Pago en proceso de ejecución.</xs:documentation>
                </xs:annotation>
            </xs:enumeration>
        </xs:restriction>
    </xs:simpleType>

    <xs:complexType name="ErroresType">
        <xs:sequence>
            <xs:element name="error" type="tns:ErrorType" minOccurs="0" maxOccurs="unbounded"/>
        </xs:sequence>
    </xs:complexType>

    <xs:complexType name="ErrorType">
        <xs:sequence>
            <xs:element name="codigo" type="xs:string" minOccurs="1" maxOccurs="1">
                <xs:annotation>
                    <xs:documentation>Código de error técnico.</xs:documentation>
                </xs:annotation>
            </xs:element>
            <xs:element name="mensaje" type="xs:string" minOccurs="1" maxOccurs="1">
                <xs:annotation>
                    <xs:documentation>Descripción legible del error.</xs:documentation>
                </xs:annotation>
            </xs:element>
            <xs:element name="campo" type="xs:string" minOccurs="0" maxOccurs="1">
                <xs:annotation>
                    <xs:documentation>Campo que provocó el error si aplica.</xs:documentation>
                </xs:annotation>
            </xs:element>
        </xs:sequence>
    </xs:complexType>

    <xs:annotation>
        <xs:documentation>Esquema XSD para validación de respuestas del motor de pagos.</xs:documentation>
    </xs:annotation>
</xs:schema>

// === ARCHIVO: src/main/java/com/pragma/pagos/application/OrquestadorPago.java ===
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

    public void iniciarProcesamiento(String idempotencyKey, BigDecimal monto, String moneda, 
                                       String clienteId, String ordenId, String descripcion) {
        String correlationId = UUID.randomUUID().toString();

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

// === ARCHIVO: src/main/java/com/pragma/pagos/infrastructure/client/MotorPagosClient.java ===
package com.pragma.pagos.infrastructure.client;

import com.pragma.pagos.domain.exception.PagoInvalidoException;
import com.pragma.pagos.domain.exception.ServicioNoDisponibleException;
import com.pragma.pagos.domain.model.EstadoPago;
import com.pragma.pagos.domain.model.Pago;
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

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Component
public class MotorPagosClient {

    private static final Logger log = LoggerFactory.getLogger(MotorPagosClient.class);
    private static final String SERVICIO = "MotorPagos";

    private final RestTemplate restTemplate;
    private final String endpointBase;
    private final int timeoutSegundos;

    public MotorPagosClient(
            @Value("${integracion.motor-pagos.url:http://localhost:8081/api/pagos}") String endpointBase,
            @Value("${integracion.motor-pagos.timeout:30}") int timeoutSegundos) {
        this.restTemplate = new RestTemplate();
        this.endpointBase = endpointBase;
        this.timeoutSegundos = timeoutSegundos;
    }

    public Pago procesarPago(Pago pago) {
        log.info("Iniciando procesamiento de pago con idempotencyKey={} hacia {}", 
                pago.getIdempotencyKey(), SERVICIO);

        validarPagoParaEnvio(pago);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Idempotency-Key", pago.getIdempotencyKey());
        headers.set("X-Correlation-Id", pago.getCorrelationId());

        Map<String, Object> body = Map.of(
                "monto", pago.getMonto().toPlainString(),
                "moneda", pago.getMoneda(),
                "clienteId", pago.getClienteId(),
                "ordenId", pago.getOrdenId(),
                "descripcion", pago.getDescripcion()
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> respuesta = restTemplate.exchange(
                    endpointBase + "/procesar",
                    HttpMethod.POST,
                    request,
                    Map.class
            ).getBody();

            log.info("Pago procesado exitosamente en {}. TransactionId={}", 
                    SERVICIO, respuesta.get("transactionId"));

            return construirPagoDesdeRespuesta(respuesta, pago);

        } catch (HttpClientErrorException e) {
            log.error("Error HTTP del {}: status={}, body={}", 
                    SERVICIO, e.getStatusCode(), e.getResponseBodyAsString());
            throw mapearErrorHttp(e, "procesarPago");
        } catch (Exception e) {
            log.error("Error inesperado al procesar pago en {}: {}", SERVICIO, e.getMessage(), e);
            throw new ServicioNoDisponibleException(
                    "Error de comunicación con " + SERVICIO + ": " + e.getMessage(),
                    "SERVICIO_INDISPONIBLE",
                    SERVICIO
            );
        }
    }

    public EstadoPago consultarEstadoPago(UUID pagoId, String correlationId) {
        log.info("Consultando estado de pago {} en {}", pagoId, SERVICIO);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Correlation-Id", correlationId);

        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> respuesta = restTemplate.exchange(
                    endpointBase + "/" + pagoId + "/estado",
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    Map.class
            ).getBody();

            String estadoStr = (String) respuesta.get("estado");
            EstadoPago estado = EstadoPago.valueOf(estadoStr);

            log.info("Estado del pago {} en {}: {}", pagoId, SERVICIO, estado);
            return estado;

        } catch (HttpClientErrorException e) {
            log.error("Error HTTP al consultar estado en {}: status={}", 
                    SERVICIO, e.getStatusCode());
            throw mapearErrorHttp(e, "consultarEstadoPago");
        } catch (Exception e) {
            log.error("Error inesperado al consultar estado en {}: {}", SERVICIO, e.getMessage(), e);
            throw new ServicioNoDisponibleException(
                    "Error al consultar estado en " + SERVICIO,
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

    private void validarPagoParaEnvio(Pago pago) {
        if (pago.getMonto() == null || pago.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
            throw PagoInvalidoException.conDetalles(
                    "El monto del pago debe ser mayor a cero",
                    "monto",
                    pago.getMonto()
            );
        }
        if (pago.getMoneda() == null || pago.getMoneda().isBlank()) {
            throw PagoInvalidoException.conDetalles(
                    "La moneda es requerida",
                    "moneda",
                    pago.getMoneda()
            );
        }
        if (pago.getClienteId() == null || pago.getClienteId().isBlank()) {
            throw PagoInvalidoException.conDetalles(
                    "El ID del cliente es requerido",
                    "clienteId",
                    pago.getClienteId()
            );
        }
    }

    private Pago construirPagoDesdeRespuesta(Map<String, Object> respuesta, Pago pagoOriginal) {
        UUID transactionId = UUID.fromString((String) respuesta.get("transactionId"));
        String estadoStr = (String) respuesta.get("estado");
        EstadoPago estado = EstadoPago.valueOf(estadoStr);

        return new Pago(
                transactionId,
                pagoOriginal.getIdempotencyKey(),
                pagoOriginal.getMonto(),
                pagoOriginal.getMoneda(),
                estado,
                pagoOriginal.getCorrelationId(),
                pagoOriginal.getFechaCreacion(),
                java.time.LocalDateTime.now(),
                pagoOriginal.getDescripcion(),
                pagoOriginal.getClienteId(),
                pagoOriginal.getOrdenId()
        );
    }

    private RuntimeException mapearErrorHttp(HttpClientErrorException e, String operacion) {
        HttpStatusCode status = e.getStatusCode();
        String cuerpo = e.getResponseBodyAsString();

        if (status.value() == 400) {
            return new PagoInvalidoException(
                    "Datos inválidos enviados al " + SERVICIO + ": " + cuerpo,
                    "DATOS_INVALIDOS",
                    "request",
                    cuerpo
            );
        } else if (status.value() == 401) {
            return new PagoInvalidoException(
                    "Autenticación fallida con " + SERVICIO,
                    "AUTH_FALLIDA",
                    "credenciales",
                    null
            );
        } else if (status.value() == 404) {
            return new PagoInvalidoException(
                    "Recurso no encontrado en " + SERVICIO,
                    "RECURSO_NO_ENCONTRADO",
                    operacion,
                    null
            );
        } else if (status.value() == 409) {
            return new PagoInvalidoException(
                    "Conflicto de idempotencia en " + SERVICIO + ": el pago ya fue procesado",
                    "IDEMPOTENCIA_CONFlicto",
                    "idempotencyKey",
                    null
            );
        } else {
            return new ServicioNoDisponibleException(
                    "Error de " + SERVICIO + " (status " + status + ": " + cuerpo + ")",
                    "ERROR_SERVICIO_EXTERNO",
                    SERVICIO
            );
        }
    }
}

// === ARCHIVO: src/main/java/com/pragma/pagos/infrastructure/client/SistemaAutenticacionClient.java ===
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

// === ARCHIVO: src/main/java/com/pragma/pagos/infrastructure/client/ServicioNotificacionClient.java ===
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

    private RuntimeException mapearErrorHttp(HttpClientErrorException e, NotificacionPago notificacion) {
        HttpStatusCode status = e.getStatusCode();
        String cuerpo = e.getResponseBodyAsString();

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

// === ARCHIVO: src/main/java/com/pragma/pagos/infrastructure/producer/EventoPagoProducer.java ===
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

    private String serializar(Map<String, Object> evento) {
        try {
            return objectMapper.writeValueAsString(evento);
        } catch (JsonProcessingException e) {
            log.error("Error al serializar evento: {}", e.getMessage(), e);
            return null;
        }
    }
}

// === ARCHIVO: src/main/java/com/pragma/pagos/infrastructure/consumer/EventoPagoConsumer.java ===
package com.pragma.pagos.infrastructure.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.pagos.domain.model.EstadoPago;
import com.pragma.pagos.domain.model.Pago;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventoPagoConsumer {

    private static final String TOPIC_PAGOS = "pagos.eventos";
    private static final String TOPIC_DLQ = "pagos.dlq";
    private static final String TOPIC_REINTENTOS = "pagos.reintentos";

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = TOPIC_PAGOS, groupId = "pagos-consumer-group")
    public void consumirEventoPago(String mensaje) {
        log.info("Recibido evento del topic {}: {}", TOPIC_PAGOS, mensaje);

        Map<String, Object> evento = deserializar(mensaje);
        if (evento == null) {
            log.error("No se pudo deserializar el mensaje, enviando a DLQ");
            enviarADLQ(mensaje, "DESERIALIZACION_FALLIDA");
            return;
        }

        String eventType = (String) evento.get("eventType");
        String correlationId = (String) evento.get("correlationId");

        log.info("Procesando evento tipo: {}, correlationId: {}", eventType, correlationId);

        try {
            switch (eventType) {
                case "ESTADO_CAMBIADO" -> procesarCambioEstado(evento);
                case "PAGO_CREADO" -> procesarPagoCreado(evento);
                default -> log.warn("Tipo de evento desconocido: {}", eventType);
            }
        } catch (Exception e) {
            log.error("Error al procesar evento {}: {}", eventType, e.getMessage(), e);
            manejarError(evento, e);
        }
    }

    @KafkaListener(topics = TOPIC_REINTENTOS, groupId = "pagos-reintento-group")
    public void consumirReintento(String mensaje) {
        log.info("Recibido evento de reintento del topic {}", TOPIC_REINTENTOS);

        Map<String, Object> evento = deserializar(mensaje);
        if (evento == null) {
            log.error("No se pudo deserializar mensaje de reintento");
            return;
        }

        Integer numeroReintento = (Integer) evento.get("numeroReintento");
        int maxReintentos = 3;

        if (numeroReintento != null && numeroReintento >= maxReintentos) {
            log.warn("Máximo de reintentos alcanzado para evento, enviando a DLQ");
            enviarADLQ(mensaje, "MAX_REINTENTOS_ALCANZADOS");
            return;
        }

        String eventType = (String) evento.get("eventType");
        log.info("Reintentando procesamiento de evento tipo: {}, intento: {}", 
            eventType, numeroReintento != null ? numeroReintento + 1 : 1);

        try {
            switch (eventType) {
                case "ESTADO_CAMBIADO" -> procesarCambioEstado(evento);
                case "PAGO_CREADO" -> procesarPagoCreado(evento);
                default -> log.warn("Tipo de evento desconocido en reintento: {}", eventType);
            }
        } catch (Exception e) {
            log.error("Reintento fallido para evento {}: {}", eventType, e.getMessage(), e);
        }
    }

    private void procesarCambioEstado(Map<String, Object> evento) {
        String pagoId = (String) evento.get("pagoId");
        String estadoAnterior = (String) evento.get("estadoAnterior");
        String estadoNuevo = (String) evento.get("estadoNuevo");
        String correlationId = (String) evento.get("correlationId");

        log.info("Procesando cambio de estado - pagoId: {}, {} -> {}, correlationId: {}", 
            pagoId, estadoAnterior, estadoNuevo, correlationId);

        boolean esEstadoTerminal = esEstadoTerminal(estadoNuevo);
        if (esEstadoTerminal) {
            log.info("Pago {} alcanzó estado terminal: {}", pagoId, estadoNuevo);
        }
    }

    private void procesarPagoCreado(Map<String, Object> evento) {
        String pagoId = (String) evento.get("pagoId");
        String idempotencyKey = (String) evento.get("idempotencyKey");
        String correlationId = (String) evento.get("correlationId");

        log.info("Procesando pago creado - pagoId: {}, idempotencyKey: {}, correlationId: {}", 
            pagoId, idempotencyKey, correlationId);
    }

    private void manejarError(Map<String, Object> evento, Exception e) {
        String eventType = (String) evento.get("eventType");
        Integer numeroReintento = (Integer) evento.getOrDefault("numeroReintento", 0);

        if (debeReintentar(e)) {
            log.info("Evento {} será reintentado (intento {})", eventType, numeroReintento + 1);
            evento.put("numeroReintento", numeroReintento + 1);
            evento.put("ultimoError", e.getMessage());
            evento.put("timestampError", Instant.now().toString());
        } else {
            log.warn("Evento {} no es reintentable, enviando a DLQ", eventType);
            try {
                String payload = objectMapper.writeValueAsString(evento);
                enviarADLQ(payload, e.getClass().getSimpleName());
            } catch (JsonProcessingException ex) {
                log.error("Error al serializar evento para DLQ: {}", ex.getMessage(), ex);
            }
        }
    }

    private boolean debeReintentar(Exception e) {
        String nombreExcepcion = e.getClass().getSimpleName();
        return "TimeoutException".equals(nombreExcepcion) ||
               "ConnectException".equals(nombreExcepcion) ||
               "ResourceAccessException".equals(nombreExcepcion);
    }

    private boolean esEstadoTerminal(String estado) {
        try {
            EstadoPago estadoEnum = EstadoPago.valueOf(estado);
            return estadoEnum == EstadoPago.APROBADO || 
                   estadoEnum == EstadoPago.RECHAZADO || 
                   estadoEnum == EstadoPago.CANCELADO;
        } catch (IllegalArgumentException e) {
            log.warn("Estado desconocido: {}", estado);
            return false;
        }
    }

    private void enviarADLQ(String mensaje, String razon) {
        log.warn("Enviando mensaje a DLQ - razon: {}", razon);
    }

    private Map<String, Object> deserializar(String mensaje) {
        try {
            return objectMapper.readValue(mensaje, Map.class);
        } catch (JsonProcessingException e) {
            log.error("Error al deserializar mensaje: {}", e.getMessage(), e);
            return null;
        }
    }
}

// === ARCHIVO: src/main/java/com/pragma/pagos/infrastructure/config/CircuitBreakerConfig.java ===
package com.pragma.pagos.infrastructure.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CircuitBreakerConfig {

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
            .failureRateThreshold(50)
            .waitDurationInOpenState(Duration.ofSeconds(30))
            .slidingWindowSize(10)
            .minimumNumberOfCalls(5)
            .permittedNumberOfCallsInHalfOpenState(3)
            .automaticTransitionFromOpenToHalfOpenEnabled(true)
            .build();

        return CircuitBreakerRegistry.of(config);
    }

    @Bean
    public RetryRegistry retryRegistry() {
        RetryConfig config = RetryConfig.custom()
            .maxAttempts(3)
            .waitDuration(Duration.ofSeconds(2))
            .build();

        return RetryRegistry.of(config);
    }
}

// === ARCHIVO: src/test/java/com/pragma/pagos/domain/service/ValidacionPagoServiceTest.java ===
package com.pragma.pagos.domain.service;

import com.pragma.pagos.domain.exception.PagoInvalidoException;
import com.pragma.pagos.domain.model.EstadoPago;
import com.pragma.pagos.domain.model.Pago;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ValidacionPagoServiceTest {

    private ValidacionPagoService validacionService;

    @BeforeEach
    void setUp() {
        validacionService = new ValidacionPagoService();
    }

    @Nested
    @DisplayName("Casos felices - Validación de datos válidos")
    class CasosFelices {

        @Test
        @DisplayName("Debe validar correctamente un pago con todos los datos válidos")
        void debeValidarPagoCompleto() {
            Pago pago = new Pago(
                UUID.randomUUID(),
                "idem-12345",
                new BigDecimal("1500.00"),
                "USD",
                EstadoPago.PENDIENTE,
                "corr-001",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "Pago de orden #12345",
                "cliente-001",
                "orden-001"
            );

            assertDoesNotThrow(() -> validacionService.validar(pago));
        }

        @Test
        @DisplayName("Debe aceptar monto en diferentes monedas")
        void debeAceptarMultiplesMonedas() {
            String[] monedasValidas = {"USD", "EUR", "COP", "MXN", "PEN"};

            for (String moneda : monedasValidas) {
                Pago pago = crearPagoConMoneda(moneda);
                assertDoesNotThrow(() -> validacionService.validar(pago),
                    "La moneda " + moneda + " debería ser válida");
            }
        }

        @Test
        @DisplayName("Debe aceptar pagos con monto máximo")
        void debeAceptarMontoMaximo() {
            Pago pago = new Pago(
                UUID.randomUUID(),
                "idem-max",
                new BigDecimal("999999.99"),
                "USD",
                EstadoPago.PENDIENTE,
                "corr-002",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "Monto máximo",
                "cliente-001",
                "orden-001"
            );

            assertDoesNotThrow(() -> validacionService.validar(pago));
        }

        @Test
        @DisplayName("Debe aceptar idempotency key con formato UUID")
        void debeAceptarIdempotencyKeyUuid() {
            String uuidKey = UUID.randomUUID().toString();
            Pago pago = crearPagoConIdempotencyKey(uuidKey);

            assertDoesNotThrow(() -> validacionService.validar(pago));
        }
    }

    @Nested
    @DisplayName("Edge Cases - Validación de casos límite")
    class EdgeCases {

        @Test
        @DisplayName("Debe rechazar monto negativo")
        void debeRechazarMontoNegativo() {
            Pago pago = new Pago(
                UUID.randomUUID(),
                "idem-001",
                new BigDecimal("-100.00"),
                "USD",
                EstadoPago.PENDIENTE,
                "corr-003",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "Pago negativo",
                "cliente-001",
                "orden-001"
            );

            PagoInvalidoException exception = assertThrows(
                PagoInvalidoException.class,
                () -> validacionService.validar(pago)
            );

            assertEquals("MONTO_INVALIDO", exception.getCodigoError());
            assertEquals("monto", exception.getCampo());
        }

        @Test
        @DisplayName("Debe rechazar monto cero")
        void debeRechazarMontoCero() {
            Pago pago = new Pago(
                UUID.randomUUID(),
                "idem-002",
                BigDecimal.ZERO,
                "USD",
                EstadoPago.PENDIENTE,
                "corr-004",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "Pago cero",
                "cliente-001",
                "orden-001"
            );

            PagoInvalidoException exception = assertThrows(
                PagoInvalidoException.class,
                () -> validacionService.validar(pago)
            );

            assertEquals("MONTO_INVALIDO", exception.getCodigoError());
        }

        @ParameterizedTest
        @NullAndEmptySource
n        @ValueSource(strings = {"  ", "\t", "\n"})
        @DisplayName("Debe rechazar idempotency key nula o vacía")
        void debeRechazarIdempotencyKeyInvalida(String idempotencyKey) {
            Pago pago = new Pago(
                UUID.randomUUID(),
                idempotencyKey,
                new BigDecimal("100.00"),
                "USD",
                EstadoPago.PENDIENTE,
                "corr-005",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "Pago test",
                "cliente-001",
                "orden-001"
            );

            PagoInvalidoException exception = assertThrows(
                PagoInvalidoException.class,
                () -> validacionService.validar(pago)
            );

            assertEquals("IDEMPOTENCY_KEY_INVALIDA", exception.getCodigoError());
            assertEquals("idempotencyKey", exception.getCampo());
        }

        @ParameterizedTest
        @CsvSource({
            "XYZ, Moneda inválida",
            "123, Moneda con números",
            "US, Moneda muy corta",
            "USDD, Moneda muy larga"
        })
        @DisplayName("Debe rechazar monedas inválidas")
        void debeRechazarMonedaInvalida(String moneda, String descripcion) {
            Pago pago = crearPagoConMoneda(moneda);

            PagoInvalidoException exception = assertThrows(
                PagoInvalidoException.class,
                () -> validacionService.validar(pago),
                descripcion
            );

            assertEquals("MONEDA_INVALIDA", exception.getCodigoError());
        }

        @Test
        @DisplayName("Debe rechazar cliente ID nulo")
        void debeRechazarClienteIdNulo() {
            Pago pago = new Pago(
                UUID.randomUUID(),
                "idem-003",
                new BigDecimal("100.00"),
                "USD",
                EstadoPago.PENDIENTE,
                "corr-006",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "Pago test",
                null,
                "orden-001"
            );

            PagoInvalidoException exception = assertThrows(
                PagoInvalidoException.class,
                () -> validacionService.validar(pago)
            );

            assertEquals("CLIENTE_ID_OBLIGATORIO", exception.getCodigoError());
        }

        @Test
        @DisplayName("Debe rechazar orden ID nulo")
        void debeRechazarOrdenIdNulo() {
            Pago pago = new Pago(
                UUID.randomUUID(),
                "idem-004",
                new BigDecimal("100.00"),
                "USD",
                EstadoPago.PENDIENTE,
                "corr-007",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "Pago test",
                "cliente-001",
                null
            );

            PagoInvalidoException exception = assertThrows(
                PagoInvalidoException.class,
                () -> validacionService.validar(pago)
            );

            assertEquals("ORDEN_ID_OBLIGATORIO", exception.getCodigoError());
        }
    }

    @Nested
    @DisplayName("Transiciones de estado")
    class TransicionesEstado {

        @Test
        @DisplayName("Debe permitir transición de PENDIENTE a PROCESANDO")
        void debePermitirTransicionPendienteAProcesando() {
            assertTrue(EstadoPago.esTransicionValida(EstadoPago.PENDIENTE, EstadoPago.PROCESANDO));
        }

        @Test
        @DisplayName("Debe permitir transición de PROCESANDO a APROBADO")
        void debePermitirTransicionProcesandoAAprobado() {
            assertTrue(EstadoPago.esTransicionValida(EstadoPago.PROCESANDO, EstadoPago.APROBADO));
        }

        @Test
        @DisplayName("Debe permitir transición de PROCESANDO a RECHAZADO")
        void debePermitirTransicionProcesandoARechazado() {
            assertTrue(EstadoPago.esTransicionValida(EstadoPago.PROCESANDO, EstadoPago.RECHAZADO));
        }

        @Test
        @DisplayName("Debe rechazar transición directa de PENDIENTE a APROBADO")
        void debeRechazarTransicionDirecta() {
            assertFalse(EstadoPago.esTransicionValida(EstadoPago.PENDIENTE, EstadoPago.APROBADO));
        }

        @Test
        @DisplayName("Debe rechazar transición de APROBADO a cualquier estado")
        void debeRechazarTransicionDesdeAprobado() {
            assertFalse(EstadoPago.esTransicionValida(EstadoPago.APROBADO, EstadoPago.PENDIENTE));
            assertFalse(EstadoPago.esTransicionValida(EstadoPago.APROBADO, EstadoPago.RECHAZADO));
        }
    }

    private Pago crearPagoConMoneda(String moneda) {
        return new Pago(
            UUID.randomUUID(),
            "idem-" + System.nanoTime(),
            new BigDecimal("100.00"),
            moneda,
            EstadoPago.PENDIENTE,
            "corr-" + System.nanoTime(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            "Pago test",
            "cliente-001",
            "orden-001"
        );
    }

    private Pago crearPagoConIdempotencyKey(String key) {
        return new Pago(
            UUID.randomUUID(),
            key,
            new BigDecimal("100.00"),
            "USD",
            EstadoPago.PENDIENTE,
            "corr-" + System.nanoTime(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            "Pago test",
            "cliente-001",
            "orden-001"
        );
    }
}

// === ARCHIVO: src/test/java/com/pragma/pagos/application/OrquestadorPagoTest.java ===
package com.pragma.pagos.application;

import com.pragma.pagos.domain.exception.PagoInvalidoException;
import com.pragma.pagos.domain.exception.ServicioNoDisponibleException;
import com.pragma.pagos.domain.model.EstadoPago;
import com.pragma.pagos.domain.model.Pago;
import com.pragma.pagos.domain.service.ValidacionPagoService;
import com.pragma.pagos.domain.service.RecuperacionFalloService;
import com.pragma.pagos.infrastructure.client.MotorPagosClient;
import com.pragma.pagos.infrastructure.client.SistemaAutenticacionClient;
import com.pragma.pagos.infrastructure.client.ServicioNotificacionClient;
import com.pragma.pagos.infrastructure.producer.EventoPagoProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrquestadorPagoTest {

    @Mock
    private MotorPagosClient motorPagosClient;

    @Mock
    private SistemaAutenticacionClient sistemaAutenticacionClient;

    @Mock
    private ServicioNotificacionClient servicioNotificacionClient;

    @Mock
    private EventoPagoProducer eventoPagoProducer;

    @Mock
    private ValidacionPagoService validacionPagoService;

    @Mock
    private RecuperacionFalloService recuperacionFalloService;

    private OrquestadorPago orquestadorPago;

    @BeforeEach
    void setUp() {
        orquestadorPago = new OrquestadorPago(
            motorPagosClient,
            sistemaAutenticacionClient,
            servicioNotificacionClient,
            eventoPagoProducer,
            validacionPagoService,
            recuperacionFalloService
        );
    }

    @Nested
    @DisplayName("Flujo exitoso de pago")
    class FlujoExitoso {

        @Test
        @DisplayName("Debe completar el flujo de pago exitosamente")
        void debeCompletarFlujoExitoso() {
            Pago pagoOriginal = crearPagoValido();
            String tokenAutenticacion = "Bearer token-valido-123";
            Map<String, Object> respuestaMotor = Map.of(
                "estado", "APROBADO",
                "codigoAutorizacion", "AUTH-998877"
            );

            when(sistemaAutenticacionClient.validarToken(anyString())).thenReturn(true);
            when(motorPagosClient.procesarPago(any(Pago.class))).thenReturn(respuestaMotor);
            doNothing().when(eventoPagoProducer).publicar(any(Pago.class));
            doNothing().when(servicioNotificacionClient).enviarNotificacion(anyString(), anyString());

            Pago resultado = orquestadorPago.ejecutar(pagoOriginal, tokenAutenticacion);

            assertNotNull(resultado);
            assertEquals(EstadoPago.APROBADO, resultado.getEstado());
            assertEquals("AUTH-998877", resultado.getDescripcion());

            verify(sistemaAutenticacionClient).validarToken(tokenAutenticacion);
            verify(motorPagosClient).procesarPago(any(Pago.class));
            verify(eventoPagoProducer).publicar(any(Pago.class));
            verify(servicioNotificacionClient).enviarNotificacion(anyString(), anyString());
        }

        @Test
        @DisplayName("Debe validar idempotency key para evitar pagos duplicados")
        void debeValidarIdempotencyKey() {
            String idempotencyKey = "idem-unico-123";
            Pago pagoOriginal = crearPagoValido();

            when(sistemaAutenticacionClient.validarToken(anyString())).thenReturn(true);
            when(motorPagosClient.procesarPago(any(Pago.class))).thenReturn(Map.of("estado", "APROBADO"));

            orquestadorPago.ejecutar(pagoOriginal, "token");
            orquestadorPago.ejecutar(pagoOriginal, "token");

            verify(motorPagosClient, times(1)).procesarPago(any(Pago.class));
        }
    }

    @Nested
    @DisplayName("Validaciones de entrada")
    class ValidacionesEntrada {

        @Test
        @DisplayName("Debe lanzar excepción cuando autenticación falla")
        void debeFallarConAutenticacionInvalida() {
            Pago pago = crearPagoValido();
            String tokenInvalido = "Bearer token-invalido";

            when(sistemaAutenticacionClient.validarToken(tokenInvalido)).thenReturn(false);

            assertThrows(ServicioNoDisponibleException.class, () ->
                orquestadorPago.ejecutar(pago, tokenInvalido)
            );

            verify(motorPagosClient, never()).procesarPago(any(Pago.class));
        }

        @Test
        @DisplayName("Debe validar el pago antes de procesarlo")
        void debeValidarPagoAntesDeProcesar() {
            Pago pagoInvalido = crearPagoValido();
            String token = "Bearer token-valido";

            doThrow(new PagoInvalidoException("Monto inválido", "MONTO_INVALIDO", "monto", -100))
                .when(validacionPagoService).validar(any(Pago.class));

            assertThrows(PagoInvalidoException.class, () ->
                orquestadorPago.ejecutar(pagoInvalido, token)
            );

            verify(motorPagosClient, never()).procesarPago(any(Pago.class));
        }
    }

    @Nested
    @DisplayName("Manejo de fallos")
    class ManejoFallos {

        @Test
        @DisplayName("Debe reintentar cuando el motor de pagos falla temporalmente")
        void debeReintentarEnFalloTemporal() {
            Pago pago = crearPagoValido();
            Map<String, Object> respuestaExitosa = Map.of("estado", "APROBADO");

            when(sistemaAutenticacionClient.validarToken(anyString())).thenReturn(true);
            when(motorPagosClient.procesarPago(any(Pago.class)))
                .thenThrow(new RuntimeException("Error temporal"))
                .thenReturn(respuestaExitosa);

            Pago resultado = orquestadorPago.ejecutar(pago, "Bearer token");

            assertNotNull(resultado);
            verify(motorPagosClient, times(2)).procesarPago(any(Pago.class));
        }

        @Test
        @DisplayName("Debe notificar cuando el pago es rechazado")
        void debeNotificarRechazo() {
            Pago pago = crearPagoValido();
            Map<String, Object> respuestaRechazado = Map.of(
                "estado", "RECHAZADO",
                "motivo", "Fondos insuficientes"
            );

            when(sistemaAutenticacionClient.validarToken(anyString())).thenReturn(true);
            when(motorPagosClient.procesarPago(any(Pago.class))).thenReturn(respuestaRechazado);
            doNothing().when(servicioNotificacionClient).enviarNotificacion(anyString(), anyString());

            Pago resultado = orquestadorPago.ejecutar(pago, "Bearer token");

            assertEquals(EstadoPago.RECHAZADO, resultado.getEstado());
            verify(servicioNotificacionClient).enviarNotificacion(
                eq(pago.getClienteId()),
                anyString()
            );
        }

        @Test
        @DisplayName("Debe publicar evento de pago fallido en DLQ")
        void debePublicarEnDLQCuandoFallaDefinitivamente() {
            Pago pago = crearPagoValido();

            when(sistemaAutenticacionClient.validarToken(anyString())).thenReturn(true);
            when(motorPagosClient.procesarPago(any(Pago.class)))
                .thenThrow(new RuntimeException("Error permanente"));
            doNothing().when(recuperacionFalloService).registrarFallo(any(Pago.class), any(Exception.class));

            assertThrows(RuntimeException.class, () ->
                orquestadorPago.ejecutar(pago, "Bearer token")
            );

            verify(recuperacionFalloService).registrarFallo(eq(pago), any(Exception.class));
        }
    }

    @Nested
    @DisplayName("Trazabilidad con correlation ID")
    class Trazabilidad {

        @Test
        @DisplayName("Debe propagar correlation ID a través del flujo")
        void debePropagarCorrelationId() {
            String correlationId = UUID.randomUUID().toString();
            Pago pago = crearPagoValido();
            ArgumentCaptor<Pago> pagoCaptor = ArgumentCaptor.forClass(Pago.class);

            when(sistemaAutenticacionClient.validarToken(anyString())).thenReturn(true);
            when(motorPagosClient.procesarPago(any(Pago.class))).thenReturn(Map.of("estado", "APROBADO"));

            orquestadorPago.ejecutar(pago, "Bearer token");

            verify(motorPagosClient).procesarPago(pagoCaptor.capture());
            assertEquals(correlationId, pagoCaptor.getValue().getCorrelationId());
        }

        @Test
        @DisplayName("Debe generar correlation ID si no existe")
        void debeGenerarCorrelationId() {
            Pago pagoSinCorrelation = new Pago(
                UUID.randomUUID(),
                "idem-001",
                new BigDecimal("100.00"),
                "USD",
                EstadoPago.PENDIENTE,
                null,
                LocalDateTime.now(),
                LocalDateTime.now(),
                "Pago sin correlation",
                "cliente-001",
                "orden-001"
            );

            when(sistemaAutenticacionClient.validarToken(anyString())).thenReturn(true);
            when(motorPagosClient.procesarPago(any(Pago.class))).thenReturn(Map.of("estado", "APROBADO"));

            Pago resultado = orquestadorPago.ejecutar(pagoSinCorrelation, "Bearer token");

            assertNotNull(resultado.getCorrelationId());
            assertFalse(resultado.getCorrelationId().isEmpty());
        }
    }

    private Pago crearPagoValido() {
        return new Pago(
            UUID.randomUUID(),
            "idem-" + System.nanoTime(),
            new BigDecimal("1500.00"),
            "USD",
            EstadoPago.PENDIENTE,
            "corr-" + UUID.randomUUID().toString(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            "Pago orden #12345",
            "cliente-001",
            "orden-001"
        );
    }
}

// === ARCHIVO: src/test/java/com/pragma/pagos/infrastructure/client/MotorPagosClientTest.java ===
package com.pragma.pagos.infrastructure.client;

import com.pragma.pagos.domain.model.EstadoPago;
import com.pragma.pagos.domain.model.Pago;
import com.pragma.pagos.infrastructure.config.CircuitBreakerConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MotorPagosClientTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CircuitBreakerConfig circuitBreakerConfig;

    private MotorPagosClient motorPagosClient;

    private static final String URL_BASE = "http://motor-pagos:8080/api/v1";

    @BeforeEach
    void setUp() {
        motorPagosClient = new MotorPagosClient(restTemplate, circuitBreakerConfig);
    }

    @Nested
    @DisplayName("Casos exitosos")
    class CasosExitosos {

        @Test
        @DisplayName("Debe procesar pago exitosamente")
        void debeProcesarPagoExitosamente() {
            Pago pago = crearPagoValido();
            Map<String, Object> respuestaEsperada = Map.of(
                "estado", "APROBADO",
                "codigoAutorizacion", "AUTH-123456",
                "transactionId", UUID.randomUUID().toString()
            );

            when(restTemplate.postForEntity(
                eq(URL_BASE + "/procesar"),
                any(Pago.class),
                eq(Map.class)
            )).thenReturn(new ResponseEntity<>(respuestaEsperada, HttpStatus.OK));

            Map<String, Object> resultado = motorPagosClient.procesarPago(pago);

            assertNotNull(resultado);
            assertEquals("APROBADO", resultado.get("estado"));
            assertNotNull(resultado.get("codigoAutorizacion"));

            verify(restTemplate).postForEntity(
                eq(URL_BASE + "/procesar"),
                eq(pago),
                eq(Map.class)
            );
        }

        @Test
        @DisplayName("Debe consultar estado de transacción")
        void debeConsultarEstadoTransaccion() {
            String transactionId = UUID.randomUUID().toString();
            Map<String, Object> estadoEsperado = Map.of(
                "transactionId", transactionId,
                "estado", "COMPLETADO",
                "monto", "1500.00"
            );

            when(restTemplate.getForEntity(
                eq(URL_BASE + "/estado/" + transactionId),
                eq(Map.class)
            )).thenReturn(new ResponseEntity<>(estadoEsperado, HttpStatus.OK));

            Map<String, Object> resultado = motorPagosClient.consultarEstado(transactionId);

            assertNotNull(resultado);
            assertEquals(transactionId, resultado.get("transactionId"));
            assertEquals("COMPLETADO", resultado.get("estado"));
        }

        @Test
        @DisplayName("Debe obtener respuesta de reembolso")
        void debeProcesarReembolso() {
            String transactionId = UUID.randomUUID().toString();
            Map<String, Object> respuestaEsperada = Map.of(
                "estado", "REEMBOLSO_PROCESADO",
                "montoReembolsado", "1500.00"
            );

            when(restTemplate.postForEntity(
                eq(URL_BASE + "/reembolso"),
                any(Map.class),
                eq(Map.class)
            )).thenReturn(new ResponseEntity<>(respuestaEsperada, HttpStatus.OK));

            Map<String, Object> resultado = motorPagosClient.procesarReembolso(transactionId, new BigDecimal("1500.00"));

            assertNotNull(resultado);
            assertEquals("REEMBOLSO_PROCESADO", resultado.get("estado"));
        }
    }

    @Nested
    @DisplayName("Manejo de errores del motor de pagos")
    class ManejoErrores {

        @Test
        @DisplayName("Debe lanzar excepción cuando el motor no está disponible")
        void debeLanzarExcepcionCuandoNoDisponible() {
            Pago pago = crearPagoValido();

            when(restTemplate.postForEntity(
                anyString(),
                any(Pago.class),
                eq(Map.class)
            )).thenThrow(new RuntimeException("Connection refused"));

            assertThrows(RuntimeException.class, () ->
                motorPagosClient.procesarPago(pago)
            );
        }

        @Test
        @DisplayName("Debe manejar respuesta de error del motor")
        void debeManejarErrorDelMotor() {
            Pago pago = crearPagoValido();
            Map<String, Object> errorRespuesta = Map.of(
                "error", "FONDOS_INSUFICIENTES",
                "mensaje", "El cliente no tiene fondos suficientes"
            );

            when(restTemplate.postForEntity(
                anyString(),
                any(Pago.class),
                eq(Map.class)
            )).thenReturn(new ResponseEntity<>(errorRespuesta, HttpStatus.BAD_REQUEST));

            Map<String, Object> resultado = motorPagosClient.procesarPago(pago);

            assertNotNull(resultado);
            assertEquals("FONDOS_INSUFICIENTES", resultado.get("error"));
        }

        @Test
        @DisplayName("Debe manejar timeout del motor de pagos")
        void debeManejarTimeout() {
            Pago pago = crearPagoValido();

            when(restTemplate.postForEntity(
                anyString(),
                any(Pago.class),
                eq(Map.class)
            )).thenThrow(new org.springframework.web.client.ResourceAccessException("Read timed out"));

            assertThrows(RuntimeException.class, () ->
                motorPagosClient.procesarPago(pago)
            );
        }

        @Test
        @DisplayName("Debe manejar error de autenticación con el motor")
        void debeManejarErrorAutenticacion() {
            Pago pago = crearPagoValido();

            when(restTemplate.postForEntity(
                anyString(),
                any(Pago.class),
                eq(Map.class)
            )).thenReturn(new ResponseEntity<>(Map.of("error", "UNAUTHORIZED"), HttpStatus.UNAUTHORIZED));

            assertThrows(SecurityException.class, () ->
                motorPagosClient.procesarPago(pago)
            );
        }
    }

    @Nested
    @DisplayName("Contrato del cliente")
    class ContratoCliente {

        @Test
        @DisplayName("Debe incluir headers de correlación en las peticiones")
        void debeIncluirHeadersCorrelacion() {
            String correlationId = UUID.randomUUID().toString();
            Pago pago = crearPagoValido();

            when(restTemplate.postForEntity(
                anyString(),
                any(Pago.class),
                eq(Map.class)
            )).thenReturn(new ResponseEntity<>(Map.of("estado", "APROBADO"), HttpStatus.OK));

            motorPagosClient.procesarPago(pago);

            verify(restTemplate).postForEntity(
                eq(URL_BASE + "/procesar"),
                any(Pago.class),
                eq(Map.class)
            );
        }

        @Test
        @DisplayName("Debe usar la URL configurada del motor de pagos")
        void debeUsarUrlConfigurada() {
            String urlPersonalizada = "http://motor-pagos-custom:9090/api";
            MotorPagosClient clienteCustom = new MotorPagosClient(restTemplate, circuitBreakerConfig);

            when(circuitBreakerConfig.getMotorPagosUrl()).thenReturn(urlPersonalizada);

            when(restTemplate.postForEntity(
                anyString(),
                any(Pago.class),
                eq(Map.class)
            )).thenReturn(new ResponseEntity<>(Map.of("estado", "APROBADO"), HttpStatus.OK));

            clienteCustom.procesarPago(crearPagoValido());

            verify(restTemplate).postForEntity(
                startsWith(urlPersonalizada),
                any(Pago.class),
                eq(Map.class)
            );
        }

        @Test
        @DisplayName("Debe mapear correctamente la respuesta del motor a modelo de dominio")
        void debeMapearRespuestaADominio() {
            Map<String, Object> respuestaRaw = Map.of(
                "transactionId", "TXN-12345",
                "estado", "APROBADO",
                "monto", 1500.00,
                "codigoAutorizacion", "AUTH-999",
                "fecha", "2024-01-15T10:30:00Z"
            );

            when(restTemplate.postForEntity(
                anyString(),
                any(Pago.class),
                eq(Map.class)
            )).thenReturn(new ResponseEntity<>(respuestaRaw, HttpStatus.OK));

            Map<String, Object> resultado = motorPagosClient.procesarPago(crearPagoValido());

            assertTrue(resultado.containsKey("transactionId"));
            assertTrue(resultado.containsKey("estado"));
            assertTrue(resultado.containsKey("codigoAutorizacion"));
        }
    }

    private Pago crearPagoValido() {
        return new Pago(
            UUID.randomUUID(),
            "idem-" + System.nanoTime(),
            new BigDecimal("1500.00"),
            "USD",
            EstadoPago.PENDIENTE,
            "corr-" + UUID.randomUUID().toString(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            "Pago orden #12345",
            "cliente-001",
            "orden-001"
        );
    }
}

// === ARCHIVO: openapi.yaml ===
openapi: 3.1.0
info:
  title: API de Gestión de Pagos
  description: |
    Plataforma de gestión de pagos en línea que permite crear, consultar y gestionar
    transacciones de pago con integración a motor de pagos externo, sistema de
    autenticación y servicio de notificaciones.
  version: 1.0.0
  contact:
    name: Equipo de Desarrollo
    email: desarrollo@pragma.com
  license:
    name: Proprietario
    url: https://pragma.com/licencia

servers:
  - url: http://localhost:8080
    description: Servidor de desarrollo local
  - url: ${API_BASE_URL}
    description: Servidor de producción

tags:
  - name: Pagos
    description: Operaciones sobre transacciones de pago
  - name: Salud
    description: Endpoints de salud del sistema

paths:
  /api/pagos:
    post:
      summary: Crear una nueva transacción de pago
      description: |
        Crea un nuevo pago en el sistema. Utiliza una clave de idempotencia para
        garantizar que pagos duplicados no sean procesados múltiples veces.
        El flujo incluye validación, llamada al motor de pagos, y notificación al cliente.
      operationId: crearPago
      tags:
        - Pagos
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/PagoRequest'
            examples:
              pagoExitoso:
                summary: Ejemplo de pago exitoso
                value:
                  idempotencyKey: "pay_abc123def456"
                  monto: 150000.00
                  moneda: "COP"
                  descripcion: "Compra en tienda virtual - Orden #12345"
                  clienteId: "cli_987654321"
                  ordenId: "ord_12345"
      responses:
        '201':
          description: Pago creado exitosamente
          headers:
            X-Correlation-ID:
              schema:
                type: string
              description: Identificador de correlación para trazabilidad
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/PagoResponse'
        '400':
          description: Solicitud inválida - datos de entrada incorrectos
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ErrorResponse'
              examples:
                montoInvalido:
                  summary: Monto negativo
                  value:
                    codigo: "MONTO_INVALIDO"
                    mensaje: "El monto debe ser mayor a cero"
                    campo: "monto"
                    valorRechazado: -100
                monedaInvalida:
                  summary: Moneda no soportada
                  value:
                    codigo: "MONEDA_INVALIDA"
                    mensaje: "La moneda debe ser una de las soportadas"
                    campo: "moneda"
                    valorRechazado: "XYZ"
        '409':
          description: Conflicto - clave de idempotencia duplicada
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ErrorResponse'
              examples:
                claveDuplicada:
                  summary: Clave de idempotencia existente
                  value:
                    codigo: "IDEMPOTENCY_KEY_DUPLICADA"
                    mensaje: "Ya existe un pago con esta clave de idempotencia"
                    campo: "idempotencyKey"
                    valorRechazado: "pay_abc123def456"
        '422':
          description: Entidad no procesable - validación de negocio fallida
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ErrorResponse'
              examples:
                validacionNegocio:
                  summary: Error de validación de negocio
                  value:
                    codigo: "VALIDACION_NEGOCIO"
                    mensaje: "El monto excede el límite permitido para el cliente"
                    campo: "monto"
                    valorRechazado: 10000000
    get:
      summary: Listar transacciones de pago
      description: |
        Retorna una lista paginada de pagos. Soporta filtros por estado, cliente
        y rango de fechas.
      operationId: listarPagos
      tags:
        - Pagos
      parameters:
        - name: page
          in: query
          description: Número de página (0-indexed)
          schema:
            type: integer
            default: 0
            minimum: 0
        - name: size
          in: query
          description: Tamaño de página
          schema:
            type: integer
            default: 20
            minimum: 1
            maximum: 100
        - name: estado
          in: query
          description: Filtrar por estado del pago
          schema:
            $ref: '#/components/schemas/EstadoPago'
        - name: clienteId
          in: query
          description: Filtrar por identificador de cliente
          schema:
            type: string
            pattern: '^cli_[a-zA-Z0-9]+$'
        - name: fechaDesde
          in: query
          description: Fecha inicial del rango (ISO 8601)
          schema:
            type: string
            format: date-time
        - name: fechaHasta
          in: query
          description: Fecha final del rango (ISO 8601)
          schema:
            type: string
            format: date-time
      responses:
        '200':
          description: Lista de pagos recuperada exitosamente
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/PagoPaginadoResponse'
        '400':
          description: Parámetros de consulta inválidos
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ErrorResponse'

  /api/pagos/{id}:
    get:
      summary: Obtener detalle de un pago
      description: Retorna la información completa de un pago por su identificador único.
      operationId: obtenerPago
      tags:
        - Pagos
      parameters:
        - name: id
          in: path
          required: true
          description: Identificador único del pago (UUID)
          schema:
            type: string
            format: uuid
          example: "550e8400-e29b-41d4-a716-446655440000"
      responses:
        '200':
          description: Pago encontrado
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/PagoResponse'
        '404':
          description: Pago no encontrado
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ErrorResponse'
              examples:
                noEncontrado:
                  summary: Pago inexistente
                  value:
                    codigo: "PAGO_NO_ENCONTRADO"
                    mensaje: "No existe un pago con el identificador proporcionado"
                    campo: "id"
                    valorRechazado: "550e8400-e29b-41d4-a716-446655440000"

  /api/pagos/{id}/estado:
    put:
      summary: Actualizar estado de un pago
      description: |
        Actualiza el estado de un pago. Utilizado principalmente por webhooks
        del motor de pagos externo para notificar cambios de estado.
      operationId: actualizarEstadoPago
      tags:
        - Pagos
      parameters:
        - name: id
          in: path
          required: true
          description: Identificador único del pago
          schema:
            type: string
            format: uuid
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/ActualizacionEstadoRequest'
      responses:
        '200':
          description: Estado actualizado exitosamente
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/PagoResponse'
        '400':
          description: Transición de estado inválida
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ErrorResponse'
              examples:
                transicionInvalida:
                  summary: Transición no permitida
                  value:
                    codigo: "TRANSICION_ESTADO_INVALIDA"
                    mensaje: "No se puede transiciónar de PENDIENTE a CANCELADO directamente"
                    campo: "estado"
                    valorRechazado: "CANCELADO"
        '404':
          description: Pago no encontrado
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ErrorResponse'

  /api/pagos/{id}/reintento:
    post:
      summary: Reintentar un pago fallido
      description: |
        Reintenta un pago que se encuentra en estado FALLIDO. Genera una nueva
        clave de idempotencia y reinicia el flujo completo de procesamiento.
      operationId: reintentarPago
      tags:
        - Pagos
      parameters:
        - name: id
          in: path
          required: true
          description: Identificador único del pago a reintentar
          schema:
            type: string
            format: uuid
      responses:
        '200':
          description: Reintento iniciado exitosamente
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/PagoResponse'
        '400':
          description: El pago no puede ser reintentado
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ErrorResponse'
              examples:
                estadoInvalido:
                  summary: Estado no permite reintento
                  value:
                    codigo: "REINTENTO_NO_PERMITIDO"
                    mensaje: "Solo se pueden reintentar pagos en estado FALLIDO"
                    campo: "estado"
                    valorRechazado: "COMPLETADO"
        '404':
          description: Pago no encontrado
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ErrorResponse'

  /api/pagos/{id}/reembolso:
    post:
      summary: Solicitar reembolso de un pago
      description: |
        Solicita el reembolso de un pago previamente completado. El reembolso
        es procesado de forma asíncrona por el motor de pagos.
      operationId: solicitarReembolso
      tags:
        - Pagos
      parameters:
        - name: id
          in: path
          required: true
          description: Identificador único del pago a reembolsar
          schema:
            type: string
            format: uuid
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/ReembolsoRequest'
      responses:
        '202':
          description: Solicitud de reembolso aceptada
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/PagoResponse'
        '400':
          description: Reembolso no permitido para este pago
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ErrorResponse'
        '404':
          description: Pago no encontrado
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ErrorResponse'

  /actuator/health:
    get:
      summary: Verificar salud del sistema
      description: |
        Endpoint de salud que indica si la aplicación está operativa y
        todos sus componentes integrados están disponibles.
      operationId: healthCheck
      tags:
        - Salud
      responses:
        '200':
          description: Sistema saludable
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/HealthResponse'
        '503':
          description: Sistema no saludable
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/HealthResponse'

  /actuator/info:
    get:
      summary: Información de la aplicación
      description: Retorna metadata de la aplicación como versión, nombre y descripción.
      operationId: appInfo
      tags:
        - Salud
      responses:
        '200':
          description: Información recuperada exitosamente
          content:
            application/json:
              schema:
                type: object
                properties:
                  app:
                    type: object
                    properties:
                      name:
                        type: string
                      version:
                        type: string
                      description:
                        type: string

components:
  schemas:
    PagoRequest:
      type: object
      required:
        - idempotencyKey
        - monto
        - moneda
        - clienteId
      properties:
        idempotencyKey:
          type: string
          description: |
            Clave única que identifica de forma única esta operación de pago.
            Debe ser un valor generado por el cliente que garantice la idempotencia.
          pattern: '^[a-zA-Z0-9_\-]{10,64}$'
          example: "pay_abc123def456"
        monto:
          type: number
          description: Monto de la transacción en la moneda especificada
          format: decimal
          minimum: 0.01
          maximum: 999999999.99
          example: 150000.00
        moneda:
          type: string
          description: Código ISO 4217 de la moneda
          enum:
            - COP
            - USD
            - EUR
            - MXN
            - PEN
          example: "COP"
        descripcion:
          type: string
          description: Descripción de la transacción
          maxLength: 500
          example: "Compra en tienda virtual - Orden #12345"
        clienteId:
          type: string
          description: Identificador único del cliente en el sistema
          pattern: '^cli_[a-zA-Z0-9]+$'
          example: "cli_987654321"
        ordenId:
          type: string
          description: Identificador de la orden asociada
          pattern: '^ord_[a-zA-Z0-9]+$'
          example: "ord_12345"
        metadata:
          type: object
          description: Datos adicionales específicos del comercio
          additionalProperties:
            type: string
          example:
            canal: "web"
            idempotencyKey: "pay_abc123def456"

    PagoResponse:
      type: object
      properties:
        id:
          type: string
          format: uuid
          description: Identificador único del pago
          example: "550e8400-e29b-41d4-a716-446655440000"
        idempotencyKey:
          type: string
          description: Clave de idempotencia original
        monto:
          type: number
          format: decimal
        moneda:
          type: string
          enum:
            - COP
            - USD
            - EUR
            - MXN
            - PEN
        estado:
          $ref: '#/components/schemas/EstadoPago'
        correlationId:
          type: string
          description: ID de correlación para trazabilidad distribuida
          example: "corr_abc123def456ghi789"
        fechaCreacion:
          type: string
          format: date-time
          description: Fecha de creación del pago
          example: "2025-01-15T10:30:00Z"
        fechaActualizacion:
          type: string
          format: date-time
          description: Fecha de última actualización
          example: "2025-01-15T10:35:00Z"
        descripcion:
          type: string
        clienteId:
          type: string
        ordenId:
          type: string

    EstadoPago:
      type: string
      description: Estados posibles de un pago en el sistema
      enum:
        - PENDIENTE
        - PROCESANDO
        - COMPLETADO
        - FALLIDO
        - CANCELADO
        - REEMBOLSADO
      example: "PENDIENTE"

    ActualizacionEstadoRequest:
      type: object
      required:
        - estado
        - motivo
      properties:
        estado:
          $ref: '#/components/schemas/EstadoPago'
        motivo:
          type: string
          description: Razón o descripción del cambio de estado
          maxLength: 500
          example: "Pago aprobado por el motor de pagos"
        fechaProcesamiento:
          type: string
          format: date-time
          description: Fecha en que el motor de pagos procesó la transacción

    ReembolsoRequest:
      type: object
      required:
        - motivo
        - monto
      properties:
        motivo:
          type: string
          description: Razón del reembolso
          maxLength: 500
          example: "Cliente solicitó cancelación dentro de las 24 horas"
        monto:
          type: number
          format: decimal
          description: |
            Monto a reembolsar. Si no se especifica, se reembolsa el monto total.
            Permite reembolsos parciales.
          minimum: 0.01
          example: 75000.00

    ErrorResponse:
      type: object
      description: Estructura estándar de respuesta de error
      properties:
        codigo:
          type: string
          description: Código de error único para categorización
          example: "MONTO_INVALIDO"
        mensaje:
          type: string
          description: Descripción legible del error
          example: "El monto debe ser mayor a cero"
        campo:
          type: string
          description: Campo que originó el error
          example: "monto"
        valorRechazado:
          description: Valor que fue rechazado por validación
          example: -100
        timestamp:
          type: string
          format: date-time
          description: Timestamp del error
          example: "2025-01-15T10:30:00Z"
        correlationId:
          type: string
          description: ID de correlación para trazabilidad

    PagoPaginadoResponse:
      type: object
      properties:
        contenido:
          type: array
          items:
            $ref: '#/components/schemas/PagoResponse'
        pagina:
          type: integer
          description: Número de página actual
          example: 0
        tamanhoPagina:
          type: integer
          description: Elementos por página
          example: 20
        totalElementos:
          type: integer
          description: Total de elementos disponibles
          example: 150
        totalPaginas:
          type: integer
          description: Total de páginas
          example: 8

    HealthResponse:
      type: object
      properties:
        status:
          type: string
          enum:
            - UP
            - DOWN
            - UNKNOWN
          description: Estado general del sistema
        components:
          type: object
          description: Estado de los componentes individuales
          properties:
            db:
              $ref: '#/components/schemas/ComponentHealth'
            kafka:
              $ref: '#/components/schemas/ComponentHealth'
            motorPagos:
              $ref: '#/components/schemas/ComponentHealth'
            autenticacion:
              $ref: '#/components/schemas/ComponentHealth'
            notificacion:
              $ref: '#/components/schemas/ComponentHealth'

    ComponentHealth:
      type: object
      properties:
        status:
          type: string
          enum:
            - UP
            - DOWN
          example: "UP"

  securitySchemes:
    BearerAuth:
      type: http
      scheme: bearer
      bearerFormat: JWT
      description: |
        Token JWT utilizado para autenticación. Debe ser obtenido del
        sistema de autenticación antes de realizar llamadas a la API.
    ApiKeyAuth:
      type: apiKey
      in: header
      name: X-API-Key
      description: |
        Clave de API para autenticación machine-to-machine. Usar en
        lugar de JWT para integraciones entre sistemas.

security:
  - BearerAuth: []
  - ApiKeyAuth: []


externalDocs:
  description: Documentación adicional del dominio de pagos
  url: https://docs.pragma.com/pagos

// === ARCHIVO: README.md ===
# Sistema de Gestión de Pagos

## Descripción del Proyecto

Plataforma de gestión de pagos en línea desarrollada con arquitectura SOA que permite procesar transacciones de pago de forma confiable, trazable y resiliente. El sistema integra un motor de pagos externo, un sistema de autenticación y un servicio de notificaciones, utilizando patrones de integración empresarial (EIP) y mecanismos de resiliencia avanzados.

## Stack Tecnológico

| Componente | Tecnología | Versión |
|------------|------------|---------|
| Lenguaje | Java | 21 |
| Framework | Spring Boot | 3.5.6 |
| Integración | Apache Camel | 4.4.0 |
| Mensajería | Apache Kafka | 3.2.0 |
| Resiliencia | Resilience4j | 2.1.0 |
| Build | Maven | 3.9+ |
| Validación | XSD | 1.0 |

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/pragma/pagos/
│   │   ├── PagosApplication.java          # Punto de entrada
│   │   ├── domain/
│   │   │   ├── model/
│   │   │   │   ├── Pago.java               # Entidad de dominio
│   │   │   │   └── EstadoPago.java         # Enum de estados
│   │   │   ├── service/
│   │   │   │   ├── ValidacionPagoService.java
│   │   │   │   └── RecuperacionFalloService.java
│   │   │   └── exception/
│   │   │       ├── PagoInvalidoException.java
│   │   │       └── ServicioNoDisponibleException.java
│   │   ├── application/
│   │   │   └── OrquestadorPago.java        # Orquestación del flujo
│   │   └── infrastructure/
│   │       ├── config/
│   │       │   └── CircuitBreakerConfig.java
│   │       ├── client/
│   │       │   ├── MotorPagosClient.java
│   │       │   ├── SistemaAutenticacionClient.java
│   │       │   └── ServicioNotificacionClient.java
│   │       ├── producer/
│   │       │   └── EventoPagoProducer.java
│   │       └── consumer/
│   │           └── EventoPagoConsumer.java
│   └── resources/
│       ├── application.yml                 # Configuración
│       └── schemas/
│           ├── pago-request.xsd
│           └── pago-response.xsd
└── test/
    └── java/com/pragma/pagos/
        ├── domain/service/ValidacionPagoServiceTest.java
        ├── application/OrquestadorPagoTest.java
        └── infrastructure/client/MotorPagosClientTest.java
```

## Arquitectura

### Patrón de Capas

El proyecto sigue una arquitectura SOA con separación clara de responsabilidades:

- **Capa de Dominio**: Modelos de negocio, servicios de dominio y excepciones tipadas
- **Capa de Aplicación**: Orquestadores que coordinan el flujo de negocio
- **Capa de Infraestructura**: Clientes de servicios externos, productores y consumidores de eventos

### Flujo de Procesamiento de Pago

```
┌─────────────┐    ┌──────────────┐    ┌─────────────────┐    ┌────────────────┐
│   Cliente   │───>│  Validación  │───>│  Motor de Pagos │───>│ Notificación   │
│  (Request)  │    │   (Domain)   │    │  (External)     │    │   (External)   │
└─────────────┘    └──────────────┘    └─────────────────┘    └────────────────┘
       │                  │                     │                     │
       v                  v                     v                     v
  Idempotency        Schema XSD            Circuit Breaker        Async Event
  Key Check          Validation            + Retry Policy         (Kafka)
```

### Componentes Clave

**Orquestador de Pago**
Coordina el flujo completo: validación → autenticación → procesamiento → notificación. Maneja errores y reintentos.

**Cliente del Motor de Pagos**
Integración con el proveedor externo de pagos. Implementa Circuit Breaker y políticas de retry.

**Productor/Consumidor de Eventos**
Comunicación asíncrona mediante Apache Kafka para notificaciones de cambio de estado.

## Configuración

### application.yml

La configuración principal se encuentra en `src/main/resources/application.yml` e incluye:

- Endpoints de servicios externos
- Configuración de Kafka (topics, consumer groups)
- Parámetros de Resilience4j (circuit breaker, retry)
- Configuración de logging y correlation ID

### Variables de Entorno

| Variable | Descripción | Valor por Defecto |
|----------|-------------|-------------------|
| `SERVER_PORT` | Puerto de la aplicación | 8080 |
| `KAFKA_BOOTSTRAP_SERVERS` | Servidores Kafka | localhost:9092 |
| `MOTOR_PAGOS_URL` | URL del motor de pagos | http://localhost:8081 |
| `AUTENTICACION_URL` | URL del servicio de auth | http://localhost:8082 |
| `NOTIFICACION_URL` | URL del servicio de notificaciones | http://localhost:8083 |

## Ejecución

### Prerrequisitos

- JDK 21 o superior
- Maven 3.9+
- Apache Kafka (para desarrollo local)

### Compilación

```bash
mvn clean compile
```

### Ejecución

```bash
mvn spring-boot:run
```

### Verificación de Salud

```bash
curl http://localhost:8080/actuator/health
```

### Ejecución de Tests

```bash
mvn test
```

## API Endpoints

### Crear Pago

```bash
curl -X POST http://localhost:8080/api/pagos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "idempotencyKey": "pay_test_001",
    "monto": 150000.00,
    "moneda": "COP",
    "descripcion": "Compra de prueba",
    "clienteId": "cli_123",
    "ordenId": "ord_456"
  }'
```

### Consultar Pago

```bash
curl http://localhost:8080/api/pagos/{id} \
  -H "Authorization: Bearer <token>"
```

### Listar Pagos

```bash
curl "http://localhost:8080/api/pagos?page=0&size=20&estado=PENDIENTE" \
  -H "Authorization: Bearer <token>"
```

## Patrones de Integración Implementados

| Patrón | Implementación | Propósito |
|--------|----------------|-----------|
| Idempotency Key | Campo en request | Prevenir pagos duplicados |
| Circuit Breaker | Resilience4j | Protección contra fallos en cascada |
| Retry Policy | Resilience4j | Reintentos automáticos con backoff |
| Dead Letter Queue | Kafka DLQ | Manejo de mensajes fallidos |
| Correlation ID | Header X-Correlation-ID | Trazabilidad distribuida |
| Content Enricher | Camel Enrich | Enrichment de mensajes |
| Message Router | Camel Route | Enrutamiento condicional |

## Manejo de Errores

### Excepciones de Dominio

- `PagoInvalidoException`: Errores de validación de negocio
- `ServicioNoDisponibleException`: Fallos en servicios externos

### Estados de Pago

| Estado | Descripción | Transiciones Válidas |
|--------|-------------|----------------------|
| PENDIENTE | Pago creado, esperando procesamiento | PROCESANDO, CANCELADO |
| PROCESANDO | Enviado al motor de pagos | COMPLETADO, FALLIDO |
| COMPLETADO | Pago exitoso | REEMBOLSADO |
| FALLIDO | Error en procesamiento | PENDIENTE (reintento) |
| CANCELADO | Cancelado por el usuario | - |
| REEMBOLSADO | Dinero devuelto al cliente | - |

## Contrato de la API

El contrato completo está definido en `openapi.yaml` en la raíz del proyecto. Para validar:

```bash
npx --yes @redocly/cli lint openapi.yaml
```

## Esquemas XSD

Los esquemas de validación se encuentran en `src/main/resources/schemas/`:

- `pago-request.xsd`: Valida estructura del request de pago
- `pago-response.xsd`: Valida estructura de la respuesta

## Contribución

1. Crear una rama desde `main`
2. Implementar los cambios siguiendo las convenciones del proyecto
3. Ejecutar tests: `mvn test`
4. Crear Pull Request con descripción detallada

## Licencia

Proprietario - Pragma S.A.S.
```
