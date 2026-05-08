# Hexagonal Architecture & DDD Synergy

## 🧩 The Hybrid Approach

We don't just use "folders"; we use a strict boundary system where the **Domain** is the sovereign center.

### 1. Rich Domain Model (DDD)

Unlike "Anemic Domain Models" where entities are just getters and setters, our entities are **Rich**. They encapsulate
their own business rules.

**Key Pattern: Internal Validation**
Entities must always be in a valid state. We use a `validate()` method called before any persistence.

```java
public class Type {
    private String name;

    public void validate() {
        if (name == null || name.isBlank()) {
            throw new DomainException("Name is required");
        }
        // Other business rules...
    }
}
```

### 2. Ports and Adapters (Hexagonal)

The Domain defines **Ports** (Interfaces). The Infrastructure provides **Adapters** (Implementations).

- **Input Port:** An interface in the domain that the Application calls (e.g., `ITypeService`).
- **Output Port:** An interface in the domain that the Infrastructure implements (e.g., `ITypeRepository`).

**Benefit:** We can change the database from PostgreSQL to MongoDB by only changing the `Infrastructure` adapter,
without touching a single line of `Domain` or `Application` code.

### 3. Domain Services

When a business process involves multiple entities or requires an external port (like a repository) to make a decision,
we use a **Domain Service**.

**Example:** Checking if a "Type" name is unique before creating it. The `TypeDomainService` (Domain) uses the
`ITypeRepository` (Port) to check uniqueness.

#### The `@DomainService` Annotation

Domain Services are annotated with `@DomainService` (defined in `domain/common/DomainService.java`). This is a pure
Java annotation with no Spring dependency, preserving Domain Purity.

The Infrastructure layer (`DomainServicesBeanRegistrar`) scans for this annotation at startup and registers each
class as a Spring singleton bean with constructor autowiring. This means:

- **No `DomainServicesConfig.java`** manually listing every service.
- **Adding a new Domain Service requires only one step**: annotate the class with `@DomainService`.
- The Domain layer never imports `org.springframework.*`.

```java
// domain/typecategory/TypeCategoryDomainService.java
@DomainService  // <-- pure Java annotation, no Spring dependency
public class TypeCategoryDomainService {
    public TypeCategoryDomainService(ITypeCategoryRepository repo, ITypeRepository typeRepo) { ... }
}
```

Spring resolves constructor dependencies from the application context automatically.

## 📂 Package Structure by Feature

We abandoned the `model/`, `services/`, `repository/` global folders in favor of **Feature Packaging**.

**Old Way (Layered):**

- `domain.model.Type`
- `domain.services.TypeService`
- `domain.repository.ITypeRepository`

**New Way (Feature):**

- `domain.type.Type`
- `domain.type.TypeService`
- `domain.type.ITypeRepository`

**Why?** This improves **Locality of Change**. When you work on the "Type" feature, all related files are in the same
package, reducing cognitive load and navigation time.
