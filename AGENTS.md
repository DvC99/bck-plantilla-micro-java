# AGENTS.md - BCK-Plantilla

## Agent Framework Overview

This project uses a sophisticated multi-agent system to ensure architectural integrity and development efficiency.
Instead of simple prompts, we use a structured framework of **Definitions**, **Skills**, and **Governance**.

### 🏗️ System Structure

The agent system is located in the `.agents/` directory:

- **`definitions/`**: The "Brains". YAML files defining agent identities (e.g., `ArchAuditAgent`).
- **`skills/`**: The "Know-How". Unified suites of instructions for specific capabilities.
- **`core/`**: The orchestration engine (Base agents, factory, and orchestrator).
- **`memory/`**: Context persistence (Vector stores for RAG and session history).
- **`prompts/`**: System instructions and dynamic templates.
- **`tools/`**: Technical adapters (Git, Terminal, API, DB).

### 🚀 Available Skill Suites

Developers and AI agents should activate the following suites depending on the task:

1. **`architecture-core`**: Hexagonal Architecture, CQRS, EDA, and Governance.
2. **`architecture-design`**: UML, ArchiMate, and PlantUML diagrams for system visualization.
3. **`java-ecosystem`**: Spring Boot 4, JUnit 5, and Javadoc standards.
4. **`dev-workflow`**: Git PRs, Diátaxis documentation, and Release process.

### 🛡️ Architecture Governance (The Quality Gate)

To prevent architectural decay, the project uses the `architecture-governance` skill.
**Rule**: Before finalizing any feature or creating a PR, you MUST trigger the `ArchAuditAgent` to validate the code.
The agent will check for:

- JPA leakage in the domain.
- Dependency rule violations.
- Incorrect packaging.
- Event standard violations.

### 🛠️ Setup & Usage

**Initial Setup:**

```bash
cd /path/to/project
opencode
/init
```

This generates the `.agents/` folder with project-specific skills.

---

## Architecture & Core Principles

(Rest of the document remains same...)

## Core Principles

- **Feature-based Packaging:** Code is organized by business feature (e.g., `/type`, `/typecategory`) rather than by
  technical layer.
- **Statelessness:** All handlers and services are Spring Singletons and stateless.
- **Fail Fast:** Validation occurs at the DTO level (Application) and at the Entity level (Domain).

## Developer Commands

- **Build:** `./gradlew clean build`
- **Run:** `./gradlew bootRun`
- **Test:** `./gradlew test`
- **Code Coverage:** `./gradlew jacocoTestReport`

## Technology Stack

- **Java:** 25
- **Spring Boot:** 4.0.0
- **DB:** PostgreSQL
- **API:** OpenAPI 3.0 / Swagger UI (`http://localhost:8080/swagger-ui.html`)
- **Tests:** JUnit 5, Mockito, EasyRandom

## Conventions

- **CQRS Pattern:** Separate commands (write) and queries (read).
    - **Naming:** Use `*CreateCommand`, `*UpdateCommand`, `*DeleteCommand` for writes, and `*Query` for reads.
    - **Base Classes:** Inherit from `domain/common/` abstracts: `CommandProcessAbstract`, `QueryAbstract`,
      `PaginatedQueryAbstract`, `ComboQueryAbstract`.
- **Error Handling:** Use `domain/constants/DomainErrors.java` for standardized business errors.
- **i18n:** Use `Accept-Language` header (`es` default, `en`, `pt`).
- **Persistence:** Spring Data JPA.
- **Validation:** Bean Validation (JSR-380).
- **Profiling:** Default profile is `dev`. Change via `spring.profiles.active`.

## Internationalization (i18n)

### Supported Languages

- **es** - Español (default)
- **en** - English
- **pt** - Português

### Configuration

- **Header:** Use `Accept-Language` to set the language
- **Files:** `application/src/main/resources/messages.properties` (default), `messages_en.properties`,
  `messages_pt.properties`

### Message Keys by Layer

Each layer has its own constants file that maps to the message properties:

| Layer             | Constants File                                       | Purpose                                            |
|-------------------|------------------------------------------------------|----------------------------------------------------|
| `commons/`        | `commons/constants/MessageKeys.java`                 | Generic keys (success, general errors, validation) |
| `domain/`         | `domain/constants/DomainErrors.java`                 | Business logic errors                              |
| `application/`    | `application/constants/ApplicationErrors.java`       | DTO validation errors                              |
| `infrastructure/` | `infrastructure/constants/InfrastructureErrors.java` | Infrastructure and pagination errors               |
| `infrastructure/` | `infrastructure/constants/RestConstants.java`        | REST endpoints, Swagger tags, success messages     |

### Key Naming Conventions

- **DTO Validation:** `validation.{entity}.{field}.{type}` (e.g., `validation.type.name.required`)
- **Domain Errors:** `error.domain.valid.{entity}.{detail}` (e.g., `error.domain.valid.typeCategory.name.empty`)
- **Infrastructure Errors:** `error.infrastructure.{detail}` (e.g., `error.infrastructure.no.registro.by.id`)
- **Success Messages:** `success.{entity}.{operation}` (e.g., `success.typeCategory.created`)
- **Swagger Docs:** `doc.{entity}.{operation}` (e.g., `doc.typeCategory.controller`)

### Placeholders

Use `{0}`, `{1}`, etc. for dynamic parameters in messages.

## Verification Flow

Run in order: `build` -> `test` -> `jacocoTestReport` (if coverage is needed).
