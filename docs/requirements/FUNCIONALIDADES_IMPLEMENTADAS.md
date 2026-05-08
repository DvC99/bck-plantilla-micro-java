# Funcionalidades Implementadas (Consolidado)

> **Fuente de verdad** para el estado actual del proyecto. Actualizar en cada PR que toque controladores, respuestas, errores, persistencia o i18n.

---

## 1. API REST y flujo de capas

### Flujo aplicado

```
Controller (Infra) → UseCase (App) → Processor (Domain) → RepositoryImpl (Infra) → DB
```

### Controladores principales

- `infrastructure/type/TypeRestController.java`
- `infrastructure/typecategory/TypeCategoryRestController.java`
- `infrastructure/event/EventAuditRestController.java`

### Base común de controladores

- `infrastructure/common/BaseRestController.java` — provee helpers `success()`, `successList()`, `paginated()` para estandarizar respuestas sin repetición.

### Criterios implementados

- Endpoints de escritura con `@Transactional(rollbackFor = Exception.class)`.
- Commands ejecutados en modo síncrono por request (`setAsync(false)` en writes).
- Queries para lectura (id, combo, paginado).
- Paginado y combo expuestos como `GET` con `@RequestParam`.

---

## 2. Respuestas unificadas

### Tipos de respuesta usados en controladores

- `success(...)` para respuestas no paginadas ni lista.
- `successList(...)` para listados simples.
- `paginated(...)` para respuestas paginadas.

### Builder de respuestas

- `commons/helper/ApiResponseBuilder.java`

### Base de controladores

- `infrastructure/common/BaseRestController.java` — extiende todos los controladores REST del proyecto.

---

## 3. Manejo de errores

- Los errores se centralizan en `application/config/exception/ErrorHandlerConfig.java`.
- Los controladores retornan respuestas de éxito; el manejo de excepciones y formato de error vive en el handler.
- Excepciones tipadas: `DomainException`, `ApplicationException`, `InfrastructureException` (en `commons/exception/`).

---

## 4. Persistencia y modelo de datos

### Entidades

- `infrastructure/entities/type/TypeEntity.java`
- `infrastructure/entities/typeCategory/TypeCategoryEntity.java`
- `infrastructure/entities/event/EventAuditEntity.java`

### Relación entre entidades

- `TypeCategoryEntity` 1 --- N `TypeEntity`
- En `TypeEntity`:
    - `typeCategoryId` (columna FK)
    - relación `@ManyToOne` a `TypeCategoryEntity`
- En `TypeCategoryEntity`:
    - relación `@OneToMany(mappedBy = "typeCategory")`

### IDs por secuencia

- Secuencias definidas para `Type`, `TypeCategory` y `EventAudit`.
- Entidades con `@GeneratedValue(strategy = SEQUENCE)` + `@SequenceGenerator`.

---

## 5. Repositorios y operaciones masivas

### Repos command/query específicos por entidad

- **Type:**
    - `...repositories/command/type/ITypeCommandJpaRepository.java`
    - `...repositories/query/type/ITypeQueryJpaRepository.java`
- **TypeCategory:**
    - `...repositories/command/typeCategory/ITypeCategoryCommandJpaRepository.java`
    - `...repositories/query/typeCategory/ITypeCategoryQueryJpaRepository.java`
- **EventAudit:**
    - `...repositories/command/event/IEventAuditCommandJpaRepository.java`
    - `...repositories/query/event/IEventAuditQueryJpaRepository.java`

### EventAudit (repositorio simple para auditoría)

- `infrastructure/event/EventAuditRepository.java` — `JpaRepository` simple usado por `EventAuditServiceImpl`, escaneado en el basePackage del `CommandJpaConfig`.

### Optimización masiva aplicada

- `updateAll(...)` en bloque con `saveAll(...)`.
- `deleteAll(...)` en bloque con `deleteAllByIdInBatch(...)`.
- Repos command con frontera transaccional declarada.

---

## 6. Configuración JPA (dual datasource)

### Configuración usada

- Command datasource: `infrastructure/config/jpa/command/CommandJpaConfig.java`
    - `basePackages`: `...repositories.command` + `...event` (para `EventAuditRepository`)
- Query datasource: `infrastructure/config/jpa/queries/QueryJpaConfig.java`
    - `basePackages`: `...repositories.query`
- Base común: `infrastructure/config/jpa/AbstractJpaConfig.java`
- Credenciales: `infrastructure/config/jpa/DataSourceSecretConfig.java` → lee de variables de entorno.

### Variables de entorno requeridas (perfil dev)

Definidas en `.env` (no subir al repo, ver `.example.env`):

```
SPRING_DATASOURCE_COMMAND_URL
SPRING_DATASOURCE_COMMAND_USERNAME
SPRING_DATASOURCE_COMMAND_PASSWORD
SPRING_DATASOURCE_QUERY_URL
SPRING_DATASOURCE_QUERY_USERNAME
SPRING_DATASOURCE_QUERY_PASSWORD
```

---

## 7. Paginación y defaults

### Dónde se normaliza

- `commons/cqrs/PaginatedQueryAbstract.java`

### Base de casos de uso

- `application/common/BaseUseCase.java` — provee `executeProcessor()` y `buildPageContext()` para estandarizar la orquestación en todos los `UseCase`.

### Defaults centralizados

- `pageNumber = 0`
- `pageSize = 10`
- `sortBy = "id"`
- `sortDir = "asc"`
- `filterType = "CONTAINING"`

---

## 8. CQRS — Clases base (commons)

Todas las clases abstractas CQRS residen en `commons/cqrs/`:

| Clase | Tipo | Uso |
|---|---|---|
| `CommandProcessAbstract` | Write | Base para todos los Commands (Create, Update, Delete) |
| `QueryAbstract` | Read | Base para consultas simples (getById) |
| `PaginatedQueryAbstract` | Read | Base para consultas paginadas |
| `ComboQueryAbstract` | Read | Base para listados combo (sin paginación) |

---

## 9. Servicios de dominio (beans de configuración)

Los `DomainService` del dominio no usan `@Service` (el dominio es agnóstico a Spring). Se registran como beans en:

- `infrastructure/config/domain/DomainServicesConfig.java`
    - `TypeCategoryDomainService`
    - `TypeDomainService`

---

## 10. Kafka / Mensajería

- `KafkaEventPublisher` — implementación de `IEventPublisher`, habilitada condicionalmente con `app.messaging.kafka.enabled=true`.
- `KafkaEventListenerAspect` — AOP para interceptar listeners y registrar eventos entrantes.
- En perfil `dev`, Kafka está deshabilitado: `app.messaging.kafka.enabled=false`.

---

## 11. Validaciones e internacionalización

### DTOs request con Jakarta Validation

- Validaciones de null/blank/size en DTOs de create/update.
- Mensajes por clave i18n (`messages*.properties`).

### Catálogo de mensajes

- `application/src/main/resources/messages.properties` (default: español)
- `application/src/main/resources/messages_en.properties`
- `application/src/main/resources/messages_pt.properties`

---

## 12. Scripts SQL y estrategia DB (PostgreSQL)

### Carpeta de scripts

- `scripts/create_table_type_category.ddl`
- `scripts/create_table_type.ddl`
- `scripts/add_constraints_type.ddl`

### Criterios aplicados

- `CREATE ... IF NOT EXISTS`.
- Secuencias y `DEFAULT nextval(...)` para IDs.
- Constraints con enfoque idempotente.

---

## 13. Configuración local de desarrollo

### Arranque en perfil dev

```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

### Puertos

- HTTP API: `http://localhost:8081`
- gRPC: `9091`
- Swagger UI: `http://localhost:8081/swagger-ui.html`

### Properties del perfil dev

Las properties del perfil `dev` están en:

- `infrastructure/src/main/resources/application-dev.properties` ← **el que aplica al arrancar**
- `application/src/main/resources/application-dev.properties` ← properties del módulo application (tests, etc.)

Las credenciales sensibles se leen desde variables de entorno. Ver `.example.env` en la raíz para el listado completo de variables necesarias.

### Spring Cloud Config

Deshabilitado en dev local:
```properties
spring.cloud.config.enabled=false
spring.cloud.config.import-check.enabled=false
```

---

## 14. Estado de documentación

- Esta guía es la fuente de verdad del estado actual.
- Documentación de arquitectura: `docs/architecture/`
- Guía de configuración local: `docs/architecture/local-setup.md`
- Documentación histórica (no canónica): `docs/archive/`
- Pendientes de seguridad/proceso: `docs/technical-details/security-implementation.md`
