package co.com.empresa.domain.common;


import co.com.empresa.commons.exception.DomainException;

import co.com.empresa.commons.services.IGenericService;

import co.com.empresa.domain.constants.DomainErrors;

import lombok.extern.slf4j.Slf4j;


import java.util.List;


/**
 * Clase abstracta para el manejo de consultas de combos, extendiendo {@code QueryAbstract}.
 * <p>
 * Proporciona una implementación genérica para operaciones relacionadas con combos.
 *
 * @param <T> el tipo del contexto y los elementos del resultado
 * @param <K> el tipo de la llave utilizada en el servicio genérico
 */
@Slf4j
public abstract class ComboQueryAbstract<T, K> extends QueryAbstract<T, List<T>> {

    private final IGenericService<T, K> service;


    /**
     * Constructor para {@code ComboQueryAbstract} con el servicio genérico especificado.
     *
     * @param service el servicio genérico utilizado para las operaciones de combo
     */
    protected ComboQueryAbstract(IGenericService<T, K> service) {

        this.service = service;

    }


    /**
     * Realiza verificaciones de pre-procesamiento sobre el contexto dado.
     *
     * @param context el contexto a pre-procesar
     * @return el contexto validado, o {@code null} si el contexto es nulo
     */
    @Override
    protected T preProcess(T context) throws DomainException {

        if (context == null) {

            log.error(DomainErrors.ERROR_CONTEXTO_EMPTY);

            throw new DomainException(DomainErrors.ERROR_CONTEXTO_EMPTY);

        }

        return context;

    }


    /**
     * Procesa la consulta de combo utilizando el contexto dado.
     *
     * @param context el contexto para procesar la consulta
     * @return una lista de tipo {@code T} con los resultados de la consulta de combo
     */
    @Override
    protected List<T> process(T context) {

        return this.service.getComboSencillo(context);

    }

}

