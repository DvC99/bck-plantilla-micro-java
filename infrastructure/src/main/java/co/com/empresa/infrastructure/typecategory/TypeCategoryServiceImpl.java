package co.com.empresa.infrastructure.typecategory;import co.com.empresa.commons.services.impl.GenericServiceImpl;import co.com.empresa.domain.typecategory.ITypeCategoryRepository;import co.com.empresa.domain.typecategory.ITypeCategoryService;import co.com.empresa.domain.typecategory.TypeCategory;import co.com.empresa.domain.typecategory.TypeCategoryDomainService;import org.springframework.stereotype.Service;/**
 * Infrastructure implementation of the {@link ITypeCategoryService}.
 * <p>
 * This service handles the persistence and business logic for {@link TypeCategory} entities,
 * extending {@link GenericServiceImpl} to provide standard CRUD operations.
 */
@Service
public class TypeCategoryServiceImpl extends GenericServiceImpl<TypeCategory, Long> implements ITypeCategoryService {
    private final ITypeCategoryRepository categoryRepository;    private final TypeCategoryDomainService typeCategoryDomainService;    public TypeCategoryServiceImpl(ITypeCategoryRepository categoryRepository,                                   TypeCategoryDomainService typeCategoryDomainService) {        this.categoryRepository = categoryRepository;        this.typeCategoryDomainService = typeCategoryDomainService;    }        @Override
    protected ITypeCategoryRepository getRepository() {
        return this.categoryRepository;
    }

    @Override
    protected Long getModelKey(TypeCategory model) {
        return model == null ? null : model.getId();
    }

    @Override
    protected TypeCategory getEmptyModel() {
        return TypeCategory.builder().build();
    }

    /**
     * Validates that the type category is unique.
     *
     * @param category the type category to validate
     * @throws co.com.empresa.commons.exception.DomainException if the category is not unique
     */
    @Override
    public void validateUniqueness(TypeCategory category) {
        typeCategoryDomainService.validateUniqueness(category);
    }

    /**
     * Validates that the type category has no dependencies (e.g., associated types) before deletion.
     *
     * @param category the type category to validate
     * @throws co.com.empresa.commons.exception.DomainException if dependencies are found
     */
    @Override
    public void validateNoDependencies(TypeCategory category) {
        typeCategoryDomainService.validateNoDependencies(category);
    }
}