package co.com.empresa.infrastructure.type;import co.com.empresa.commons.mapper.IGenericMapper;import co.com.empresa.domain.type.Type;import co.com.empresa.infrastructure.entities.type.TypeEntity;import org.mapstruct.Mapper;import org.mapstruct.Mapping;/**
 * Mapper for converting between {@link Type} domain models and {@link TypeEntity} JPA entities.
 */
@Mapper(componentModel = "spring")
public interface TypeInfrastructureMapper extends IGenericMapper<Type, TypeEntity> {

    /**
     * Converts a domain model to a JPA entity.
     * <p>
     * The {@code typeCategory} field is ignored during this conversion to avoid circular dependencies
     * or unnecessary updates to the category entity.
     *
     * @param model the domain model to convert
     * @return the converted JPA entity
     */
    @Override
    @Mapping(target = "typeCategory", ignore = true)
    TypeEntity modelToEntity(Type model);
}
