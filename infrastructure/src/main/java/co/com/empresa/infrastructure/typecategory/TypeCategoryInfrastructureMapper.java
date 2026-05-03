package co.com.empresa.infrastructure.typecategory;import co.com.empresa.commons.mapper.IGenericMapper;import co.com.empresa.domain.typecategory.TypeCategory;import co.com.empresa.infrastructure.entities.typeCategory.TypeCategoryEntity;import org.mapstruct.Mapper;import org.mapstruct.Mapping;/**
 * Mapper for converting between {@link TypeCategory} domain models and {@link TypeCategoryEntity} JPA entities.
 */
@Mapper(componentModel = "spring")
public interface TypeCategoryInfrastructureMapper extends IGenericMapper<TypeCategory, TypeCategoryEntity> {

    /**
     * Converts a domain model to a JPA entity.
     * <p>
     * The {@code types} list is ignored during this conversion to avoid circular dependencies
     * or unnecessary updates to the associated types.
     *
     * @param model the domain model to convert
     * @return the converted JPA entity
     */
    @Override
    @Mapping(target = "types", ignore = true)
    TypeCategoryEntity modelToEntity(TypeCategory model);
}
