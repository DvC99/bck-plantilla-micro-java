package co.com.empresa.application.type;


import co.com.empresa.commons.dto.request.PaginationRequest;

import co.com.empresa.domain.type.*;

import org.springframework.data.domain.Page;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;


import java.util.List;


/**
 * Caso de uso para la orquestación de {@code Type}.
 */
@Service
public class TypeUseCase {

    private final TypeCreateProcessor createProcessor;
    private final TypeUpdateProcessor updateProcessor;
    private final TypeDeleteProcessor deleteProcessor;
    private final TypeGetByIdProcessor getByIdProcessor;
    private final TypeGetComboProcessor getComboProcessor;
    private final TypeGetComboPaginadoProcessor getComboPaginadoProcessor;
    private final TypeApplicationMapper mapper;


    /**
     * Constructor para {@code TypeUseCase}.
     *
     * @param createProcessor           procesador para la creación de tipos
     * @param updateProcessor           procesador para la actualización de tipos
     * @param deleteProcessor           procesador para la eliminación de tipos
     * @param getByIdProcessor          procesador para la obtención de tipos por ID
     * @param getComboProcessor         procesador para la obtención de combos sencillos
     * @param getComboPaginadoProcessor procesador para la obtención de combos paginados
     * @param mapper                    mapeador de la capa de aplicación
     */
    public TypeUseCase(TypeCreateProcessor createProcessor,
                       TypeUpdateProcessor updateProcessor,
                       TypeDeleteProcessor deleteProcessor,
                       TypeGetByIdProcessor getByIdProcessor,
                       TypeGetComboProcessor getComboProcessor,
                       TypeGetComboPaginadoProcessor getComboPaginadoProcessor,
                       TypeApplicationMapper mapper) {
        this.createProcessor = createProcessor;
        this.updateProcessor = updateProcessor;
        this.deleteProcessor = deleteProcessor;
        this.getByIdProcessor = getByIdProcessor;
        this.getComboProcessor = getComboProcessor;
        this.getComboPaginadoProcessor = getComboPaginadoProcessor;
        this.mapper = mapper;
    }


    /**
     * Crea un nuevo tipo en el sistema.
     *
     * @param request DTO con los datos del tipo a crear
     * @return DTO de respuesta con el tipo creado
     */
    @Transactional
    public TypeResponseDto create(TypeRequestDto request) {
        Type type = mapper.fromCreateDto(request);
        Type saved = createProcessor.execute(new TypeCreateCommand(type));
        return mapper.toResponseDto(saved);
    }


    /**
     * Actualiza un tipo existente en el sistema.
     *
     * @param request DTO con los datos del tipo a actualizar
     * @return DTO de respuesta con el tipo actualizado
     */
    @Transactional
    public TypeResponseDto update(TypeRequestDto request) {
        Type type = mapper.fromUpdateDto(request);
        Type updated = updateProcessor.execute(new TypeUpdateCommand(type));
        return mapper.toResponseDto(updated);
    }


    /**
     * Elimina un tipo del sistema.
     *
     * @param id identificador del tipo a eliminar
     * @return DTO de respuesta con la información del tipo eliminado
     */
    @Transactional
    public TypeResponseDto delete(Long id) {
        Type type = Type.builder().id(id).build();
        Type deleted = deleteProcessor.execute(new TypeDeleteCommand(type));
        return mapper.toResponseDto(deleted);
    }


    /**
     * Obtiene un tipo por su identificador.
     *
     * @param id identificador del tipo
     * @return DTO de respuesta con la información del tipo
     */
    public TypeResponseDto getById(Long id) {
        Type result = getByIdProcessor.execute(new GetTypeByIdQuery(id));
        return mapper.toResponseDto(result);
    }


    /**
     * Obtiene una lista de tipos filtrados (Combo Sencillo).
     *
     * @param filterDto DTO con los criterios de filtrado
     * @return lista de DTOs de respuesta
     */
    public List<TypeResponseDto> getCombo(TypeFilterDto filterDto) {
        Type filter = mapper.fromFilterDto(filterDto);
        List<Type> result = getComboProcessor.execute(new GetTypesByTypeCategoryQuery(filter));
        return mapper.toResponseDtoList(result);
    }


    /**
     * Obtiene una lista paginada de tipos filtrados (Combo Grande).
     *
     * @param filterDto  DTO con los criterios de filtrado
     * @param pagination parámetros de paginación y ordenamiento
     * @return página de DTOs de respuesta
     */
    public Page<TypeResponseDto> getComboPaginado(TypeFilterDto filterDto, PaginationRequest pagination) {
        Type filter = mapper.fromFilterDto(filterDto);

        co.com.empresa.commons.dto.pageable.PageContext<Type> pageContext =
                co.com.empresa.commons.dto.pageable.PageContext.<Type>builder()
                        .data(filter)
                        .pageNumber(pagination.pageNumber())
                        .pageSize(pagination.pageSize())
                        .sortBy(pagination.sortBy())
                        .sortDir(pagination.sortDir())
                        .filterType(pagination.filterType())
                        .build();

        Page<Type> result = getComboPaginadoProcessor.execute(new GetTypesByTypeCategoryPaginadoQuery(pageContext));
        return result.map(mapper::toResponseDto);
    }
}




















