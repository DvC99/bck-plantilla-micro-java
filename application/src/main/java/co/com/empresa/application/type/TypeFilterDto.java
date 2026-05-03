package co.com.empresa.application.type;/**
 * DTO record de filtros para consultas de {@code Type}.
 *
 * @param id             identificador del tipo
 * @param typeCategoryId identificador de la categoría
 * @param name           nombre del tipo
 * @param code           código del tipo
 * @param active         estado de vigencia
 */
public record TypeFilterDto(
        Long id,
        Long typeCategoryId,
        String name,
        String code,
        Boolean active
) {
}
