package co.com.empresa.domain.typecategory;

import co.com.empresa.commons.exception.DomainException;
import co.com.empresa.domain.common.QueryAbstract;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Procesador de dominio para la obtención de una lista simplificada de categorías (Combo).
 * <p>
 * Utiliza el servicio de dominio para obtener los datos basados en el contexto de filtro.
 */
@Service
public class TypeCategoryGetAllProcessor extends QueryAbstract<GetAllTypeCategoriesQuery, List<TypeCategory>> {

    private final ITypeCategoryService iTypeCategoryService;

    /**
     * Constructor para {@code TypeCategoryGetAllProcessor}.
     *
     * @param iTypeCategoryService servicio de dominio para la gestión de categorías
     */
    public TypeCategoryGetAllProcessor(ITypeCategoryService iTypeCategoryService) {
        this.iTypeCategoryService = iTypeCategoryService;
    }

    /**
     * Pre-procesamiento de la consulta de combo.
     *
     * @param context consulta de obtención de combo
     * @return la consulta validada
     * @throws DomainException si ocurre un error de negocio
     */
    @Override
    protected GetAllTypeCategoriesQuery preProcess(GetAllTypeCategoriesQuery context) throws DomainException {
        return context;
    }

    /**
     * Ejecuta la obtención de la lista de categorías simplificada.
     *
     * @param context consulta de obtención de combo
     * @return una lista de {@code TypeCategory} con la información solicitada
     * @throws DomainException si ocurre un error de negocio
     */
    @Override
    protected List<TypeCategory> process(GetAllTypeCategoriesQuery context) throws DomainException {
        return iTypeCategoryService.getComboSencillo(context.context());
    }
}
