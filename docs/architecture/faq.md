# ❓ Preguntas Frecuentes (FAQ) - BCK-Plantilla

Aborda dudas comunes y justificaciones técnicas sobre las decisiones arquitectónicas y los procedimientos operativos del proyecto.

---

## 🏗️ Razonamiento arquitectónico (El "Por qué")

### 1. ¿Por qué usar `Command` y `Query` si ya tengo la `Entity`?

Las entidades representan el **Estado** (qué es), mientras que Commands y Queries representan la **Intención** (qué queremos hacer).

- **La Entity (`Type`)**: Describe el objeto de negocio.
- **El Command/Query (`TypeCreateCommand`)**: Encapsula la solicitud específica.

**Beneficios clave:**

- **Claridad de intención**: `processor.execute(new TypeCreateCommand(type))` es explícitamente una orden de *crear*; `processor.execute(type)` es ambiguo.
- **Metadatos de operación**: Los Commands permiten agregar datos que no pertenecen a la entity (ej. `userId` del operador, `sourceIP`, `auditToken`) sin contaminar el modelo de dominio.
- **Inmutabilidad**: Los Records aseguran que la intención no pueda cambiar una vez pasada al Processor.

### 2. ¿Por qué la clase `MainApplication` está en el módulo `infrastructure`?

Para evitar **Dependencias Circulares**.

En arquitectura limpia, el flujo de dependencias es: `Infrastructure` → `Application` → `Domain`. Si la clase Main estuviera en el módulo `application`, tendría que depender de `infrastructure` para configurar DB y Kafka, creando un ciclo (`App` → `Infra` → `App`). Ubicarla en `infrastructure` garantiza que el flujo sea unidireccional.

### 3. ¿Quién llama al Processor, y quién recibe la respuesta?

El flujo sigue una cadena de responsabilidad estricta:
`Controller` → `UseCase` → `Processor` → `Repository`.

- **Quien llama**: El `UseCase` es el orquestador. Crea el record `Command/Query` y llama a `Processor.execute()`.
- **Quien recibe**: El `Processor` retorna un Domain Object al `UseCase`. El `UseCase` lo mapea a un `ResponseDto` y lo retorna al `Controller`.

### 4. ¿Por qué el Controller recibe un DTO y no el Command directamente?

Para desacoplar el **Contrato Externo** de la **Lógica Interna**.

- **DTOs** son para la API. Manejan serialización JSON y validación de entrada (`@Valid`).
- **Commands** son para el Dominio. Encapsulan la intención de negocio. Si cambia una regla de negocio en el Command, no queremos forzar a los clientes de la API a cambiar el formato JSON de su request.

### 5. ¿Cuál es la diferencia entre un Domain Service y un Processor? ¿Por qué necesito ambos?

Tienen roles fundamentalmente distintos:

- **Domain Service**: Contiene **reglas de negocio puras** y lógica reutilizable (ej. `validateUniqueness()`). Es un "experto sin estado" que no conoce el ciclo de vida de la request; solo sabe validar o calcular reglas de negocio.
- **Processor**: Es el **orquestador de la operación**. Define el *flujo de ejecución* (`preProcess` → `process` → `postProcess`). Usa el Domain Service para validar las reglas y el Repository para persistir.

**En resumen**: El Service provee la **Regla**, y el Processor provee el **Procedimiento**.

### 6. ¿Por qué los DomainServices no tienen `@Service`?

Porque el módulo `domain` es agnóstico a Spring. Añadir `@Service` introduciría una dependencia de Spring en el dominio, violando la **Domain Purity**.

En su lugar, los `DomainService` se registran como beans Spring en:
`infrastructure/config/domain/DomainServicesConfig.java`

Esto mantiene el dominio puro y permite que Spring los gestione desde la capa de infraestructura.

---

## 🛠️ Operación y Troubleshooting (El "Cómo")

### 1. ¿Cómo arranco la aplicación?

```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

La app quedará disponible en:
- **HTTP API**: `http://localhost:8081`
- **Swagger UI**: `http://localhost:8081/swagger-ui.html`
- **gRPC**: puerto `9091`

> Para más detalles sobre configuración del entorno local, ver [`local-setup.md`](./local-setup.md).

### 2. ¿Por qué IntelliJ muestra "Main class not found"?

El IDE suele cachear configuraciones antiguas del módulo `application`. Para solucionar:

1. Navegar a `infrastructure/src/main/java/co/com/empresa/infrastructure/MainApplication.java`.
2. **Click derecho** → **Run 'MainApplication.main()'**.

Esto crea una nueva configuración de ejecución correcta.

### 3. ¿Qué hacer si veo un `NoClassDefFoundError` relacionado con gRPC?

Suele ocurrir por un mismatch de versiones entre `grpc-core` y `grpc-api`.

**Solución:** Verificar que todas las dependencias gRPC estén alineadas a la misma versión (actualmente `1.63.0`) en el `build.gradle` raíz.

### 4. ¿Qué hacer si la app falla con `Could not resolve placeholder`?

Ocurre cuando falta una propiedad en el Config Server o en los properties locales.

**Solución:** Proveer siempre un valor por defecto en las anotaciones `@Value`:
```java
@Value("${spring.datasource.hikari.maximum-pool-size:10}")
```

También puede ocurrir porque las variables de entorno del `.env` no están cargadas. Verificar que el archivo `.env` exista en la raíz y que las variables estén definidas.

### 5. ¿Cómo cambio el perfil activo (Entorno)?

Por argumento al arrancar:
```bash
./gradlew bootRun --args='--spring.profiles.active=prod'
```

Por variable de entorno:
```bash
export SPRING_PROFILES_ACTIVE=prod
./gradlew bootRun
```

### 6. ¿Por qué la app falla con `password authentication failed for user "postgres"` al arrancar?

Las credenciales de la base de datos se leen desde variables de entorno. Si las variables `SPRING_DATASOURCE_COMMAND_USERNAME` / `SPRING_DATASOURCE_COMMAND_PASSWORD` no están definidas, Spring usa el usuario por defecto `postgres` y falla.

**Solución:** Crear el archivo `.env` en la raíz a partir del `.example.env` y definir las credenciales correctas.

### 7. ¿Por qué Kafka falla al arrancar en desarrollo local?

En perfil `dev`, Kafka está deshabilitado por defecto:
```properties
app.messaging.kafka.enabled=false
```

Esto desactiva el bean `KafkaEventPublisher` mediante `@ConditionalOnProperty`. Si necesitas probar Kafka en local, cambia esa propiedad a `true` y define `SPRING_KAFKA_BOOTSTRAP_SERVERS` en el `.env`.

---

## 🚀 Resumen rápido del flujo de datos

```
JSON → RequestDto → Domain Entity → Command/Query Record → Processor → Repository → Database
```
*(Y el mismo camino en reversa para la respuesta)*
