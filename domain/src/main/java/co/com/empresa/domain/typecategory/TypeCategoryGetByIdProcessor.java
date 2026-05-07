package co.com.empresa.domain.typecategory;

import co.com.empresa.commons.exception.DomainException;
import co.com.empresa.commons.cqrs.QueryAbstract;
import org.springframework.stereotype.Service;

/**
 * Procesador de dominio para la obtención de una categoría de tipos mediante su identificador.
 * <p>
 * Coordina la búsqueda en el repositorio y gestiona el error en caso de que la categoría no exista.
 */
@Service
public class TypeCategoryGetByIdProcessor extends QueryAbstract<GetTypeCategoryByIdQuery, TypeCategory> {

    private final ITypeCategoryRepository categoryRepository;

    /**
     * Constructor para {@code TypeCategoryGetByIdProcessor}.
     *
     * @param categoryRepository repositorio de categorías de tipos
     */
    public TypeCategoryGetByIdProcessor(ITypeCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Valida que el identificador de la consulta no sea nulo.
     *
     * @param context consulta de obtención por ID
     * @return la consulta si es válida, {@code null} en caso contrario
     * @throws DomainException si ocurre un error de negocio
     */
    @Override
    protected GetTypeCategoryByIdQuery preProcess(GetTypeCategoryByIdQuery context) throws DomainException {
        if (context == null || context.id() == null) {
            return null;
        }
        return context;
    }

    /**
     * Ejecuta la búsqueda de la categoría en el repositorio.
     *
     * @param context consulta de obtención por ID
     * @return el objeto {@code TypeCategory} encontrado
     * @throws DomainException si no se encuentra ninguna categoría con el ID proporcionado
     */
    @Override
    protected TypeCategory process(GetTypeCategoryByIdQuery context) throws DomainException {
        return categoryRepository.findById(context.id())
                .orElseThrow(() -> new DomainException("TypeCategory not found with id: " + context.id()));
    }
}
