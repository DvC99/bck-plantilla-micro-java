package co.com.empresa.commons.helper;

import co.com.empresa.commons.constants.MessageKeys;
import co.com.empresa.commons.dto.response.GenericResponse;
import co.com.empresa.commons.services.i18.MessageService;
import co.com.empresa.commons.services.pageable.IPageableResult;
import co.com.empresa.commons.services.pageable.PageableResultImpl;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ApiResponseBuilder")
class ApiResponseBuilderTest {

    @Mock private MessageService messageService;

    @InjectMocks private ApiResponseBuilder builder;

    private EasyRandom easyRandom;

    @BeforeEach
    void setUp() {
        easyRandom = new EasyRandom();
    }

    @Nested @DisplayName("success(object, message)")
    class SuccessSingle {
        @Test @DisplayName("debe retornar GenericResponse con dato unico")
        void success_objeto_ok() {
            // Arrange
            String data = easyRandom.nextObject(String.class);
            String msg = easyRandom.nextObject(String.class);
            // Act
            GenericResponse<String> r = builder.success(data, msg);
            // Assert
            assertTrue(r.isOk());
            assertEquals(HttpStatus.OK.value(), r.getCodigo());
            assertEquals(msg, r.getMensaje());
            assertEquals(data, r.getDato());
        }
    }

    @Nested @DisplayName("successList(list, message)")
    class SuccessList {
        @Test @DisplayName("debe retornar GenericResponse con lista")
        void successList_lista_ok() {
            // Arrange
            List<String> items = easyRandom.objects(String.class, 3).toList();
            String msg = easyRandom.nextObject(String.class);
            // Act
            GenericResponse<String> r = builder.successList(items, msg);
            // Assert
            assertTrue(r.isOk());
            assertEquals(3, r.getConteo());
            assertEquals(items, r.getDatos());
        }

        @Test @DisplayName("debe retornar lista vacia cuando la entrada es null")
        void successList_null_retornaVacia() {
            // Arrange
            String msg = easyRandom.nextObject(String.class);
            // Act
            GenericResponse<String> r = builder.successList(null, msg);
            // Assert
            assertTrue(r.isOk());
            assertEquals(0, r.getConteo());
            assertTrue(r.getDatos().isEmpty());
        }
    }

    @Nested @DisplayName("paginated(IPageableResult, message)")
    class PaginatedPageable {
        @Test @DisplayName("debe retornar respuesta paginada con datos")
        void paginated_conDatos() {
            // Arrange
            List<String> items = easyRandom.objects(String.class, 5).toList();
            IPageableResult<String> pr = new PageableResultImpl<>(items, 0, 5, 10L);
            String msg = easyRandom.nextObject(String.class);
            // Act
            GenericResponse<String> r = builder.paginated(pr, msg);
            // Assert
            assertTrue(r.isOk());
            assertEquals(10, r.getConteo());
        }

        @Test @DisplayName("debe retornar sin resultados cuando totalElements es 0")
        void paginated_sinResultados() {
            // Arrange
            IPageableResult<String> pr = new PageableResultImpl<>(List.of(), 0, 5, 0L);
            String msg = easyRandom.nextObject(String.class);
            // Act
            GenericResponse<String> r = builder.paginated(pr, msg);
            // Assert
            assertTrue(r.isOk());
            assertEquals(0, r.getConteo());
        }

        @Test @DisplayName("debe retornar sin resultados cuando pageableResult es null")
        void paginated_null_sinResultados() {
            // Arrange
            String msg = easyRandom.nextObject(String.class);
            // Act
            GenericResponse<String> r = builder.paginated((IPageableResult<String>) null, msg);
            // Assert
            assertTrue(r.isOk());
            assertEquals(0, r.getConteo());
        }
    }

    @Nested @DisplayName("paginated(Page, message)")
    class PaginatedPage {
        @Test @DisplayName("debe retornar respuesta paginada desde Page")
        void paginated_page_conDatos() {
            // Arrange
            Page<String> page = new PageImpl<>(List.of("a"), PageRequest.of(0, 10), 1);
            String msg = easyRandom.nextObject(String.class);
            // Act
            GenericResponse<String> r = builder.paginated(page, msg);
            // Assert
            assertTrue(r.isOk());
            assertEquals(1, r.getConteo());
        }

        @Test @DisplayName("debe retornar sin resultados cuando page es null")
        void paginated_pageNull_sinResultados() {
            // Arrange
            String msg = easyRandom.nextObject(String.class);
            // Act
            GenericResponse<String> r = builder.paginated((Page<String>) null, msg);
            // Assert
            assertTrue(r.isOk());
            assertEquals(0, r.getConteo());
        }
    }

    @Nested @DisplayName("noContent")
    class NoContent {
        @Test @DisplayName("debe retornar 204 sin dato")
        void noContent_204() {
            // Arrange
            String msg = easyRandom.nextObject(String.class);
            // Act
            GenericResponse<Object> r = builder.noContent(msg);
            // Assert
            assertEquals(HttpStatus.NO_CONTENT.value(), r.getCodigo());
            assertNull(r.getDato());
        }
    }

    @Nested @DisplayName("error")
    class Error {
        @Test @DisplayName("error(Throwable) debe retornar 500")
        void error_throwable_500() {
            // Act
            GenericResponse<Object> r = builder.error(new RuntimeException("boom"));
            // Assert
            assertEquals(500, r.getCodigo());
            assertFalse(r.isOk());
        }

        @Test @DisplayName("error(Throwable, code) debe retornar el codigo especificado")
        void error_throwable_code() {
            // Act
            GenericResponse<Object> r = builder.error(new RuntimeException("boom"), 503);
            // Assert
            assertEquals(503, r.getCodigo());
        }

        @Test @DisplayName("error(String) debe retornar 400")
        void error_mensaje_400() {
            // Act
            GenericResponse<Object> r = builder.error("bad");
            // Assert
            assertEquals(400, r.getCodigo());
        }

        @Test @DisplayName("error(code, message) debe retornar codigo y mensaje")
        void error_codigo_mensaje() {
            // Act
            GenericResponse<Object> r = builder.error(422, "validation");
            // Assert
            assertEquals(422, r.getCodigo());
            assertEquals("validation", r.getMensaje());
        }
    }

    @Nested @DisplayName("HTTP helpers")
    class HttpHelpers {
        @Test @DisplayName("badRequest debe retornar 400")
        void badRequest_400() {
            GenericResponse<Object> r = builder.badRequest("mal");
            assertEquals(400, r.getCodigo());
        }

        @Test @DisplayName("notFound debe retornar 404")
        void notFound_404() {
            GenericResponse<Object> r = builder.notFound("no existe");
            assertEquals(404, r.getCodigo());
        }

        @Test @DisplayName("unauthorized debe retornar 401")
        void unauthorized_401() {
            GenericResponse<Object> r = builder.unauthorized("no auth");
            assertEquals(401, r.getCodigo());
        }

        @Test @DisplayName("forbidden debe retornar 403")
        void forbidden_403() {
            GenericResponse<Object> r = builder.forbidden("denegado");
            assertEquals(403, r.getCodigo());
        }
    }
}
