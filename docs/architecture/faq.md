# ❓ Frequently Asked Questions (FAQ) - BCK-Plantilla

This document addresses common doubts and technical justifications regarding the architectural decisions and operational
procedures of the BCK-Plantilla project.

---

## 🏗️ Architectural Reasoning (The "Why")

### 1. Why use `Command` and `Query` records if I already have the `Entity`?

The entities represent the **State** (What it is), while Commands and Queries represent the **Intent** (What we want to
do).

- **The Entity (`Type`)**: Describes the business object.
- **The Command/Query (`TypeCreateCommand`)**: Encapsulates the specific request.

**Key benefits:**

- **Intent Clarity**: `processor.execute(new TypeCreateCommand(type))` is explicitly an order to *create*, whereas
  `processor.execute(type)` is ambiguous.
- **Operation Metadata**: Commands allow adding data that doesn't belong to the entity (e.g., `userId` of the operator,
  `sourceIP`, `auditToken`) without polluting the domain model.
- **Immutability**: Records ensure that the intent cannot be changed once it's passed to the processor.

### 2. Why is the `MainApplication` class in the `infrastructure` module?

To prevent **Circular Dependencies**.

In a clean architecture, the dependency flow is: `Infrastructure` $\rightarrow$ `Application` $\rightarrow$ `Domain`. If
the Main class (the bootstrap) were in the `application` module, it would have to depend on `infrastructure` to
configure things like DB and Kafka, creating a cycle (`App` $\rightarrow$ `Infra` $\rightarrow$ `App`). Placing it in
`infrastructure` ensures the flow remains unidirectional.

### 3. Who calls the Processor, and who receives the response?

The flow follows a strict chain of responsibility:
`Controller` $\rightarrow$ `UseCase` $\rightarrow$ `Processor` $\rightarrow$ `Repository`.

- **Caller**: The `UseCase` is the orchestrator. It creates the `Command/Query` record and calls the
  `Processor.execute()` method.
- **Receiver**: The `Processor` returns a Domain Object to the `UseCase`. The `UseCase` then maps this object to a
  `ResponseDto` and returns it to the `Controller`.

### 4. Why does the Controller receive a DTO and not the Command directly?

To decouple the **External Contract** from the **Internal Logic**.

- **DTOs** are for the API. They handle JSON serialization and input validation (`@Valid`).
- **Commands** are for the Domain. They encapsulate business intent.
  If we change a business rule in the Command, we don't want to force all API clients to change their JSON request
  format.

### 5. What is the difference between a Domain Service and a Processor? Why do I need both?

They have fundamentally different roles:

- **Domain Service**: Contains **pure business rules** and reusable logic (e.g., `validateUniqueness()`). It is a '
  stateless expert' that doesn't know about the request lifecycle; it only knows how to validate or calculate business
  rules.
- **Processor**: Is the **orchestrator of the operation**. It defines the *execution flow* (`preProcess` $\rightarrow$
  `process` $\rightarrow$ `postProcess`). It uses the Domain Service to validate the business rules and the Repository
  to persist the data.

**In short**: The Service provides the **'Rule'**, and the Processor provides the **'Procedure'**.

---

## 🛠️ Operation & Troubleshooting (The "How")

### 1. How do I run the application?

Since this is a multi-module project, the entry point is in the `infrastructure` module. Use the following command:

```bash
./gradlew :infrastructure:bootRun
```

### 2. Why does IntelliJ show "Main class not found" or fail to run?

The IDE often caches old run configurations from the `application` module. To fix this:

1. Navigate to `infrastructure/src/main/java/co/com/empresa/infrastructure/MainApplication.java`.
2. **Right-click** the file $\rightarrow$ **Run 'MainApplication.main()'**.
   This creates a new, correct run configuration.

### 3. What to do if I see a `NoClassDefFoundError` related to gRPC?

This usually happens due to a version mismatch between `grpc-core` and `grpc-api` (Dependency Hell).
**Fix:** Ensure all gRPC dependencies are aligned to the same version (currently `1.63.0`) in the root `build.gradle`.

### 4. What to do if the app fails with `Could not resolve placeholder`?

This happens when a property is missing in the Config Server or local properties.
**Fix:** Always provide a default value in your `@Value` annotations:
`@Value("${spring.datasource.hikari.maximum-pool-size:10}")`

### 5. How do I change the active profile (Environment)?

You can change the profile in `application.properties` or via a JVM argument:

```bash
./gradlew :infrastructure:bootRun -Dspring.profiles.active=production
```

---

## 🚀 Quick Summary of the Data Flow

`JSON` $\rightarrow$ `RequestDto` $\rightarrow$ `Domain Entity` $\rightarrow$ `Command/Query Record` $\rightarrow$
`Processor` $\rightarrow$ `Repository` $\rightarrow$ `Database`
*(And then the same path in reverse for the response)*
