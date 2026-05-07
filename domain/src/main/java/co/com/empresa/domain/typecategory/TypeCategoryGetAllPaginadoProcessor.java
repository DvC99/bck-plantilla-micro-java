package co.com.empresa.domain.typecategory;

import co.com.empresa.commons.dto.request.PaginationRequest;
import co.com.empresa.commons.exception.DomainException;
import co.com.empresa.commons.services.pageable.IPageableResult;
import co.com.empresa.commons.cqrs.PaginatedQueryAbstract;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * Procesador de dominio para la obtención paginada de categorías (Combo Grande).
 * <p>
 * Coordina la extracción de datos paginados desde el servicio de dominio y define
 * la función de mapeo para el resultado final.
 */
@Service
public class TypeCategoryGetAllPaginadoProcessor extends PaginatedQueryAbstract<GetAllTypeCategoriesPaginadoQuery, TypeCategory, TypeCategory> {

    private final ITypeCategoryService iTypeCategoryService;

    /**
     * Constructor para {@code TypeCategoryGetAllPaginadoProcessor}.
     *
     * @param iTypeCategoryService servicio de dominio para la gestión de categorías
     */
    public TypeCategoryGetAllPaginadoProcessor(ITypeCategoryService iTypeCategoryService) {
        this.iTypeCategoryService = iTypeCategoryService;
    }

    /**
     * Pre-procesamiento de la consulta paginada.
     *
     * @param context consulta de obtención paginada de combo
     * @return la consulta validada
     * @throws DomainException si ocurre un error de negocio
     */
    @Override
    protected GetAllTypeCategoriesPaginadoQuery preProcess(GetAllTypeCategoriesPaginadoQuery context) throws DomainException {
        return context;
    }

    /**
     * Recupera los datos paginados desde la fuente de datos.
     *
     * @param context consulta de obtención paginada de combo
     * @return el resultado paginado de {@code TypeCategory}
     */
    @Override
    protected IPageableResult<TypeCategory> fetchPage(GetAllTypeCategoriesPaginadoQuery context) {
        var pc = context.context();
        PaginationRequest pagination = new PaginationRequest(
                pc.getPageNumber(),
                pc.getPageSize(),
                pc.getSortBy(),
                pc.getSortDir(),
                pc.getFilterType()
        );
        return iTypeCategoryService.getComboGrande(pc.getData(), pagination);
    }

    /**
     * Define la función de mapeo del modelo al DTO.
     * En este caso, el modelo se retorna tal cual.
     *
     * @return la función de mapeo de identidad
     */
    @Override
    public Function<TypeCategory, TypeCategory> getMapper() {
        return category -> category;
    }
}
