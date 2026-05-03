package co.com.empresa.domain.type;

import co.com.empresa.commons.dto.request.PaginationRequest;
import co.com.empresa.commons.exception.DomainException;
import co.com.empresa.commons.services.pageable.IPageableResult;
import co.com.empresa.domain.common.PaginatedQueryAbstract;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * Procesador de dominio para la obtención paginada de tipos (Combo Grande).
 * <p>
 * Coordina la extracción de datos paginados desde el servicio de dominio y define
 * la función de mapeo para el resultado final.
 */
@Service
public class TypeGetComboPaginadoProcessor extends PaginatedQueryAbstract<GetTypesByTypeCategoryPaginadoQuery, Type, Type> {

    private final ITypeService iTypeService;

    /**
     * Constructor para {@code TypeGetComboPaginadoProcessor}.
     *
     * @param iTypeService servicio de dominio para la gestión de tipos
     */
    public TypeGetComboPaginadoProcessor(ITypeService iTypeService) {
        this.iTypeService = iTypeService;
    }

    /**
     * Pre-procesamiento de la consulta paginada.
     *
     * @param context consulta de obtención paginada de combo
     * @return la consulta validada
     * @throws DomainException si ocurre un error de negocio
     */
    @Override
    protected GetTypesByTypeCategoryPaginadoQuery preProcess(GetTypesByTypeCategoryPaginadoQuery context) throws DomainException {
        return context;
    }

    /**
     * Recupera los datos paginados desde la fuente de datos.
     *
     * @param context consulta de obtención paginada de combo
     * @return el resultado paginado de {@code Type}
     */
    @Override
    protected IPageableResult<Type> fetchPage(GetTypesByTypeCategoryPaginadoQuery context) {
        var pc = context.context();
        PaginationRequest pagination = new PaginationRequest(
                pc.getPageNumber(),
                pc.getPageSize(),
                pc.getSortBy(),
                pc.getSortDir(),
                pc.getFilterType()
        );
        return iTypeService.getComboGrande(pc.getData(), pagination);
    }

    /**
     * Define la función de mapeo del modelo al DTO.
     * En este caso, el modelo se retorna tal cual.
     *
     * @return la función de mapeo de identidad
     */
    @Override
    public Function<Type, Type> getMapper() {
        return type -> type;
    }
}
