package co.com.empresa.domain.type;


/**
 * Comando para la creación de un nuevo tipo en el sistema.
 *
 * @param context objeto {@code Type} con los datos necesarios para la creación
 */
public record TypeCreateCommand(Type context) {

}