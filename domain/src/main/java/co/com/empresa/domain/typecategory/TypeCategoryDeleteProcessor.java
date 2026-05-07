package co.com.empresa.domain.typecategory;

import co.com.empresa.commons.exception.DomainException;
import co.com.empresa.commons.cqrs.CommandProcessAbstract;
import org.springframework.stereotype.Service;

/**
 * Procesador de dominio encargado de la eliminación de categorías de tipos.
 * <p>
 * Verifica que el identificador sea válido y que la categoría no tenga dependencias
 * (tipos asociados) antes de proceder con la eliminación.
 */
@Service
public class TypeCategoryDeleteProcessor extends CommandProcessAbstract<TypeCategoryDeleteCommand, TypeCategory> {

    private final ITypeCategoryRepository categoryRepository;
    private final ITypeCategoryService iTypeCategoryService;

    /**
     * Constructor para {@code TypeCategoryDeleteProcessor}.
     *
     * @param categoryRepository   repositorio de categorías de tipos
     * @param iTypeCategoryService servicio de dominio para validaciones de categoría
     */
    public TypeCategoryDeleteProcessor(ITypeCategoryRepository categoryRepository, ITypeCategoryService iTypeCategoryService) {
        this.categoryRepository = categoryRepository;
        this.iTypeCategoryService = iTypeCategoryService;
    }

    /**
     * Realiza las validaciones previas a la eliminación de la categoría.
     *
     * @param command comando de eliminación
     * @return el comando si es válido, {@code null} en caso contrario
     * @throws DomainException si la categoría tiene dependencias o el ID es nulo
     */
    @Override
    protected TypeCategoryDeleteCommand preProcess(TypeCategoryDeleteCommand command) throws DomainException {
        TypeCategory category = command.context();
        if (category == null || category.getId() == null) {
            return null;
        }
        iTypeCategoryService.validateNoDependencies(category);
        return command;
    }

    /**
     * Ejecuta la eliminación de la categoría.
     *
     * @param command comando de eliminación
     * @return el objeto {@code TypeCategory} que fue eliminado
     * @throws DomainException si ocurre un error durante la eliminación
     */
    @Override
    protected TypeCategory process(TypeCategoryDeleteCommand command) throws DomainException {
        categoryRepository.delete(command.context().getId());
        return command.context();
    }
}
