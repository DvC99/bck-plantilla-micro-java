package co.com.empresa.domain.common;


import co.com.empresa.commons.services.pageable.IPageableResult;

import co.com.empresa.commons.util.PaginationHelper;

import org.springframework.data.domain.Page;


import java.util.function.Function;


/**
 * Especialización de {@code QueryAbstract} para manejar consultas paginadas.
 * <p>
 * Integra el {@link PaginationHelper} para estandarizar la conversión de
 * resultados de base de datos a páginas de {@code DTO}s.
 *
 * @param <C> el tipo del contexto de la consulta (generalmente incluye filtros y {@code PaginationRequest})
 * @param <M> el tipo del modelo de dominio obtenido de la base de datos
 * @param <R> el tipo del {@code DTO} de respuesta
 */
public abstract class PaginatedQueryAbstract<C, M, R> extends QueryAbstract<C, Page<R>> {


    /**
     * Procesa la consulta paginada.
     *
     * @param context el contexto validado
     * @return una página de {@code DTO}s
     */
    @Override
    protected Page<R> process(C context) {

        // 1. Obtener el resultado paginado crudo del repositorio/servicio

        IPageableResult<M> rawResult = fetchPage(context);


        // 2. Definir el mapeador de Modelo -> DTO

        Function<M, R> mapper = getMapper();


        // 3. Utilizar el PaginationHelper para convertir el resultado a Page<R>

        return PaginationHelper.mapPage(rawResult, mapper);

    }


    /**
     * Método que debe implementar la subclase para obtener los datos de la fuente.
     *
     * @param context contexto validado
     * @return el resultado paginado en términos de modelos de dominio
     */
    protected abstract IPageableResult<M> fetchPage(C context);


    /**
     * Método que debe implementar la subclase para definir cómo mapear el modelo al {@code DTO}.
     *
     * @return la función de mapeo
     */
    protected abstract Function<M, R> getMapper();
}














