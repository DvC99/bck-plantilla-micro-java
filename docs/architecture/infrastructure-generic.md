# Infrastructure Pragmatism: Generic Implementation

## 🚀 The Boilerplate Problem

Standard Hexagonal Architecture often leads to "Class Explosion": for every entity, you end up with a ServiceImpl, a
RepositoryImpl, and 5-6 nearly identical CRUD methods.

## 🛠️ The Solution: `GenericServiceImpl`

We implemented a generic engine in the infrastructure layer that handles the most common operations using Spring Data
JPA's `ExampleMatcher` and `Pageable`.

### 1. Core Capabilities

The `GenericServiceImpl` provides:

- **Standard CRUD:** Save, Update, Delete, FindById.
- **Advanced Filtering:** Uses `Example` for dynamic queries.
- **Pagination:** Integrated with `PaginationRequest` to handle sorting and paging.
- **Combo-Lists:** Specialized methods to return lightweight DTOs for dropdowns/combos.

### 2. Implementation Example

Instead of writing `save()` in every service, we extend the generic base:

```java
@Service
public class TypeServiceImpl extends GenericServiceImpl<TypeEntity, TypeRepository, Type, TypeEntity> {
    // Only implement specialized business logic here
    // CRUD is inherited automatically
}
```

## 📦 The Pagination Pipeline

To keep the Domain pure, we use a specific pipeline for paginated results:

1. **Request:** `PaginationRequest` (Record) captures `page`, `size`, and `sort`.
2. **Execution:** Repository returns a JPA `Page<Entity>`.
3. **Mapping:** `PaginationHelper.mapPage()` converts the `Page<Entity>` to `Page<DTO>` without losing pagination
   metadata.
4. **Delivery:** The Controller returns a standard Spring `Page` object.

## 🧩 Controller Simplification

To reduce repetition in REST controllers, a `BaseRestController` provides helpers for:

- `success(...)`
- `successList(...)`
- `paginated(...)`

This keeps controllers focused on orchestration only.

## 🔌 Adapter Strategy

Repositories are split into:

- **Command Repositories:** For modifications (Write).
- **Query Repositories:** For complex reads, projections, and reporting (Read).

This prevents the JPA interface from becoming a "God Interface" with 100+ methods.
