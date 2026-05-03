package co.com.empresa.domain.typecategory;

import co.com.empresa.commons.exception.DomainException;
import co.com.empresa.domain.common.CommandProcessAbstract;
import org.springframework.stereotype.Service;

/**
 * Procesador de dominio encargado de la actualización de categorías de tipos.
 * <p>
 * Valida la existencia del registro, la integridad de los datos y la unicidad
 * del código a nivel global antes de actualizar la entidad.
 */
@Service
public class TypeCategoryUpdateProcessor extends CommandProcessAbstract<TypeCategoryUpdateCommand, TypeCategory> {

    private final ITypeCategoryRepository categoryRepository;
    private final ITypeCategoryService iTypeCategoryService;

    /**
     * Constructor para {@code TypeCategoryUpdateProcessor}.
     *
     * @param categoryRepository   repositorio de categorías de tipos
     * @param iTypeCategoryService servicio de dominio para validaciones de categoría
     */
    public TypeCategoryUpdateProcessor(ITypeCategoryRepository categoryRepository, ITypeCategoryService iTypeCategoryService) {
        this.categoryRepository = categoryRepository;
        this.iTypeCategoryService = iTypeCategoryService;
    }

    /**
     * Realiza las validaciones previas a la actualización de la categoría.
     *
     * @param command comando de actualización
     * @return el comando si es válido, {@code null} en caso contrario
     * @throws DomainException si alguna validación de negocio falla
     */
    @Override
    protected TypeCategoryUpdateCommand preProcess(TypeCategoryUpdateCommand command) throws DomainException {
        TypeCategory category = command.context();
        if (category == null || category.getId() == null) {
            return null;
        }
        category.validate();
        iTypeCategoryService.validateUniqueness(category);
        return command;
    }

    /**
     * Ejecuta la actualización de la categoría en la base de datos.
     *
     * @param command comando de actualización
     * @return la categoría actualizada
     * @throws DomainException si ocurre un error durante la actualización
     */
    @Override
    protected TypeCategory process(TypeCategoryUpdateCommand command) throws DomainException {
        return categoryRepository.update(command.context());
    }
}
