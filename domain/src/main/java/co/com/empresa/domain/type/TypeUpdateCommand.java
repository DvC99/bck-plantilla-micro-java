package co.com.empresa.domain.type;


/**
 * Comando para la actualización de la información de un tipo existente.
 *
 * @param context objeto {@code Type} con los datos actualizados
 */
public record TypeUpdateCommand(Type context) {

}