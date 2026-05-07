package co.com.empresa.infrastructure.common;

import co.com.empresa.commons.dto.response.GenericResponse;
import co.com.empresa.commons.helper.ApiResponseBuilder;
import co.com.empresa.commons.services.i18.MessageService;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Base controller para estandarizar respuestas REST.
 */
public abstract class BaseRestController {
    private final ApiResponseBuilder responseBuilder;
    private final MessageService messageService;

    protected BaseRestController(ApiResponseBuilder responseBuilder, MessageService messageService) {
        this.responseBuilder = responseBuilder;
        this.messageService = messageService;
    }

    protected <T> GenericResponse<T> success(T result, String messageKey) {
        return responseBuilder.success(result, messageService.getMessage(messageKey));
    }

    protected <T> GenericResponse<T> successList(List<T> result, String messageKey) {
        return responseBuilder.successList(result, messageService.getMessage(messageKey));
    }

    protected <T> GenericResponse<T> paginated(Page<T> result, String messageKey) {
        return responseBuilder.paginated(result, messageService.getMessage(messageKey));
    }
}
