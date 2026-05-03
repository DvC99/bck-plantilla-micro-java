package co.com.empresa.domain.typecategory;


/**
 * Comando para la eliminación de una categoría de tipos del sistema.
 *
 * @param context objeto {@code TypeCategory} que contiene el identificador de la categoría a eliminar
 */
public record TypeCategoryDeleteCommand(TypeCategory context) {

}

