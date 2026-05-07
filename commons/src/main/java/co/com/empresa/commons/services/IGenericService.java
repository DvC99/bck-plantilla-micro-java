package co.com.empresa.commons.services;


import co.com.empresa.commons.dto.request.PaginationRequest;

import co.com.empresa.commons.services.pageable.IPageableResult;


import java.util.List;


/**
 * Interfaz genérica que define operaciones CRUD básicas y métodos de consulta.
 *
 * @param <M> el tipo del modelo de dominio
 * @param <K> el tipo de la clave primaria
 */
public interface IGenericService<M, K> {
    /**
     * Obtiene una lista paginada de elementos con opciones de ordenamiento y filtrado.
     *
     * @param model      modelo utilizado como criterio de filtro
     * @param pagination objeto con parámetros de paginación y ordenamiento
     * @return resultado paginado de elementos implementando {@code IPageableResult}
     */
    IPageableResult<M> getComboGrande(M model, PaginationRequest pagination);


    /**
     * Obtiene una lista simple de elementos filtrados.
     *
     * @param model modelo utilizado como criterio de filtro
     * @return lista de elementos encontrados
     */
    List<M> getComboSencillo(M model);


    /**
     * Obtiene un elemento por su identificador.
     *
     * @param id identificador del elemento
     * @return el elemento encontrado
     */
    M getElement(K id);


    /**
     * Guarda un nuevo elemento en el sistema.
     *
     * @param model elemento a guardar
     * @return el elemento guardado
     */
    M save(M model);


    /**
     * Guarda múltiples elementos en el sistema.
     *
     * @param models colección de elementos a guardar
     * @return las entidades guardadas
     */
    Iterable<M> saveAll(Iterable<M> models);


    /**
     * Actualiza un elemento existente en el sistema.
     *
     * @param model elemento a actualizar
     * @return el elemento actualizado
     */
    M update(M model);


    /**
     * Actualiza múltiples elementos existentes en el sistema.
     *
     * @param models colección de elementos a actualizar
     * @return las entidades actualizadas
     */
    Iterable<M> updateAll(Iterable<M> models);


    /**
     * Elimina un elemento del sistema.
     *
     * @param model elemento a eliminar
     * @return el elemento eliminado
     */
    M delete(M model);


    /**
     * Verifica si existe un elemento por su identificador.
     *
     * @param id identificador a verificar
     * @return {@code true} si existe, {@code false} en caso contrario
     */
    boolean existById(K id);


    /**
     * Obtiene el siguiente identificador disponible para la entidad.
     *
     * @return el siguiente identificador
     */
    K getNextId();

}
