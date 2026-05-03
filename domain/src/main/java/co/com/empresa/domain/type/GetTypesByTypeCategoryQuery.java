package co.com.empresa.domain.type;


/**
 * Consulta para obtener la lista de tipos asociados a una categoría aplicando filtros.
 *
 * @param context objeto {@code Type} utilizado como criterio de filtro
 */
public record GetTypesByTypeCategoryQuery(Type context) {

}