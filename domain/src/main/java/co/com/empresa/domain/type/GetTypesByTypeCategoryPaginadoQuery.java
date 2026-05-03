package co.com.empresa.domain.type;

import co.com.empresa.commons.dto.pageable.PageContext;


/**
 * Consulta paginada para obtener los tipos asociados a una categoría aplicando filtros.
 *
 * @param context contexto de paginación que incluye el criterio de filtro {@code Type} y parámetros de página
 */
public record GetTypesByTypeCategoryPaginadoQuery(PageContext<Type> context) {

}