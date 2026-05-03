package co.com.empresa.domain.typecategory;

import co.com.empresa.commons.exception.DomainException;
import co.com.empresa.domain.common.CommandProcessAbstract;
import org.springframework.stereotype.Service;

/**
 * Procesador de dominio encargado de la creación de categorías de tipos.
 * <p>
 * Valida la integridad del objeto y la unicidad del código a nivel global
 * antes de persistir la entidad.
 */
@Service
public class TypeCategoryCreateProcessor extends CommandProcessAbstract<TypeCategoryCreateCommand, TypeCategory> {

    private final ITypeCategoryRepository categoryRepository;
    private final ITypeCategoryService iTypeCategoryService;

    /**
     * Constructor para {@code TypeCategoryCreateProcessor}.
     *
     * @param categoryRepository   repositorio de categorías de tipos
     * @param iTypeCategoryService servicio de dominio para validaciones de categoría
     */
    public TypeCategoryCreateProcessor(ITypeCategoryRepository categoryRepository, ITypeCategoryService iTypeCategoryService) {
        this.categoryRepository = categoryRepository;
        this.iTypeCategoryService = iTypeCategoryService;
    }

    /**
     * Realiza las validaciones previas a la creación de la categoría.
     *
     * @param command comando de creación
     * @return el comando si es válido, {@code null} en caso contrario
     * @throws DomainException si alguna validación de negocio falla
     */
    @Override
    protected TypeCategoryCreateCommand preProcess(TypeCategoryCreateCommand command) throws DomainException {
        TypeCategory category = command.context();
        if (category == null) {
            return null;
        }
        category.validate();
        iTypeCategoryService.validateUniqueness(category);
        return command;
    }

    /**
     * Ejecuta la persistencia de la nueva categoría.
     *
     * @param command comando de creación
     * @return la categoría persistida
     * @throws DomainException si ocurre un error durante la persistencia
     */
    @Override
    protected TypeCategory process(TypeCategoryCreateCommand command) throws DomainException {
        return categoryRepository.save(command.context());
    }
}
