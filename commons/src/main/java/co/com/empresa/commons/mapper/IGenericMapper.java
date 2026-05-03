package co.com.empresa.commons.mapper;


import java.util.List;


/**
 * Interfaz genérica para el mapeo entre modelos de dominio y entidades de persistencia.
 *
 * @param <M> el tipo del modelo de dominio
 * @param <E> el tipo de la entidad de persistencia
 */
public interface IGenericMapper<M, E> {

    /**
     * Convierte un modelo de dominio en una entidad de persistencia.
     *
     * @param model el modelo a convertir
     * @return la entidad resultante
     */
    E modelToEntity(M model);

    /**
     * Convierte una entidad de persistencia en un modelo de dominio.
     *
     * @param entity la entidad a convertir
     * @return el modelo resultante
     */
    M entityToModel(E entity);


    /**
     * Convierte una lista de modelos en una lista de entidades.
     *
     * @param models lista de modelos a convertir
     * @return lista de entidades resultantes
     */
    default List<E> modelToEntityList(List<M> models) {

        return models.stream().map(this::modelToEntity).toList();

    }


    /**
     * Convierte una lista de entidades en una lista de modelos.
     *
     * @param entities lista de entidades a convertir
     * @return lista de modelos resultantes
     */
    default List<M> entityToModelList(List<E> entities) {

        return entities.stream().map(this::entityToModel).toList();

    }


    /**
     * Método utilitario para convertir una lista de entidades en modelos.
     *
     * @param entities lista de entidades a convertir
     * @return lista de modelos resultantes
     */
    default List<M> toModelList(List<E> entities) {

        return entityToModelList(entities);

    }


    /**
     * Método utilitario para convertir una lista de modelos en entidades.
     *
     * @param models lista de modelos a convertir
     * @return lista de entidades resultantes
     */
    default List<E> toEntityList(List<M> models) {

        return modelToEntityList(models);

    }

}