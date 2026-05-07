package co.com.empresa.infrastructure.type;

import co.com.empresa.application.type.TypeFilterDto;
import co.com.empresa.application.type.TypeRequestDto;
import co.com.empresa.application.type.TypeResponseDto;
import co.com.empresa.application.type.TypeUseCase;
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
 * REST controller for Type operations.
 * <p>
 * This controller provides endpoints for creating, updating, deleting, and retrieving types,
 * delegating the business logic to the {@link TypeUseCase}.
 */
@RestController
@RequestMapping(RestConstants.API_TYPE)
@Tag(name = RestConstants.TAG_TYPE, description = RestConstants.DOC_TYPE_CONTROLLER)
public class TypeRestController extends BaseRestController {

    private final TypeUseCase typeUseCase;

    public TypeRestController(ApiResponseBuilder responseBuilder,
                              MessageService messageService,
                              TypeUseCase typeUseCase) {
        super(responseBuilder, messageService);
        this.typeUseCase = typeUseCase;
    }

    /**
     * Creates a new type.
     *
     * @param request the type request DTO containing the data to create
     * @return a {@link GenericResponse} containing the created type response DTO
     */
    @PostMapping
    @Operation(summary = RestConstants.DOC_TYPE_CREATE)
    public GenericResponse<TypeResponseDto> create(@Valid @RequestBody TypeRequestDto request) {
        TypeResponseDto result = typeUseCase.create(request);
        return success(result, RestConstants.MSG_TYPE_CREATED);
    }

    /**
     * Updates an existing type.
     *
     * @param request the type request DTO containing the data to update
     * @return a {@link GenericResponse} containing the updated type response DTO
     */
    @PutMapping
    @Operation(summary = RestConstants.DOC_TYPE_UPDATE)
    public GenericResponse<TypeResponseDto> update(@Valid @RequestBody TypeRequestDto request) {
        TypeResponseDto result = typeUseCase.update(request);
        return success(result, RestConstants.MSG_TYPE_UPDATED);
    }

    /**
     * Deletes a type by its identifier.
     *
     * @param id the identifier of the type to delete
     * @return a {@link GenericResponse} containing the deleted type response DTO
     */
    @DeleteMapping(RestConstants.PATH_ID)
    @Operation(summary = RestConstants.DOC_TYPE_DELETE)
    public GenericResponse<TypeResponseDto> delete(@PathVariable Long id) {
        TypeResponseDto result = typeUseCase.delete(id);
        return success(result, RestConstants.MSG_TYPE_DELETED);
    }

    /**
     * Retrieves a type by its identifier.
     *
     * @param id the identifier of the type to retrieve
     * @return a {@link GenericResponse} containing the found type response DTO
     */
    @GetMapping(RestConstants.PATH_ID)
    @Operation(summary = RestConstants.DOC_TYPE_GET_BY_ID)
    public GenericResponse<TypeResponseDto> getById(@PathVariable Long id) {
        TypeResponseDto result = typeUseCase.getById(id);
        return success(result, RestConstants.MSG_TYPE_FOUND);
    }

    /**
     * Retrieves a list of types based on the provided filter.
     *
     * @param filter the filter criteria for the types
     * @return a {@link GenericResponse} containing the list of found type response DTOs
     */
    @GetMapping(RestConstants.PATH_COMBO)
    @Operation(summary = RestConstants.DOC_TYPE_COMBO)
    public GenericResponse<TypeResponseDto> combo(TypeFilterDto filter) {
        List<TypeResponseDto> result = typeUseCase.getCombo(filter);
        return successList(result, RestConstants.MSG_TYPE_LIST);
    }

    /**
     * Retrieves a paginated list of types based on the provided filter and pagination request.
     *
     * @param filter      the filter criteria for the types
     * @param pagination the pagination request
     * @return a {@link GenericResponse} containing the paginated list of type response DTOs
     */
    @GetMapping(RestConstants.PATH_PAGINADO)
    @Operation(summary = RestConstants.DOC_TYPE_PAGINADO)
    public GenericResponse<TypeResponseDto> paginado(
            TypeFilterDto filter,
            PaginationRequest pagination) {
        Page<TypeResponseDto> result = typeUseCase.getComboPaginado(filter, pagination);
        return paginated(result, RestConstants.MSG_TYPE_PAGE);
    }
}
