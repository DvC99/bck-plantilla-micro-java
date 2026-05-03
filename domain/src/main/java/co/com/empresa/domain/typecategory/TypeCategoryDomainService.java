package co.com.empresa.domain.typecategory;


import co.com.empresa.commons.exception.DomainException;

import co.com.empresa.domain.constants.DomainErrors;

import co.com.empresa.domain.type.ITypeRepository;

import co.com.empresa.domain.type.Type;

import org.springframework.data.domain.Example;

import org.springframework.data.domain.ExampleMatcher;


import java.util.List;


/**
 * Servicio de dominio que encapsula las reglas de negocio para {@link TypeCategory}.
 */
public class TypeCategoryDomainService {

    private final ITypeCategoryRepository categoryRepository;
    private final ITypeRepository typeRepository;


    /**
     * Constructor para {@code TypeCategoryDomainService}.
     *
     * @param categoryRepository repositorio de categorías de tipos
     * @param typeRepository     repositorio de tipos
     */
    public TypeCategoryDomainService(ITypeCategoryRepository categoryRepository, ITypeRepository typeRepository) {
        this.categoryRepository = categoryRepository;
        this.typeRepository = typeRepository;
    }


    /**
     * Valida que el código de la categoría sea único a nivel global.
     *
     * @param category la categoría a validar
     * @throws DomainException si la categoría es nula, no tiene nombre o el código ya existe
     */
    public void validateUniqueness(TypeCategory category) {
        if (category == null) {
            throw new DomainException(DomainErrors.ERROR_TYPE_CATEGORY_EMPTY);
        }
        if (category.getName() == null || category.getName().isBlank()) {
            throw new DomainException(DomainErrors.ERROR_TYPE_CATEGORY_NAME_EMPTY);
        }
        if (category.getCode() == null || category.getCode().isBlank()) {
            return;
        }

        TypeCategory probe = TypeCategory.builder()
                .code(category.getCode())
                .build();

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnoreCase();

        List<TypeCategory> matches = categoryRepository.findAll(Example.of(probe, matcher));
        if (matches == null || matches.isEmpty()) {
            return;
        }

        Long currentId = category.getId();
        boolean duplicate = matches.stream()
                .map(TypeCategory::getId)
                .anyMatch(id -> id != null && !id.equals(currentId));

        if (duplicate) {
            throw new DomainException(DomainErrors.ERROR_TYPE_CATEGORY_CODE_DUPLICATE);
        }
    }


    /**
     * Valida que la categoría no tenga dependencias (tipos asociados).
     *
     * @param category la categoría a validar
     * @throws DomainException si la categoría es nula, no tiene ID o tiene tipos asociados
     */
    public void validateNoDependencies(TypeCategory category) {
        if (category == null || category.getId() == null) {
            throw new DomainException(DomainErrors.ERROR_CONTEXTO_EMPTY);
        }

        List<Type> types = typeRepository.findAll(
                Example.of(Type.builder().typeCategoryId(category.getId()).build())
        );

        if (types != null && !types.isEmpty()) {
            throw new DomainException(DomainErrors.ERROR_TYPE_CATEGORY_HAS_TYPES);
        }
    }
}




















