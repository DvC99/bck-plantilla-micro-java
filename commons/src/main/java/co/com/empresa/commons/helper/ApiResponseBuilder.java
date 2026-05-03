package co.com.empresa.commons.helper;

import co.com.empresa.commons.constants.MessageKeys;
import co.com.empresa.commons.dto.response.GenericResponse;
import co.com.empresa.commons.services.i18.MessageService;
import co.com.empresa.commons.services.pageable.IPageableResult;
import co.com.empresa.commons.services.pageable.PageableResultImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Component for constructing standardized response objects for API communications.
 * <p>
 * Provides methods to create consistent and internationalized responses throughout the application.
 *
 * @see GenericResponse
 * @see IPageableResult
 */
@Component
@RequiredArgsConstructor
public class ApiResponseBuilder {

    @Autowired
    private final MessageService messageService;

    /**
     * Retrieves an internationalized message.
     *
     * @param key    the message key
     * @param params optional parameters for the message
     * @return the translated message, or the original key if an error occurs
     */
    private String getMessage(String key, Object... params) {
        try {
            return messageService.getMessage(key, params, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return key;
        }
    }

    /**
     * Creates a successful response with a single object and custom message.
     *
     * @param <T>     the type of the object
     * @param obj     the object to be included in the response
     * @param mensaje custom message
     * @return a {@link GenericResponse} containing the object
     */
    public <T> GenericResponse<T> success(T obj, String mensaje) {
        return GenericResponse.success(HttpStatus.OK.value(), mensaje, obj);
    }

    /**
     * Creates a successful response with a list of objects and custom message.
     *
     * @param <T>     the type of elements in the list
     * @param list    the list of objects to be included
     * @param mensaje custom message
     * @return a {@link GenericResponse} containing the list
     */
    public <T> GenericResponse<T> successList(List<T> list, String mensaje) {
        return GenericResponse.success(HttpStatus.OK.value(), mensaje, list != null ? list : Collections.emptyList());
    }

    /**
     * Creates a paginated response from an {@link IPageableResult} with custom message.
     *
     * @param <T>            the type of elements in the result
     * @param pageableResult the pageable result containing items and metadata
     * @param mensaje        custom message
     * @return a {@link GenericResponse} with pagination metadata
     */
    public <T> GenericResponse<T> paginated(IPageableResult<T> pageableResult, String mensaje) {
        if (pageableResult == null || pageableResult.getTotalElements() == 0) {
            return GenericResponse.success(HttpStatus.OK.value(), getMessage(MessageKeys.SUCCESS_NO_RESULTS), Collections.emptyList());
        }

        int totalPages = (int) Math.ceil((double) pageableResult.getTotalElements() / pageableResult.getPageSize());
        int currentPage = pageableResult.getPageNumber() + 1;

        return GenericResponse.successPaginated(
                HttpStatus.OK.value(),
                mensaje,
                pageableResult.getContent(),
                pageableResult.getTotalElements().intValue(),
                getMessage(MessageKeys.SUCCESS_PAGE_INFO, currentPage, totalPages)
        );
    }

    /**
     * Creates a paginated response from a Spring Data {@link Page} with custom message.
     *
     * @param <T>     the type of elements in the page
     * @param page    the Spring Data Page object
     * @param mensaje custom message
     * @return a {@link GenericResponse} with pagination metadata
     */
    public <T> GenericResponse<T> paginated(Page<T> page, String mensaje) {
        if (page == null || page.getTotalElements() == 0) {
            return GenericResponse.success(HttpStatus.OK.value(), getMessage(MessageKeys.SUCCESS_NO_RESULTS), Collections.emptyList());
        }

        return paginated(new PageableResultImpl<>(page), mensaje);
    }

    /**
     * Creates a no content response (HTTP 204) with custom message.
     *
     * @param <T>     the type of the response
     * @param mensaje custom message
     * @return a {@link GenericResponse} with HTTP 204 status
     */
    public <T> GenericResponse<T> noContent(String mensaje) {
        return GenericResponse.success(HttpStatus.NO_CONTENT.value(), mensaje, (T) null);
    }

    /**
     * Creates an error response from an exception.
     *
     * @param <T> the type of the response
     * @param ex  the exception that caused the error
     * @return a {@link GenericResponse} with error information
     */
    public <T> GenericResponse<T> error(Throwable ex) {
        return GenericResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }

    /**
     * Creates an error response from an exception with custom error code.
     *
     * @param <T>       the type of the response
     * @param ex        the exception that caused the error
     * @param errorCode the HTTP error code
     * @return a {@link GenericResponse} with error information
     */
    public <T> GenericResponse<T> error(Throwable ex, Integer errorCode) {
        return GenericResponse.error(errorCode, ex.getMessage());
    }

    /**
     * Creates an error response with a custom message.
     *
     * @param <T>     the type of the response
     * @param mensaje error message
     * @return a {@link GenericResponse} with error information
     */
    public <T> GenericResponse<T> error(String mensaje) {
        return GenericResponse.error(HttpStatus.BAD_REQUEST.value(), mensaje);
    }

    /**
     * Creates an error response with custom error code and message.
     *
     * @param <T>       the type of the response
     * @param errorCode the HTTP error code
     * @param mensaje   error message
     * @return a {@link GenericResponse} with error information
     */
    public <T> GenericResponse<T> error(Integer errorCode, String mensaje) {
        return GenericResponse.error(errorCode, mensaje);
    }

    /**
     * Creates a bad request response (HTTP 400).
     *
     * @param <T>     the type of the response
     * @param mensaje error message
     * @return a {@link GenericResponse} with HTTP 400 status
     */
    public <T> GenericResponse<T> badRequest(String mensaje) {
        return GenericResponse.error(HttpStatus.BAD_REQUEST.value(), mensaje);
    }

    /**
     * Creates a not found response (HTTP 404).
     *
     * @param <T>     the type of the response
     * @param mensaje error message
     * @return a {@link GenericResponse} with HTTP 404 status
     */
    public <T> GenericResponse<T> notFound(String mensaje) {
        return GenericResponse.error(HttpStatus.NOT_FOUND.value(), mensaje);
    }

    /**
     * Creates an unauthorized response (HTTP 401).
     *
     * @param <T>     the type of the response
     * @param mensaje error message
     * @return a {@link GenericResponse} with HTTP 401 status
     */
    public <T> GenericResponse<T> unauthorized(String mensaje) {
        return GenericResponse.error(HttpStatus.UNAUTHORIZED.value(), mensaje);
    }

    /**
     * Creates a forbidden response (HTTP 403).
     *
     * @param <T>     the type of the response
     * @param mensaje error message
     * @return a {@link GenericResponse} with HTTP 403 status
     */
    public <T> GenericResponse<T> forbidden(String mensaje) {
        return GenericResponse.error(HttpStatus.FORBIDDEN.value(), mensaje);
    }
}
