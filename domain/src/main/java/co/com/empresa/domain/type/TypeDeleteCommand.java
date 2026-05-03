package co.com.empresa.domain.type;


/**
 * Comando para la eliminación de un tipo del sistema.
 *
 * @param context objeto {@code Type} que contiene el identificador del tipo a eliminar
 */
public record TypeDeleteCommand(Type context) {

}