# Infrastructure Pragmatism: Generic Implementation

## The Boilerplate Problem

Standard Hexagonal Architecture often leads to "Class Explosion": for every entity, you end up with a `ServiceImpl`,
a `RepositoryImpl`, and 5–6 nearly identical CRUD methods that copy-paste across every feature.

---

## Solution 1: `AbstractRepositoryImpl<M, E, K>`

Located at `infrastructure/common/AbstractRepositoryImpl.java`, this generic abstract class implements **all ten methods
of the `IRepository<M, K>` contract** once. Every feature's `*RepositoryImpl` simply extends it and provides the three
constructor dependencies.

### What it provides automatically

- `findAll(Example<M>, Pageable)` — with entity-level Example mapping via the mapper
- `findAll(Example<M>)` — list variant
- `findById(K)` — delegates to query JPA repo
- `existsById(K)` — delegates to query JPA repo
- `save(M)`, `saveAll(Iterable<M>)` — delegates to command JPA repo
- `update(M)`, `updateAll(Iterable<M>)` — delegates to command JPA repo
- `delete(K)` — `deleteById` on command JPA repo
- `deleteAll(Iterable<K>)` — `deleteAllByIdInBatch` for bulk deletes

### What the subclass provides

- **Constructor call** with `commandJpaRepository`, `queryJpaRepository`, and `mapper`.
- **`getNextValSequence()`** override, delegating to the query JPA repository's native `@Query`.

### Before vs After

```java
// BEFORE — ~200 lines, 10 methods copy-pasted per feature
@Repository
@Transactional
public class TypeCategoryRepositoryImpl implements ITypeCategoryRepository {
    // 200 lines of identical plumbing
}

// AFTER — ~15 lines, only what is unique
@Repository
@Transactional
public class TypeCategoryRepositoryImpl
        extends AbstractRepositoryImpl<TypeCategory, TypeCategoryEntity, Long>
        implements ITypeCategoryRepository {

    private final ITypeCategoryQueryJpaRepository queryJpaRepository;

    public TypeCategoryRepositoryImpl(ITypeCategoryCommandJpaRepository cmd,
                                      ITypeCategoryQueryJpaRepository qry,
                                      TypeCategoryInfrastructureMapper mapper) {
        super(cmd, qry, mapper);
        this.queryJpaRepository = qry;
    }

    @Override
    public Long getNextValSequence() {
        return queryJpaRepository.getNextValSequence();
    }
}
```

---

## Solution 2: `IJpaCommandRepository` and `IJpaQueryRepository`

Base marker interfaces (`@NoRepositoryBean`) that replace the anonymous `extends JpaRepository<E,K>` in every
feature-specific JPA interface. They serve two purposes:

1. **Documentation**: make the CQRS datasource binding explicit in the type hierarchy.
2. **Convention enforcement**: any future tooling or governance audit can detect repos that don't follow the separation.

```java
// command base — infrastructure/adapters/output/repositories/command/IJpaCommandRepository.java
@NoRepositoryBean
public interface IJpaCommandRepository<E, K> extends JpaRepository<E, K> { }

// query base — infrastructure/adapters/output/repositories/query/IJpaQueryRepository.java
@NoRepositoryBean
public interface IJpaQueryRepository<E, K> extends JpaRepository<E, K> { }
```

Feature-specific repos extend the appropriate base:

```java
@Repository
public interface ITypeCategoryCommandJpaRepository
        extends IJpaCommandRepository<TypeCategoryEntity, Long> { }

@Repository
public interface ITypeCategoryQueryJpaRepository
        extends IJpaQueryRepository<TypeCategoryEntity, Long> {
    @Query(value = "SELECT nextval('type_category_seq')", nativeQuery = true)
    Long getNextValSequence();
}
```

---

## Solution 3: `GenericServiceImpl<M, K>`

Located in `commons/services/impl/GenericServiceImpl.java`, this abstract class provides default implementations for
all methods of `IGenericService<M, K>`, delegating to an `IRepository<M, K>` returned by `getRepository()`.

Feature `*ServiceImpl` classes extend it and override only three methods:

```java
@Service
public class TypeCategoryServiceImpl
        extends GenericServiceImpl<TypeCategory, Long>
        implements ITypeCategoryService {

    @Override protected ITypeCategoryRepository getRepository() { return categoryRepository; }
    @Override protected Long getModelKey(TypeCategory m) { return m == null ? null : m.getId(); }
    @Override protected TypeCategory getEmptyModel() { return TypeCategory.builder().build(); }

    // Custom business logic methods delegate to TypeCategoryDomainService
}
```

---

## Pagination Pipeline

To keep the Domain pure, paginated results flow through a specific pipeline:

1. **Request:** `PaginationRequest` (Record) captures `page`, `size`, and `sort`.
2. **Execution:** `AbstractRepositoryImpl.findAll(Example<M>, Pageable)` returns `Page<M>` (domain models).
3. **Mapping:** Application mapper converts `Page<M>` to `Page<DTO>` using `PaginationHelper`.
4. **Delivery:** Controller returns a standard `GenericResponse` wrapping the page.

---

## Controller Simplification: `BaseRestController`

Located at `infrastructure/common/BaseRestController.java`, provides helpers:

- `success(result, messageKey)` — single object response
- `successList(list, messageKey)` — list response
- `paginated(page, messageKey)` — paginated response

This keeps controllers focused on endpoint routing only; response building is never repeated.

---

## Adapter Strategy Summary

| Component | Location | Responsibility |
|---|---|---|
| `IJpaCommandRepository<E,K>` | `repositories/command/` | Write-side JPA binding marker |
| `IJpaQueryRepository<E,K>` | `repositories/query/` | Read-side JPA binding marker |
| `AbstractRepositoryImpl<M,E,K>` | `infrastructure/common/` | All 10 `IRepository` methods implemented once |
| `GenericServiceImpl<M,K>` | `commons/services/impl/` | All `IGenericService` methods delegating to `IRepository` |
| `BaseRestController` | `infrastructure/common/` | Response builder helpers for all controllers |
