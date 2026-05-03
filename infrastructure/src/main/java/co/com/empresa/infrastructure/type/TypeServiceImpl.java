package co.com.empresa.infrastructure.type;import co.com.empresa.commons.repository.IRepository;import co.com.empresa.commons.services.impl.GenericServiceImpl;import co.com.empresa.domain.type.ITypeRepository;import co.com.empresa.domain.type.ITypeService;import co.com.empresa.domain.type.Type;import co.com.empresa.domain.type.TypeDomainService;import org.springframework.stereotype.Service;/**
 * Infrastructure implementation of the {@link ITypeService}.
 * <p>
 * This service handles the persistence and business logic for {@link Type} entities,
 * extending {@link GenericServiceImpl} to provide standard CRUD operations.
 */
@Service
public class TypeServiceImpl extends GenericServiceImpl<Type, Long> implements ITypeService {
    private final ITypeRepository typeRepository;    private final TypeDomainService typeDomainService;    public TypeServiceImpl(ITypeRepository typeRepository,                           TypeDomainService typeDomainService) {        this.typeRepository = typeRepository;        this.typeDomainService = typeDomainService;    }        @Override
    protected IRepository<Type, Long> getRepository() {
        return typeRepository;
    }

    @Override
    protected Long getModelKey(Type model) {
        return model == null ? null : model.getId();
    }

    @Override
    protected Type getEmptyModel() {
        return Type.builder().build();
    }

    /**
     * Validates that the type is unique within its category.
     *
     * @param type the type to validate
     * @throws co.com.empresa.commons.exception.DomainException if the type is not unique
     */
    @Override
    public void validateUniqueness(Type type) {
        typeDomainService.validateUniqueness(type);
    }

    /**
     * Validates that the specified type category exists.
     *
     * @param categoryId the identifier of the category to validate
     * @throws co.com.empresa.commons.exception.DomainException if the category does not exist
     */
    @Override
    public void validateCategoryExists(Long categoryId) {
        typeDomainService.validateCategoryExists(categoryId);
    }
}