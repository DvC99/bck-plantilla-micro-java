package co.com.empresa.application.typecategory;


/**
 * DTO record de filtros para consultas de {@code TypeCategory}.
 *
 * @param id     identificador de la categoría
 * @param name   nombre de la categoría
 * @param code   código de la categoría
 * @param active estado de vigencia
 */
public record TypeCategoryFilterDto(
        Long id,
        String name,
        String code,
        Boolean active
) {
}










