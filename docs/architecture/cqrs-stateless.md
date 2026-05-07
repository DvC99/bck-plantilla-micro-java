# Stateless CQRS Implementation

## ⚡ The CQRS Pattern

We separate **Write** operations (Commands) from **Read** operations (Queries) to optimize performance and
maintainability.

## 🛠️ The Template Method Pattern

To avoid repeating the same try-catch, logging, and mapping logic, we implemented a **Stateless Template**.

### 1. Command Flow (`CommandProcessAbstract`)

Commands are designed to change state. The flow is:
`preProcess()` $\rightarrow$ `process()` $\rightarrow$ `postProcess()`.

```java
public abstract class CommandProcessAbstract<C, R> {
    public final R execute(C command) {
        preProcess(command);
        R result = process(command);
        postProcess(result);
        return result;
    }
    protected abstract void preProcess(C command);
    protected abstract R process(C command);
    protected abstract void postProcess(R result);
}
```

### 2. Query Flow (`QueryAbstract`)

Queries are designed to retrieve data without modifying it.

- **Simple Query:** Returns a single DTO or a list.
- **Paginated Query (`PaginatedQueryAbstract`):** Integrates with `PaginationHelper` to return a `Page<R>` object.

## 🔄 Statelessness vs Statefulness

**Crucial Decision:** We moved from stateful handlers to **Stateless Templates**.
In Spring, beans are Singletons by default. Storing request-specific data in class fields leads to race conditions in
multi-threaded environments. All data is now passed explicitly through method arguments.

## 📐 Summary Table

| Feature      | Command                                       | Query                                                    |
|:-------------|:----------------------------------------------|:---------------------------------------------------------|
| **Purpose**  | Change State (Create/Update/Delete)           | Retrieve Data (Read)                                     |
| **Return**   | ID or Success Confirmation                    | DTO or Page of DTOs                                      |
| **Template** | `CommandProcessAbstract`                      | `QueryAbstract` / `PaginatedQueryAbstract`               |
| **Logic**    | Business Validation $\rightarrow$ Persistence | Filtering $\rightarrow$ Mapping $\rightarrow$ Pagination |

## 📦 Ubicación de templates

Los templates CQRS residen en `commons` para reutilización transversal:

- `commons/cqrs/CommandProcessAbstract`
- `commons/cqrs/QueryAbstract`
- `commons/cqrs/PaginatedQueryAbstract`
- `commons/cqrs/ComboQueryAbstract`
