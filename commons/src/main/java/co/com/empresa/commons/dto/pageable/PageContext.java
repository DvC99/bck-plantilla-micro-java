package co.com.empresa.commons.dto.pageable;


import lombok.AllArgsConstructor;

import lombok.Builder;

import lombok.Data;

import lombok.NoArgsConstructor;


/**
 * Contexto para el manejo de datos paginados.
 * <p>
 * Esta clase encapsula la información de paginación junto con el criterio de filtro
 * necesario para realizar consultas paginadas en el repositorio.
 *
 * @param <T> el tipo de datos que se están paginando
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PageContext<T> {

    /**
     * Contenido real de los datos o criterio de filtro.
     */
    private T data;

    /**
     * Número de página actual (basado en cero).
     */
    private Integer pageNumber;

    /**
     * Tamaño de la página (número de elementos por página).
     */
    private Integer pageSize;

    /**
     * Nombre del campo por el cual se debe ordenar.
     */
    private String sortBy;

    /**
     * Dirección del ordenamiento ("asc" para ascendente, "desc" para descendente).
     */
    private String sortDir;

    /**
     * Tipo de filtro aplicado a los datos.
     */
    private String filterType;
}
