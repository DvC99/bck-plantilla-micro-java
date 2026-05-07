# Agent Guidelines: BCK-Plantilla Development

This document serves as a high-level guide for AI agents working on the BCK-Plantilla project.

## 🚀 The Governance-First Workflow

Instead of manually reviewing every change, the project uses an automated governance system. All agents MUST follow this
lifecycle:

1. **Implement**: Use the specialized skills in `.agents/skills/` to implement the feature.
2. **Audit**: Trigger the `architecture-governance` skill.
3. **Remediate**: The `ArchAuditAgent` will provide a Non-Conformance Report. Fix all violations.
4. **Validate**: Rerun the audit until you receive: "✅ Arquitectura validada."

## 🏗️ Core Architectural Guardrails

All agents must adhere to these absolute rules:

- **Pure Domain**: NO JPA annotations or framework dependencies in `domain/`, except Lombok for boilerplate reduction.
- **Dependency Flow**: Infrastructure $\rightarrow$ Application $\rightarrow$ Domain.
- **Feature-Based Packaging**: Organize code by business feature across all modules.
- **Event Standards**: Use `EventEnvelope` and `IEventPublisher` for all messaging.

## 🛠️ Available Intelligence Suites

- **`architecture-core`**: Guidance on Hexagonal, CQRS, and EDA.
- **`architecture-design`**: UML, ArchiMate, and PlantUML diagrams for system visualization.
- **`java-ecosystem`**: Standards for Spring Boot 4, JUnit 5, and Javadoc.
- **`dev-workflow`**: Git, PRs, and Diátaxis documentation.

For detailed technical specifications, refer to the **[Agent Framework Documentation](../agents/overview.md)** and the *
*[Architecture FAQ](../faq.md)**.
