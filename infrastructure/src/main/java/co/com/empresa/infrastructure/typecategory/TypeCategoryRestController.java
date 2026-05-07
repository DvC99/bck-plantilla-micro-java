package co.com.empresa.infrastructure.typecategory;

import co.com.empresa.application.typecategory.TypeCategoryFilterDto;
import co.com.empresa.application.typecategory.TypeCategoryRequestDto;
import co.com.empresa.application.typecategory.TypeCategoryResponseDto;
import co.com.empresa.application.typecategory.TypeCategoryUseCase;
import co.com.empresa.commons.dto.request.PaginationRequest;
import co.com.empresa.commons.dto.response.GenericResponse;
import co.com.empresa.commons.helper.ApiResponseBuilder;
import co.com.empresa.commons.services.i18.MessageService;
import co.com.empresa.infrastructure.common.BaseRestController;
import co.com.empresa.infrastructure.constants.RestConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for Type Category operations.
 * <p>
 * This controller provides endpoints for creating, updating, deleting, and retrieving type categories,
 * delegating the business logic to the {@link TypeCategoryUseCase}.
 */
@RestController
@RequestMapping(RestConstants.API_TYPE_CATEGORY)
@Tag(name = RestConstants.TAG_TYPE_CATEGORY, description = RestConstants.DOC_TYPE_CATEGORY_CONTROLLER)
public class TypeCategoryRestController extends BaseRestController {

    private final TypeCategoryUseCase typeCategoryUseCase;

    public TypeCategoryRestController(ApiResponseBuilder responseBuilder,
                                      MessageService messageService,
                                      TypeCategoryUseCase typeCategoryUseCase) {
        super(responseBuilder, messageService);
        this.typeCategoryUseCase = typeCategoryUseCase;
    }

    /**
     * Creates a new type category.
     *
     * @param request the type category request DTO containing the data to create
     * @return a {@link GenericResponse} containing the created type category response DTO
     */
    @PostMapping
    @Operation(summary = RestConstants.DOC_TYPE_CATEGORY_CREATE)
    public GenericResponse<TypeCategoryResponseDto> create(@Valid @RequestBody TypeCategoryRequestDto request) {
        TypeCategoryResponseDto result = typeCategoryUseCase.create(request);
        return success(result, RestConstants.MSG_TYPE_CATEGORY_CREATED);
    }

    /**
     * Updates an existing type category.
     *
     * @param request the type category request DTO containing the data to update
     * @return a {@link GenericResponse} containing the updated type category response DTO
     */
    @PutMapping
    @Operation(summary = RestConstants.DOC_TYPE_CATEGORY_UPDATE)
    public GenericResponse<TypeCategoryResponseDto> update(@Valid @RequestBody TypeCategoryRequestDto request) {
        TypeCategoryResponseDto result = typeCategoryUseCase.update(request);
        return success(result, RestConstants.MSG_TYPE_CATEGORY_UPDATED);
    }

    /**
     * Deletes a type category by its identifier.
     *
     * @param id the identifier of the type category to delete
     * @return a {@link GenericResponse} containing the deleted type category response DTO
     */
    @DeleteMapping(RestConstants.PATH_ID)
    @Operation(summary = RestConstants.DOC_TYPE_CATEGORY_DELETE)
    public GenericResponse<TypeCategoryResponseDto> delete(@PathVariable Long id) {
        TypeCategoryResponseDto result = typeCategoryUseCase.delete(id);
        return success(result, RestConstants.MSG_TYPE_CATEGORY_DELETED);
    }

    /**
     * Retrieves a type category by its identifier.
     *
     * @param id the identifier of the type category to retrieve
     * @return a {@link GenericResponse} containing the found type category response DTO
     */
    @GetMapping(RestConstants.PATH_ID)
    @Operation(summary = RestConstants.DOC_TYPE_CATEGORY_GET_BY_ID)
    public GenericResponse<TypeCategoryResponseDto> getById(@PathVariable Long id) {
        TypeCategoryResponseDto result = typeCategoryUseCase.getById(id);
        return success(result, RestConstants.MSG_TYPE_CATEGORY_FOUND);
    }

    /**
     * Retrieves a list of type categories based on the provided filter.
     *
     * @param filter the filter criteria for the type categories
     * @return a {@link GenericResponse} containing the list of found type category response DTOs
     */
    @GetMapping(RestConstants.PATH_COMBO)
    @Operation(summary = RestConstants.DOC_TYPE_CATEGORY_COMBO)
    public GenericResponse<TypeCategoryResponseDto> combo(TypeCategoryFilterDto filter) {
        List<TypeCategoryResponseDto> result = typeCategoryUseCase.getAll(filter);
        return successList(result, RestConstants.MSG_TYPE_CATEGORY_LIST);
    }

    /**
     * Retrieves a paginated list of type categories based on the provided filter and pagination request.
     *
     * @param filter      the filter criteria for the type categories
     * @param pagination the pagination request
     * @return a {@link GenericResponse} containing the paginated list of type category response DTOs
     */
    @GetMapping(RestConstants.PATH_PAGINADO)
    @Operation(summary = RestConstants.DOC_TYPE_CATEGORY_PAGINADO)
    public GenericResponse<TypeCategoryResponseDto> paginado(
            TypeCategoryFilterDto filter,
            PaginationRequest pagination) {
        Page<TypeCategoryResponseDto> result = typeCategoryUseCase.getAllPaginado(filter, pagination);
        return paginated(result, RestConstants.MSG_TYPE_CATEGORY_PAGE);
    }
}
