package co.com.empresa.commons.services.pageable;


import org.springframework.data.domain.Page;


import java.util.List;


/**
 * Implementación de la interfaz {@code IPageableResult} que envuelve un objeto {@code Page} de Spring Data
 * <p>
 * o puede ser construida directamente con datos de paginación.
 *
 * @param <M> el tipo de los elementos en la página
 */
public class PageableResultImpl<M> implements IPageableResult<M> {

    private final List<M> content;
    private final int pageNumber;
    private final int pageSize;
    private final Long totalElements;


    /**
     * Constructor a partir de un {@code Page} de Spring Data.
     *
     * @param page el objeto {@code Page} de Spring Data
     */
    public PageableResultImpl(Page<M> page) {
        this.content = page.getContent();
        this.pageNumber = page.getNumber();
        this.pageSize = page.getSize();
        this.totalElements = page.getTotalElements();
    }


    /**
     * Constructor con valores directos.
     * <p>
     * Útil para crear resultados paginados sin utilizar un {@code Page} de Spring Data.
     *
     * @param content       la lista de elementos
     * @param pageNumber    el número de página actual (basado en cero)
     * @param pageSize      el tamaño de la página
     * @param totalElements el número total de elementos
     */
    public PageableResultImpl(List<M> content, int pageNumber, int pageSize, Long totalElements) {
        this.content = content;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
    }


    @Override
    /** {@inheritDoc} */
    public List<M> getContent() {
        return content;
    }


    @Override
    /** {@inheritDoc} */
    public int getPageNumber() {
        return pageNumber;
    }


    @Override
    /** {@inheritDoc} */
    public int getPageSize() {
        return pageSize;
    }


    @Override
    /** {@inheritDoc} */
    public Long getTotalElements() {
        return totalElements;
    }


    @Override
    /** {@inheritDoc} */
    public int getTotalPages() {
        if (pageSize <= 0 || totalElements == null || totalElements == 0L) {
            return 0;
        }
        return (int) Math.ceil((double) totalElements / pageSize);
    }
}
