package co.com.empresa.application.common;

import co.com.empresa.commons.cqrs.CommandProcessAbstract;
import co.com.empresa.commons.cqrs.QueryAbstract;
import co.com.empresa.commons.dto.pageable.PageContext;
import co.com.empresa.commons.dto.request.PaginationRequest;

/**
 * Base para casos de uso con helpers estandarizados.
 */
public abstract class BaseUseCase {
    protected <C, R> R execute(CommandProcessAbstract<C, R> processor, C command) {
        return processor.execute(command);
    }

    protected <C, R> R execute(QueryAbstract<C, R> processor, C query) {
        return processor.execute(query);
    }

    protected <T> PageContext<T> pageContext(T data, PaginationRequest pagination) {
        return PageContext.<T>builder()
                .data(data)
                .pageNumber(pagination.pageNumber())
                .pageSize(pagination.pageSize())
                .sortBy(pagination.sortBy())
                .sortDir(pagination.sortDir())
                .filterType(pagination.filterType())
                .build();
    }
}
