package co.com.empresa.commons.util;


import co.com.empresa.commons.services.pageable.IPageableResult;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageImpl;

import org.springframework.data.domain.PageRequest;


import java.util.List;

import java.util.function.Function;


/**
 * Clase utilitaria para manejar la conversión de resultados paginados.
 */
public class PaginationHelper {


    private PaginationHelper() {

    }


    /**
     * Convierte un {@code IPageableResult} de dominio en un objeto {@code Page} de Spring Data con elementos mapeados.
     *
     * @param <M>    tipo del modelo de dominio
     * @param <R>    tipo del DTO de respuesta
     * @param result el resultado paginado proveniente del servicio
     * @param mapper función para convertir el modelo al DTO
     * @return una instancia de {@code Page} con los elementos mapeados y metadatos preservados
     */
    public static <M, R> Page<R> mapPage(IPageableResult<M> result, Function<M, R> mapper) {
        if (result == null) {
            return Page.empty();
        }

        List<R> mappedContent = result.getContent().stream()
                .map(mapper)
                .toList();

        PageRequest pageRequest = PageRequest.of(result.getPageNumber(), result.getPageSize());
        return new PageImpl<>(mappedContent, pageRequest, result.getTotalElements());
    }


    /**
     * Sobrecarga para manejar el mapeo de una lista completa.
     *
     * @param <M>        tipo del modelo de dominio
     * @param <R>        tipo del DTO de respuesta
     * @param result     el resultado paginado proveniente del servicio
     * @param listMapper función para convertir la lista de modelos a una lista de DTOs
     * @return una instancia de {@code Page} con los elementos mapeados y metadatos preservados
     */
    public static <M, R> Page<R> mapPageList(IPageableResult<M> result, Function<List<M>, List<R>> listMapper) {
        if (result == null) {
            return Page.empty();
        }

        List<R> mappedContent = listMapper.apply(result.getContent());
        PageRequest pageRequest = PageRequest.of(result.getPageNumber(), result.getPageSize());
        return new PageImpl<>(mappedContent, pageRequest, result.getTotalElements());
    }
}

