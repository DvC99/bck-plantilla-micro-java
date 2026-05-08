package co.com.empresa.commons.dto.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GenericResponse")
class GenericResponseTest {

    @Nested @DisplayName("success(code, message, single object)")
    class SuccessSingle {
        @Test @DisplayName("debe asignar ok=true, codigo, mensaje y dato")
        void success_objetoSimple_asignaCampos() {
            // Act
            GenericResponse<String> r = GenericResponse.success(200, "OK", "payload");
            // Assert
            assertTrue(r.isOk());
            assertEquals(200, r.getCodigo());
            assertEquals("OK", r.getMensaje());
            assertEquals("payload", r.getDato());
            assertNull(r.getDatos());
        }
    }

    @Nested @DisplayName("success(code, message, list)")
    class SuccessList {
        @Test @DisplayName("debe asignar ok=true, conteo igual al tamano de la lista")
        void success_lista_asignaConteo() {
            // Arrange
            List<String> items = List.of("a", "b", "c");
            // Act
            GenericResponse<String> r = GenericResponse.success(200, "OK", items);
            // Assert
            assertTrue(r.isOk());
            assertEquals(3, r.getConteo());
            assertEquals(items, r.getDatos());
            assertNull(r.getDato());
        }

        @Test @DisplayName("debe asignar conteo 0 cuando la lista es null")
        void success_listaNula_conteoCero() {
            // Act
            GenericResponse<String> r = GenericResponse.success(200, "OK", (List<String>) null);
            // Assert
            assertEquals(0, r.getConteo());
        }
    }

    @Nested @DisplayName("successPaginated()")
    class SuccessPaginated {
        @Test @DisplayName("debe asignar todos los campos de paginacion")
        void successPaginada_asignaTodos() {
            // Arrange
            List<String> items = List.of("x");
            // Act
            GenericResponse<String> r = GenericResponse.successPaginated(200, "OK", items, 50, "Pag 1/5");
            // Assert
            assertTrue(r.isOk());
            assertEquals(50, r.getConteo());
            assertEquals("Pag 1/5", r.getTotales());
            assertEquals(items, r.getDatos());
        }
    }

    @Nested @DisplayName("error()")
    class Error {
        @Test @DisplayName("debe asignar ok=false con codigo y mensaje")
        void error_asignaCampos() {
            // Act
            GenericResponse<Object> r = GenericResponse.error(400, "Bad request");
            // Assert
            assertFalse(r.isOk());
            assertEquals(400, r.getCodigo());
            assertEquals("Bad request", r.getMensaje());
        }
    }

    @Nested @DisplayName("Builder y NoArgsConstructor")
    class Constructores {
        @Test @DisplayName("constructor sin argumentos crea instancia con valores por defecto")
        void noArgs_creaDefault() {
            // Act
            GenericResponse<String> r = new GenericResponse<>();
            // Assert
            assertFalse(r.isOk());
            assertNull(r.getDato());
        }

        @Test @DisplayName("builder debe construir equivalente al factory estatico")
        void builder_construye() {
            // Act
            GenericResponse<String> r = GenericResponse.<String>builder()
                    .ok(true).codigo(201).mensaje("Created").dato("item").build();
            // Assert
            assertTrue(r.isOk());
            assertEquals(201, r.getCodigo());
            assertEquals("item", r.getDato());
        }
    }
}
