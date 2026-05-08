package co.com.empresa.domain.type;

import co.com.empresa.commons.exception.DomainException;
import co.com.empresa.domain.common.DomainService;
import co.com.empresa.domain.constants.DomainErrors;
import co.com.empresa.domain.typecategory.ITypeCategoryRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import java.util.List;

/**
 * Domain service that encapsulates business rules for {@code Type}.
 * <p>
 * Provides cross-cutting validations such as code uniqueness within a category
 * and verification of dependency existence.
 */
@DomainService
public class TypeDomainService {

    private final ITypeRepository typeRepository;

    private final ITypeCategoryRepository categoryRepository;

    /**
     * Constructor for {@code TypeDomainService}.
     *
     * @param typeRepository     type repository
     * @param categoryRepository type category repository
     */
    public TypeDomainService(ITypeRepository typeRepository, ITypeCategoryRepository categoryRepository) {
        this.typeRepository = typeRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Validates that the type code is unique within its associated category.
     *
     * @param type the {@code Type} object to validate
     * @throws DomainException if a duplicate code is detected for the category
     */
    public void validateUniqueness(Type type) {
        if (type == null) {
            throw new DomainException("El tipo no puede ser nulo");
        }

        if (type.getTypeCategoryId() == null) {
            throw new DomainException("El ID de categoría del tipo no puede estar vacío");
        }

        if (type.getCode() == null || type.getCode().isBlank()) {
            return;
        }

        Type probe = Type.builder()
                .typeCategoryId(type.getTypeCategoryId())
                .code(type.getCode())
                .build();

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnoreCase();

        List<Type> matches = typeRepository.findAll(Example.of(probe, matcher));

        if (matches == null || matches.isEmpty()) {
            return;
        }

        Long currentId = type.getId();

        boolean duplicate = matches.stream()
                .map(Type::getId)
                .anyMatch(id -> id != null && !id.equals(currentId));

        if (duplicate) {
            throw new DomainException(DomainErrors.ERROR_TYPE_CODE_DUPLICATE);
        }
    }

    /**
     * Validates that the specified type category exists in the system.
     *
     * @param categoryId identifier of the category to validate
     * @throws DomainException if the category is not found or the ID is null
     */
    public void validateCategoryExists(Long categoryId) {
        if (categoryId == null) {
            throw new DomainException(DomainErrors.ERROR_TYPE_CATEGORY_ID_EMPTY);
        }

        if (!categoryRepository.existsById(categoryId)) {
            throw new DomainException(DomainErrors.ERROR_TYPE_CATEGORY_NOT_FOUND);
        }
    }
}
