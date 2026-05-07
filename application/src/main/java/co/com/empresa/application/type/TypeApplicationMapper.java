package co.com.empresa.application.type;


import co.com.empresa.domain.type.Type;

import org.mapstruct.Mapper;

import org.mapstruct.Mapping;


import java.util.List;


@Mapper(componentModel = "spring")
public interface TypeApplicationMapper {


    /**
     * Mapea un DTO de creación al modelo de dominio.
     *
     * @param dto el DTO de solicitud de creación
     * @return el modelo de dominio correspondiente
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    Type fromCreateDto(TypeRequestDto dto);


    /**
     * Mapea un DTO de actualización al modelo de dominio.
     *
     * @param dto el DTO de solicitud de actualización
     * @return el modelo de dominio correspondiente
     */
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    Type fromUpdateDto(TypeRequestDto dto);


    /**
     * Mapea un DTO de filtro al modelo de dominio.
     *
     * @param dto el DTO de filtros
     * @return el modelo de dominio utilizado para la consulta
     */
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    Type fromFilterDto(TypeFilterDto dto);


    /**
     * Convierte un modelo de dominio en un DTO de respuesta.
     *
     * @param model el modelo de dominio
     * @return el DTO de respuesta correspondiente
     */
    TypeResponseDto toResponseDto(Type model);


    /**
     * Convierte una lista de modelos de dominio en una lista de DTOs de respuesta.
     *
     * @param models la lista de modelos
     * @return la lista de DTOs de respuesta
     */
    List<TypeResponseDto> toResponseDtoList(List<Type> models);
}
