package co.com.empresa.application.typecategory;

import co.com.empresa.application.common.BaseUseCase;
import co.com.empresa.commons.dto.request.PaginationRequest;
import co.com.empresa.domain.typecategory.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Caso de uso para la orquestación de {@code TypeCategory}.
 */
@Service
public class TypeCategoryUseCase extends BaseUseCase {

    private final TypeCategoryCreateProcessor createProcessor;
    private final TypeCategoryUpdateProcessor updateProcessor;
    private final TypeCategoryDeleteProcessor deleteProcessor;
    private final TypeCategoryGetByIdProcessor getByIdProcessor;
    private final TypeCategoryGetAllProcessor getAllProcessor;
    private final TypeCategoryGetAllPaginadoProcessor getAllPaginadoProcessor;
    private final TypeCategoryApplicationMapper mapper;


    /**
     * Constructor para {@code TypeCategoryUseCase}.
     *
     * @param createProcessor         procesador para la creación de categorías
     * @param updateProcessor         procesador para la actualización de categorías
     * @param deleteProcessor         procesador para la eliminación de categorías
     * @param getByIdProcessor        procesador para la obtención de categorías por ID
     * @param getAllProcessor         procesador para la obtención de combos sencillos
     * @param getAllPaginadoProcessor procesador para la obtención de combos paginados
     * @param mapper                  mapeador de la capa de aplicación
     */
    public TypeCategoryUseCase(TypeCategoryCreateProcessor createProcessor,
                               TypeCategoryUpdateProcessor updateProcessor,
                               TypeCategoryDeleteProcessor deleteProcessor,
                               TypeCategoryGetByIdProcessor getByIdProcessor,
                               TypeCategoryGetAllProcessor getAllProcessor,
                               TypeCategoryGetAllPaginadoProcessor getAllPaginadoProcessor,
                               TypeCategoryApplicationMapper mapper) {
        this.createProcessor = createProcessor;
        this.updateProcessor = updateProcessor;
        this.deleteProcessor = deleteProcessor;
        this.getByIdProcessor = getByIdProcessor;
        this.getAllProcessor = getAllProcessor;
        this.getAllPaginadoProcessor = getAllPaginadoProcessor;
        this.mapper = mapper;
    }


    /**
     * Crea una nueva categoría en el sistema.
     *
     * @param request DTO con los datos de la categoría a crear
     * @return DTO de respuesta con la categoría creada
     */
    @Transactional
    public TypeCategoryResponseDto create(TypeCategoryRequestDto request) {
        TypeCategory category = mapper.fromCreateDto(request);
        TypeCategory saved = execute(createProcessor, new TypeCategoryCreateCommand(category));
        return mapper.toResponseDto(saved);
    }


    /**
     * Actualiza una categoría existente en el sistema.
     *
     * @param request DTO con los datos de la categoría a actualizar
     * @return DTO de respuesta con la categoría actualizada
     */
    @Transactional
    public TypeCategoryResponseDto update(TypeCategoryRequestDto request) {
        TypeCategory category = mapper.fromUpdateDto(request);
        TypeCategory updated = execute(updateProcessor, new TypeCategoryUpdateCommand(category));
        return mapper.toResponseDto(updated);
    }


    /**
     * Elimina una categoría del sistema.
     *
     * @param id identificador de la categoría a eliminar
     * @return DTO de respuesta con la información de la categoría eliminada
     */
    @Transactional
    public TypeCategoryResponseDto delete(Long id) {
        TypeCategory category = TypeCategory.builder().id(id).build();
        TypeCategory deleted = execute(deleteProcessor, new TypeCategoryDeleteCommand(category));
        return mapper.toResponseDto(deleted);
    }


    /**
     * Obtiene una categoría por su identificador.
     *
     * @param id identificador de la categoría
     * @return DTO de respuesta con la información de la categoría
     */
    public TypeCategoryResponseDto getById(Long id) {
        TypeCategory result = execute(getByIdProcessor, new GetTypeCategoryByIdQuery(id));
        return mapper.toResponseDto(result);
    }


    /**
     * Obtiene una lista de categorías filtradas (Combo Sencillo).
     *
     * @param filterDto DTO con los criterios de filtrado
     * @return lista de DTOs de respuesta
     */
    public List<TypeCategoryResponseDto> getAll(TypeCategoryFilterDto filterDto) {
        TypeCategory filter = mapper.fromFilterDto(filterDto);
        List<TypeCategory> result = execute(getAllProcessor, new GetAllTypeCategoriesQuery(filter));
        return mapper.toResponseDtoList(result);
    }


    /**
     * Obtiene una lista paginada de categorías filtradas (Combo Grande).
     *
     * @param filterDto  DTO con los criterios de filtrado
     * @param pagination parámetros de paginación y ordenamiento
     * @return página de DTOs de respuesta
     */
    public Page<TypeCategoryResponseDto> getAllPaginado(TypeCategoryFilterDto filterDto, PaginationRequest pagination) {
        TypeCategory data = mapper.fromFilterDto(filterDto);

        co.com.empresa.commons.dto.pageable.PageContext<TypeCategory> pageContext = pageContext(data, pagination);

        Page<TypeCategory> result = execute(getAllPaginadoProcessor, new GetAllTypeCategoriesPaginadoQuery(pageContext));
        assert result != null;
        return result.map(mapper::toResponseDto);
    }
}
