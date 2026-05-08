package co.com.empresa.commons.util;

import co.com.empresa.commons.services.pageable.IPageableResult;
import co.com.empresa.commons.services.pageable.PageableResultImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PaginationHelper")
class PaginationHelperTest {

    @Nested @DisplayName("mapPage")
    class MapPage {
        @Test @DisplayName("debe mapear elementos de modelo a DTO preservando metadata")
        void mapPage_conDatos() {
            // Arrange
            var pr = new PageableResultImpl<>(List.of("a", "b"), 0, 10, 2L);
            // Act
            var page = PaginationHelper.mapPage(pr, String::toUpperCase);
            // Assert
            assertEquals(List.of("A", "B"), page.getContent());
            assertEquals(2L, page.getTotalElements());
            assertEquals(0, page.getNumber());
            assertEquals(10, page.getSize());
        }

        @Test @DisplayName("debe retornar pagina vacia cuando el resultado es null")
        void mapPage_null_retornaVacia() {
            // Act
            var page = PaginationHelper.mapPage(null, (Function<String, String>) String::toUpperCase);
            // Assert
            assertTrue(page.isEmpty());
            assertEquals(0, page.getTotalElements());
        }
    }

    @Nested @DisplayName("mapPageList")
    class MapPageList {
        @Test @DisplayName("debe mapear lista completa de modelo a DTO")
        void mapPageList_conDatos() {
            // Arrange
            var pr = new PageableResultImpl<>(List.of("a", "b"), 0, 10, 2L);
            // Act
            var page = PaginationHelper.mapPageList(pr, list -> list.stream().map(String::toUpperCase).toList());
            // Assert
            assertEquals(List.of("A", "B"), page.getContent());
        }

        @Test @DisplayName("debe retornar pagina vacia cuando el resultado es null")
        void mapPageList_null_retornaVacia() {
            // Act
            var page = PaginationHelper.mapPageList(null, list -> list);
            // Assert
            assertTrue(page.isEmpty());
        }
    }
}
