package co.com.empresa.commons.services.pageable;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PageableResultImpl")
class PageableResultImplTest {

    private EasyRandom easyRandom;

    @BeforeEach
    void setUp() {
        easyRandom = new EasyRandom();
    }

    @Nested @DisplayName("constructor desde Spring Page")
    class DesdePage {
        @Test @DisplayName("debe extraer content, pageNumber, pageSize, totalElements")
        void desdePage_extraeCampos() {
            // Arrange
            List<String> items = List.of("a", "b");
            Page<String> page = new PageImpl<>(items, PageRequest.of(1, 5), 12);
            // Act
            PageableResultImpl<String> r = new PageableResultImpl<>(page);
            // Assert
            assertEquals(items, r.getContent());
            assertEquals(1, r.getPageNumber());
            assertEquals(5, r.getPageSize());
            assertEquals(12L, r.getTotalElements());
        }
    }

    @Nested @DisplayName("constructor con valores directos")
    class DesdeValores {
        @Test @DisplayName("debe almacenar los valores proporcionados")
        void desdeValores_almacena() {
            // Arrange
            List<String> items = easyRandom.objects(String.class, 3).toList();
            // Act
            PageableResultImpl<String> r = new PageableResultImpl<>(items, 0, 10, 3L);
            // Assert
            assertEquals(items, r.getContent());
            assertEquals(0, r.getPageNumber());
            assertEquals(10, r.getPageSize());
            assertEquals(3L, r.getTotalElements());
        }
    }

    @Nested @DisplayName("getTotalPages()")
    class GetTotalPages {
        @Test @DisplayName("debe calcular total de paginas correctamente")
        void calcular_paginasPariales() {
            // Arrange
            var r = new PageableResultImpl<>(List.of(), 0, 10, 25L);
            // Act & Assert
            assertEquals(3, r.getTotalPages());
        }

        @Test @DisplayName("debe retornar 0 cuando totalElements es 0")
        void ceroElementos_retornaCero() {
            // Arrange
            var r = new PageableResultImpl<>(List.of(), 0, 10, 0L);
            // Act & Assert
            assertEquals(0, r.getTotalPages());
        }

        @Test @DisplayName("debe retornar 0 cuando pageSize es 0")
        void pageSizeCero_retornaCero() {
            // Arrange
            var r = new PageableResultImpl<>(List.of(), 0, 0, 10L);
            // Act & Assert
            assertEquals(0, r.getTotalPages());
        }

        @Test @DisplayName("debe retornar 1 cuando elementos llenan exactamente una pagina")
        void exactoUnaPagina_retornaUno() {
            // Arrange
            var r = new PageableResultImpl<>(List.of(), 0, 5, 5L);
            // Act & Assert
            assertEquals(1, r.getTotalPages());
        }
    }
}
