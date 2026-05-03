package co.com.empresa.domain.typecategory;


/**
 * Comando para la actualización de la información de una categoría de tipos existente.
 *
 * @param context objeto {@code TypeCategory} con los datos actualizados
 */
public record TypeCategoryUpdateCommand(TypeCategory context) {

}

