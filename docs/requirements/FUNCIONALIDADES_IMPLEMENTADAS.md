# Funcionalidades Implementadas (Consolidado)

## 1. API REST y flujo de capas

### Flujo aplicado

`controller -> command/query -> service -> repository`

### Controladores principales

- `infrastructure/type/TypeRestController.java`

- `infrastructure/typecategory/TypeCategoryRestController.java`

### Criterios implementados

- Endpoints de escritura con `@Transactional(rollbackFor = Exception.class)`.

- Commands ejecutados en modo síncrono por request (`setAsync(false)` en writes).

- Queries para lectura (id, combo, paginado).

- Paginado y combo expuestos como `GET` con `@RequestParam`.

## 2. Respuestas unificadas

### Tipos de respuesta usados en controladores

- `success(...)` para respuestas no paginadas ni lista.

- `successList(...)` para listados simples.

- `paginated(...)` para respuestas paginadas.

### Builder de respuestas

- `commons/helper/ApiResponseBuilder.java`

## 3. Manejo de errores

- Los errores se centralizan en `application/config/exception/ErrorHandlerConfig.java`.

- Los controladores retornan respuestas de éxito; el manejo de excepciones y formato de error vive en el handler.

## 4. Persistencia y modelo de datos

### Entidades

- `infrastructure/entities/type/TypeEntity.java`

- `infrastructure/entities/typeCategory/TypeCategoryEntity.java`

### Relación entre entidades

- `TypeCategoryEntity` 1 --- N `TypeEntity`

- En `TypeEntity`:

    - `typeCategoryId` (columna FK mantenida por compatibilidad)

    - relación `@ManyToOne` a `TypeCategoryEntity`

- En `TypeCategoryEntity`:

    - relación `@OneToMany(mappedBy = "typeCategory")`

### IDs por secuencia

- Secuencias definidas para Type y TypeCategory.

- Entidades con `@GeneratedValue(strategy = SEQUENCE)` + `@SequenceGenerator`.

## 5. Repositorios y operaciones masivas

### Repos command/query específicos por entidad

- Type:

    - `...repositories/command/type/*`

    - `...repositories/query/type/*`

- TypeCategory:

    - `...repositories/command/typeCategory/*`

    - `...repositories/query/typeCategory/*`

### Optimización masiva aplicada

- `updateAll(...)` en bloque con `saveAll(...)`.

- `deleteAll(...)` en bloque con `deleteAllByIdInBatch(...)`.

- Repos command con frontera transaccional declarada.

## 6. Configuración JPA (dual datasource)

### Configuración usada

- Command datasource: `infrastructure/config/jpa/command/CommandJpaConfig.java`

- Query datasource: `infrastructure/config/jpa/queries/QueryJpaConfig.java`

- Base común: `infrastructure/config/jpa/AbstractJpaConfig.java`

### Correcciones clave aplicadas

- `basePackages` corregidos a `...repositories.command` y `...repositories.query`.

- `EntitiesConstants.PACKAGE_ENTITIES` corregido a `com.empresa.plantilla.infrastructure.entities`.

## 7. Paginación y defaults

### Dónde se normaliza

- `commons/cqrs/PaginatedQueryAbstract.java`

### Defaults centralizados

- `pageNumber = 0`

- `pageSize = 10`

- `sortBy = "id"`

- `sortDir = "asc"`

- `filterType = "CONTAINING"`

## 8. Validaciones e internacionalización

### DTOs request con Jakarta Validation

- Validaciones de null/blank/size en DTOs de create/update.

- Mensajes por clave i18n (`messages*.properties`).

### Catálogo de mensajes

- `application/src/main/resources/messages.properties`

- `application/src/main/resources/messages_en.properties`

- `application/src/main/resources/messages_pt.properties`

## 9. Scripts SQL y estrategia DB (PostgreSQL)

### Carpeta de scripts

- `scripts/create_table_type_category.ddl`

- `scripts/create_table_type.ddl`

- `scripts/add_constraints_type.ddl`

### Criterios aplicados

- `CREATE ... IF NOT EXISTS`.

- Secuencias y `DEFAULT nextval(...)` para IDs.

- Constraints con enfoque idempotente.

## 10. Estado de documentación

- Esta guía reemplaza reportes temporales y resúmenes duplicados.

- Para pendientes de negocio/proceso no implementados, ver:

    - `docs/technical-details/security-implementation.md`

    - `docs/requirements/plan-infrastructureTypeTypeCategory.prompt.md`
