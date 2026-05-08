# Skills Map: Agent Capabilities

This document maps the capabilities provided by the agent skills to the development tasks they solve.

## Architecture Core Suite

**Trigger**: Use this when designing new features, modifying the domain, or implementing business logic.

| Capability | Description | Key Components |
|:---|:---|:---|
| **Hexagonal Architecture** | Ensures strict separation of layers and dependency flow. | Ports, Adapters, Domain Purity |
| **CQRS Pattern** | Implements separation of Reads (Queries) and Writes (Commands). | `CommandProcessAbstract`, `QueryAbstract` |
| **Repository Pattern** | Generic base eliminates per-feature boilerplate in repository adapters. | `AbstractRepositoryImpl<M,E,K>`, `IJpaCommandRepository`, `IJpaQueryRepository` |
| **Domain Service Auto-Registration** | `@DomainService` annotation + scanner replaces manual `DomainServicesConfig`. | `DomainService`, `DomainServicesBeanRegistrar` |
| **Event-Driven (EDA)** | Implements broker-independent messaging and idempotency. | `EventEnvelope`, `IEventPublisher`, `event_audit` |
| **Architecture Governance** | Performs strict architectural audits to prevent decay. | `ArchAuditAgent` |

## Java Ecosystem Suite

**Trigger**: Use this for technical implementation, testing, and documentation of Java code.

| Capability | Description | Key Components |
|:---|:---|:---|
| **Spring Boot 4** | Applies best practices for configuration, DI, and REST. | `@ConfigurationProperties`, `@RestController` |
| **Feature Checklist** | Step-by-step table for implementing any new entity feature with zero boilerplate. | `AbstractRepositoryImpl`, `GenericServiceImpl`, `@DomainService` |
| **JUnit 5 Testing** | Implements high-coverage unit and parameterized tests. | AAA Pattern, Mockito, `@ParameterizedTest` |
| **Javadoc Standards** | Ensures professional technical documentation of the code. | `@param`, `@return`, `@throws` |

## Architecture Design Suite

**Trigger**: Use this when creating UML/ArchiMate diagrams or communicating architecture visually.

| Capability | Description | Key Components |
|:---|:---|:---|
| **Architecture Design** | Produce PlantUML diagrams and ArchiMate views. | `architecture-design` skill |

## Dev Workflow Suite

**Trigger**: Use this for version control, documentation, and release management.

| Capability | Description | Key Components |
|:---|:---|:---|
| **Git/PR Workflow** | Manages professional commit and PR standards. | Conventional Commits, PR Templates |
| **Diátaxis Docs** | Creates high-quality technical documentation. | Tutorials, How-to, Reference, Explanation |
| **Release Management** | Coordinates the final verification and deployment steps. | `FUNCIONALIDADES_IMPLEMENTADAS.md`, Jacoco |
