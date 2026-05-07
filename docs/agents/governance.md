# Architecture Governance & Auditing

This document details how the project ensures architectural integrity through the automated auditing system.

## 🛡️ The Guard Rail Philosophy

The project employs a "Guard Rail" approach. Instead of relying on developer memory, the system uses a specialized
agent (`ArchAuditAgent`) to verify that every line of code adheres to the project's core principles.

## 🔍 Audit Checklist

The `ArchAuditAgent` scans the codebase for the following "Red Flags":

### 1. Domain Purity (Critical)

- **Rule**: The `domain/` module must have zero external framework dependencies (except Lombok for boilerplate reduction).
- **Violation**: Presence of `@Entity`, `@Table`, `@Id`, or imports from `jakarta.persistence.*` or
  `org.springframework.data.jpa.*`.

### 2. Dependency Direction

- **Rule**: Dependencies only point inwards (`Infrastructure` $\rightarrow$ `Application` $\rightarrow$ `Domain`).
- **Violation**: Any import in `domain/` that references classes in `application/` or `infrastructure/`.

### 3. Packaging Strategy

- **Rule**: Feature-Based Packaging is mandatory.
- **Violation**: Folders named `service/`, `repository/`, or `controller/` at the root of the module.
- **Correct**: `infrastructure/feature_name/service/`.

### 4. Event Standards

- **Rule**: All events must use `EventEnvelope` and be published via `IEventPublisher`.
- **Violation**: Direct use of `KafkaTemplate` in the application or domain layers.
- **Idempotency**: Absence of `event_audit` check in event listeners.

## 🚀 Governance Workflow

1. **Implementation**: The developer/agent implements the feature.
2. **Audit Trigger**: The `architecture-governance` skill is activated.
3. **Execution**: The `ArchAuditAgent` scans the modified files.
4. **Reporting**: The agent generates a report in the following format:
    - ❌ **VIOLACIÓN**: [Rule]
    - 📍 **UBICACIÓN**: `file:line`
    - 📝 **RAZÓN**: [Explanation]
    - 🛠️ **SOLUCIÓN**: [Steps to fix]
5. **Remediation**: The developer fixes the issues.
6. **Validation**: The audit is rerun until the result is: "✅ Arquitectura validada."
