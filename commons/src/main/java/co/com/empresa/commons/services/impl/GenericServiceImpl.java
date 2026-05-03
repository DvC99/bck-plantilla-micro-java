package co.com.empresa.commons.services.impl;

import co.com.empresa.commons.dto.request.PaginationRequest;
import co.com.empresa.commons.repository.IRepository;
import co.com.empresa.commons.services.IGenericService;
import co.com.empresa.commons.services.pageable.IPageableResult;
import co.com.empresa.commons.services.pageable.PageableResultImpl;

import java.util.List;

/**
 * Implementación base abstracta de {@code IGenericService}.
 * <p>
 * Proporciona la lógica común para el manejo de entidades utilizando un repositorio genérico.
 *
 * @param <M> el tipo del modelo de dominio
 * @param <K> el tipo de la clave primaria
 */
public abstract class GenericServiceImpl<M, K> implements IGenericService<M, K> {

    /**
     * Obtiene la instancia del repositorio asociado a este servicio.
     *
     * @return el repositorio genérico
     */
    protected abstract IRepository<M, K> getRepository();


    @Override
    /** {@inheritDoc} */
    public IPageableResult<M> getComboGrande(M model, PaginationRequest pagination) {
        return new PageableResultImpl<>(List.of(), 0, 1, 0L);
    }


    @Override
    /** {@inheritDoc} */
    public List<M> getComboSencillo(M model) {
        return List.of();
    }


    @Override
    /** {@inheritDoc} */
    public M getElement(K id) {
        return getRepository().findById(id).orElse(null);
    }


    @Override
    /** {@inheritDoc} */
    public M save(M model) {
        return getRepository().save(model);
    }


    @Override
    /** {@inheritDoc} */
    public Iterable<M> saveAll(Iterable<M> models) {
        return getRepository().saveAll(models);
    }


    @Override
    /** {@inheritDoc} */
    public M update(M model) {
        return getRepository().update(model);
    }


    @Override
    /** {@inheritDoc} */
    public Iterable<M> updateAll(Iterable<M> models) {
        return getRepository().saveAll(models);
    }


    @Override
    /** {@inheritDoc} */
    public M delete(M model) {
        return model;
    }


    @Override
    /** {@inheritDoc} */
    public boolean existById(K id) {
        return getRepository().existsById(id);
    }


    @SuppressWarnings("unchecked")
    @Override
    /** {@inheritDoc} */
    public K getNextId() {
        return (K) Long.valueOf(0);
    }


    /**
     * Extrae la clave primaria de un modelo.
     *
     * @param model el modelo del cual extraer la clave
     * @return la clave del modelo, o {@code null} si no es posible extraerla
     */
    protected K getModelKey(M model) {
        return null;
    }


    /**
     * Crea una instancia vacía del modelo.
     *
     * @return una instancia vacía del modelo, o {@code null}
     */
    protected M getEmptyModel() {
        return null;
    }

}