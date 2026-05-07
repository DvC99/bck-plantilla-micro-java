package co.com.empresa.commons.dto.request;


/**
 * Objeto de transferencia de datos para solicitudes de paginación.
 * <p>
 * Agrupa los parámetros comunes de control de página, tamaño y ordenamiento
 * para ser utilizados en consultas paginadas.
 *
 * @param pageNumber número de página solicitado (basado en cero)
 * @param pageSize   cantidad de elementos por página
 * @param sortBy     campo por el cual se debe ordenar el resultado
 * @param sortDir    dirección del ordenamiento ("asc" o "desc")
 * @param filterType tipo de filtro aplicado a la consulta
 */
public record PaginationRequest(
        Integer pageNumber,
        Integer pageSize,
        String sortBy,
        String sortDir,
        String filterType
) {
}