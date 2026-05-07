# Skills Map: Agent Capabilities

This document maps the capabilities provided by the agent skills to the development tasks they solve.

## 🏛️ Architecture Core Suite

**Trigger**: Use this when designing new features, modifying the domain, or implementing business logic.

| Capability                  | Description                                                     | Key Components                                    |
|:----------------------------|:----------------------------------------------------------------|:--------------------------------------------------|
| **Hexagonal Architecture**  | Ensures strict separation of layers and dependency flow.        | Ports, Adapters, Domain Purity                    |
| **CQRS Pattern**            | Implements separation of Reads (Queries) and Writes (Commands). | `CommandProcessAbstract`, `QueryAbstract`         |
| **Event-Driven (EDA)**      | Implements broker-independent messaging and idempotency.        | `EventEnvelope`, `IEventPublisher`, `event_audit` |
| **Architecture Governance** | Performs strict architectural audits to prevent decay.          | `ArchAuditAgent`                                  |

## ☕ Java Ecosystem Suite

## 🧩 Architecture Design Suite

**Trigger**: Use this when creating UML/ArchiMate diagrams or communicating architecture visually.

| Capability            | Description                                   | Key Components                 |
|:----------------------|:----------------------------------------------|:-------------------------------|
| **Architecture Design** | Produce PlantUML diagrams and ArchiMate views | `architecture-design` skill    |

**Trigger**: Use this for technical implementation, testing, and documentation of Java code.

| Capability            | Description                                               | Key Components                                |
|:----------------------|:----------------------------------------------------------|:----------------------------------------------|
| **Spring Boot 4**     | Applies best practices for configuration, DI, and REST.   | `@ConfigurationProperties`, `@RestController` |
| **JUnit 5 Testing**   | Implements high-coverage unit and parameterized tests.    | AAA Pattern, Mockito, `@ParameterizedTest`    |
| **Javadoc Standards** | Ensures professional technical documentation of the code. | `@param`, `@return`, `@throws`                |

## ⚙️ Dev Workflow Suite

**Trigger**: Use this for version control, documentation, and release management.

| Capability             | Description                                              | Key Components                             |
|:-----------------------|:---------------------------------------------------------|:-------------------------------------------|
| **Git/PR Workflow**    | Manages professional commit and PR standards.            | Conventional Commits, PR Templates         |
| **Diátaxis Docs**      | Creates high-quality technical documentation.            | Tutorials, How-to, Reference, Explanation  |
| **Release Management** | Coordinates the final verification and deployment steps. | `docs/requirements/FUNCIONALIDADES_IMPLEMENTADAS.md`, Jacoco |
