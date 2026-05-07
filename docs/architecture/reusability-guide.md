# Guía de Reutilización

Esta guía define qué tipo de componentes deben ubicarse en el módulo `commons` y cómo reutilizarlos para simplificar
la implementación de Historias de Usuario (HU).

## Principio de decisión

Antes de mover o crear código, responde:

1. ¿Es un componente transversal que se reutiliza en más de un módulo?
2. ¿Evita duplicación de lógica sin introducir reglas de negocio específicas?

Si ambas respuestas son "sí", el componente pertenece a `commons`.

## Qué va en commons

- **Utilidades y helpers**: fechas, validaciones genéricas, formateo, paginación.
- **DTOs comunes**: contratos reutilizables en múltiples módulos.
- **CQRS base**: abstractos de comando y consulta usados por diferentes bounded contexts.
- **Excepciones base**: jerarquía compartida de errores.
- **Interfaces de auditoría**: contratos comunes para entidades auditables.

## Patrones de simplicidad (Application/Infrastructure)

- **BaseUseCase**: helpers para ejecutar processors y construir `PageContext` sin repetir bloques de código.
- **BaseRestController**: helpers `success`, `successList`, `paginated` para estandarizar respuestas en controladores.

## Qué NO va en commons

- Reglas de negocio específicas.
- Lógica de un dominio concreto (por ejemplo, "type", "typecategory").
- Acceso a infraestructura o dependencias externas que acoplen la capa.

## Checklist rápida para nuevas HUs

- ¿Existe un patrón repetido en más de un módulo?
- ¿Se puede abstraer sin perder contexto de negocio?
- ¿Puede convertirse en utilitario o base CQRS?

Si la respuesta es "sí" a una de estas, evalúa moverlo a `commons`.
