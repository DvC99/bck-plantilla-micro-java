# Guía de Reutilización

Esta guía define qué tipo de componentes deben ubicarse en cada módulo y cómo reutilizarlos para simplificar
la implementación de nuevos features.

## Principio de decisión

Antes de mover o crear código, responde:

1. ¿Es un componente transversal que se reutiliza en más de un módulo?
2. ¿Evita duplicación de lógica sin introducir reglas de negocio específicas?

Si ambas respuestas son "sí", el componente pertenece a `commons`. Si la lógica es de infraestructura (JPA, Spring),
va en `infrastructure/common/`.

---

## Qué va en `commons`

- **Utilidades y helpers**: fechas, validaciones genéricas, formateo, paginación.
- **DTOs comunes**: contratos reutilizables en múltiples módulos.
- **CQRS base**: abstractos de comando y consulta (`CommandProcessAbstract`, `QueryAbstract`, etc.).
- **Excepciones base**: jerarquía compartida de errores.
- **Interfaces de repositorio**: `IRepository`, `ICommandRepository`, `IQueryRepository`, `IGenericService`.
- **`GenericServiceImpl<M,K>`**: implementación base de `IGenericService` que delega a `IRepository`.

## Qué va en `infrastructure/common`

- **`AbstractRepositoryImpl<M,E,K>`**: implementa los 10 métodos de `IRepository` una sola vez. Cada
  `*RepositoryImpl` de feature extiende esta clase en lugar de re-implementarlos.
- **`BaseRestController`**: helpers `success`, `successList`, `paginated` para estandarizar respuestas.
- **`IJpaCommandRepository<E,K>` / `IJpaQueryRepository<E,K>`**: interfaces base `@NoRepositoryBean` que
  documentan el binding al datasource correcto.

## Qué va en `domain/common`

- **`@DomainService`**: anotación marcadora pura (sin dependencias Spring). Se aplica a clases de dominio
  que necesitan ser registradas como beans por la infraestructura sin contaminar el dominio con `@Service`.

---

## Patrones de simplicidad activos

| Patrón | Clase | Beneficio |
|---|---|---|
| `AbstractRepositoryImpl` | `infrastructure/common/` | ~200 líneas → ~15 por feature |
| `GenericServiceImpl` | `commons/services/impl/` | CRUD delegado, solo 3 overrides |
| `BaseUseCase` | `application/common/` | `executeProcessor()` y `buildPageContext()` sin repetición |
| `BaseRestController` | `infrastructure/common/` | Respuestas estandarizadas sin repetición |
| `@DomainService` | `domain/common/` | Bean auto-registrado por Spring (estereotipo con meta-`@Component`) |
| `IJpaCommandRepository` / `IJpaQueryRepository` | `infrastructure/adapters/repositories/` | Binding CQRS explícito y documentado |

---

## Qué NO va en commons

- Reglas de negocio específicas.
- Lógica de un dominio concreto (por ejemplo, "type", "typecategory").
- Acceso a infraestructura o dependencias externas que acoplen la capa.
- Anotaciones Spring como `@Service`, `@Repository` (solo van en infrastructure/application).

---

## Checklist para nuevos features

Al implementar un nuevo feature (ej: `NewEntity`):

- [ ] `*RepositoryImpl` extiende `AbstractRepositoryImpl<NewEntity, NewEntityEntity, Long>`
- [ ] `INew*CommandJpaRepository` extiende `IJpaCommandRepository<NewEntityEntity, Long>`
- [ ] `INew*QueryJpaRepository` extiende `IJpaQueryRepository<NewEntityEntity, Long>` + `getNextValSequence()`
- [ ] `NewEntityDomainService` anotado con `@DomainService` (sin `DomainServicesConfig` manual)
- [ ] `New*ServiceImpl` extiende `GenericServiceImpl<NewEntity, Long>` (solo 3 overrides)
- [ ] Controlador extiende `BaseRestController`
- [ ] No crear clases `*Feature.java` vacías
