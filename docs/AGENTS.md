# AGENTS Guide

## Scope and source of truth

- Canonical docs for current behavior: `docs/requirements/FUNCIONALIDADES_IMPLEMENTADAS.md` and `docs/PR_DOC_CHECKLIST.md`.
- Historical docs in `docs/archive/` are reference-only (`docs/archive/README.md`).
- Existing AI guidance discovered: root `README.md` (no prior `AGENTS.md`/`CLAUDE.md` rules found).

## Big picture architecture (hexagonal + CQRS)

- Multi-module Gradle project (`settings.gradle`): `application`, `infrastructure`, `domain`, `commons`.
- Entry point in `infrastructure`: `infrastructure/src/main/java/co/com/empresa/infrastructure/MainApplication.java`.
- Domain owns use cases (commands/queries), e.g. `domain/.../command/type/TypeCreateCommand.java` and
  `domain/.../query/type/GetTypeByIdQuery.java`.
- Infrastructure adapts domain ports to Spring/JPA: `infrastructure/.../services/type/TypeServiceImpl.java`,
  `infrastructure/.../repositories/**`.
- Commons provides shared primitives (`commons/cqrs/*`, `GenericResponse`, i18n service).

## Request/data flow you should preserve

- REST flow is: `controller -> command/query -> service -> repository` (`docs/requirements/FUNCIONALIDADES_IMPLEMENTADAS.md`).
- Example write path: `TypeRestController#create` sets `setAsync(false)`, then executes `TypeCreateCommand`.
- Example read path: `TypeRestController#paginado` builds `PageContext`, executes query, maps to DTO, returns
  `responseBuilder.paginated(...)`.
- Keep controllers thin; business validation lives in domain commands (see duplicate-code/category checks in
  `TypeCreateCommand`).

## CQRS + persistence specifics (non-obvious)

- Two datasource/JPA contexts are configured: command vs query (`CommandJpaConfig`, `QueryJpaConfig`,
  `AbstractJpaConfig`, `SecretManager`).
- **All `*RepositoryImpl` classes extend `AbstractRepositoryImpl<M, E, K>`** (`infrastructure/common/`). This base
  class implements all 10 `IRepository` methods once. Subclasses only override `getNextValSequence()`.
- **Feature JPA repos extend `IJpaCommandRepository` or `IJpaQueryRepository`** (both `@NoRepositoryBean`) instead of
  `JpaRepository` directly. This makes the CQRS datasource binding explicit.
- The `Example<M>` → `Example<Entity>` mapping is handled inside `AbstractRepositoryImpl`; subclasses do not repeat it.
- Batch behavior is explicit: `updateAll` via `saveAll`, `deleteAll` via `deleteAllByIdInBatch` — both centralized in
  `AbstractRepositoryImpl`.

## API conventions to follow

- Base routes/constants are centralized in `infrastructure/constants/RestConstants.java` (`/api/types`,
  `/api/type-categories`, `/combo`, `/paginado`).
- Standard response envelope is built via `commons/helper/ApiResponseBuilder.java` (`success`, `successList`,
  `paginated`, `error`).
- Global exception-to-response mapping is centralized in `application/config/exception/ErrorHandlerConfig.java`; avoid
  per-controller try/catch.
- Validation messages are i18n keys from `messages*.properties`; get messages through `MessageService`.

## i18n, async, and web behavior

- Locale defaults to `es`, supports `es/en/pt` (`LocaleConfig`), mainly via `Accept-Language`.
- `LocaleChangeInterceptor` with `?lang=` is enabled (`LocaleConfig` + `WebConfig`).
- Commands inherit async capability via `CommandProcessAbstract` (commons), but current REST writes force sync with
  `setAsync(false)`.
- CORS allowed origins come from `cors.allowedOrigins` and are applied globally in `WebConfig`.

## Developer workflows (Windows-first)

- Build all modules: `./gradlew.bat clean build`
- Run app module: `./gradlew.bat :application:bootRun`
- Run tests (all): `./gradlew.bat test`
- Run domain tests only (fastest signal): `./gradlew.bat :domain:test`
- Wrapper uses Gradle 9.2.1 (`gradle/wrapper/gradle-wrapper.properties`), Java target is 25 (`build.gradle`).

## Integration points

- OpenAPI/Swagger config lives in `application/config/OpenApiConfig.java` and `application.properties` (
  `/swagger-ui.html`, `/v3/api-docs`).
- Kafka producer/consumer config and retry policy are in `infrastructure/config/kafka/KafkaConfig.java`.
- External microservice endpoints are property-driven maps: `grpc.services.*` and `rest.services.*` (`GrpcConfig`,
  `RestConfig`).
- SQL baseline scripts for Type/TypeCategory live in `scripts/` and are written idempotently.

## Domain Service registration

- Domain Services MUST be annotated with `@DomainService` (`domain/common/DomainService.java`). This annotation
  is meta-annotated with `@Component`, making it a **Spring stereotype** that is auto-detected by component scanning.
- No manual configuration class is needed — Spring's `@ComponentScan` (which includes `co.com.empresa.domain`)
  picks up all `@DomainService` classes automatically.
- **No `DomainServicesConfig.java` or `DomainServicesBeanRegistrar.java` exists**: do NOT recreate them.

## Change checklist for agents

- If you touch controllers/responses/errors/persistence/i18n, update `docs/requirements/FUNCIONALIDADES_IMPLEMENTADAS.md` in the same PR.
- Keep module boundaries: no infrastructure dependencies in `domain`; place Spring/web/JPA code in `infrastructure` or `application`.
- For new entity features follow the pattern:
  1. `*RepositoryImpl` extends `AbstractRepositoryImpl<M, E, K>` — override only `getNextValSequence()`.
  2. JPA repos extend `IJpaCommandRepository` / `IJpaQueryRepository` (not `JpaRepository` directly).
  3. `DomainService` annotated with `@DomainService` (Spring stereotype, no manual config needed).
  4. `*ServiceImpl` extends `GenericServiceImpl` — override only `getRepository()`, `getModelKey()`, `getEmptyModel()`.
  5. Do NOT create `*Feature.java` empty marker classes.
