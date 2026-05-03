package co.com.empresa.domain.type;


/**
 * Consulta para obtener un tipo la base de datos mediante su identificador único.
 *
 * @param id identificador único del tipo
 */
public record GetTypeByIdQuery(Long id) {

}