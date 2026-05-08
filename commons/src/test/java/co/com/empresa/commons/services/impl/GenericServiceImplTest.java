package co.com.empresa.commons.services.impl;

import co.com.empresa.commons.dto.request.PaginationRequest;
import co.com.empresa.commons.repository.IRepository;
import co.com.empresa.commons.services.pageable.IPageableResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("GenericServiceImpl")
class GenericServiceImplTest {

    @Mock
    private IRepository<String, Long> repository;

    private GenericServiceImpl<String, Long> service;

    @BeforeEach
    void setUp() {
        // Arrange
        IRepository<String, Long> repo = repository;
        service = new GenericServiceImpl<>() {
            @Override protected IRepository<String, Long> getRepository() { return repo; }
            @Override protected Long getModelKey(String model) { return model == null ? null : (long) model.length(); }
            @Override protected String getEmptyModel() { return ""; }
        };
    }

    @Nested @DisplayName("getElement()")
    class GetElement {
        @Test @DisplayName("debe retornar modelo cuando existe")
        void getElement_existe_retornaModelo() {
            // Arrange
            when(repository.findById(1L)).thenReturn(Optional.of("encontrado"));
            // Act
            String r = service.getElement(1L);
            // Assert
            assertEquals("encontrado", r);
        }
        @Test @DisplayName("debe retornar null cuando no existe")
        void getElement_noExiste_retornaNull() {
            // Arrange
            when(repository.findById(99L)).thenReturn(Optional.empty());
            // Act
            String r = service.getElement(99L);
            // Assert
            assertNull(r);
        }
    }

    @Nested @DisplayName("save()")
    class Save {
        @Test @DisplayName("debe delegar en repository.save()")
        void save_delega() {
            // Arrange
            when(repository.save("m")).thenReturn("guardado");
            // Act
            String r = service.save("m");
            // Assert
            assertEquals("guardado", r);
            verify(repository).save("m");
        }
    }

    @Nested @DisplayName("saveAll()")
    class SaveAll {
        @Test @DisplayName("debe delegar en repository.saveAll()")
        void saveAll_delega() {
            // Arrange
            List<String> lista = List.of("a", "b");
            when(repository.saveAll(lista)).thenReturn(lista);
            // Act
            Iterable<String> r = service.saveAll(lista);
            // Assert
            assertEquals(lista, r);
        }
    }

    @Nested @DisplayName("update()")
    class Update {
        @Test @DisplayName("debe delegar en repository.update()")
        void update_delega() {
            // Arrange
            when(repository.update("m")).thenReturn("actualizado");
            // Act
            String r = service.update("m");
            // Assert
            assertEquals("actualizado", r);
        }
    }

    @Nested @DisplayName("updateAll()")
    class UpdateAll {
        @Test @DisplayName("debe delegar en repository.saveAll()")
        void updateAll_delega() {
            // Arrange
            List<String> lista = List.of("x");
            when(repository.saveAll(lista)).thenReturn(lista);
            // Act
            Iterable<String> r = service.updateAll(lista);
            // Assert
            assertEquals(lista, r);
        }
    }

    @Nested @DisplayName("delete()")
    class Delete {
        @Test @DisplayName("debe retornar el modelo sin llamar al repositorio")
        void delete_retornaModelo() {
            // Act
            String r = service.delete("m");
            // Assert
            assertEquals("m", r);
            verifyNoInteractions(repository);
        }
    }

    @Nested @DisplayName("existById()")
    class ExistById {
        @Test @DisplayName("debe retornar true cuando el repositorio confirma")
        void existById_existe_retornaTrue() {
            // Arrange
            when(repository.existsById(1L)).thenReturn(true);
            // Act & Assert
            assertTrue(service.existById(1L));
        }
        @Test @DisplayName("debe retornar false cuando el repositorio niega")
        void existById_noExiste_retornaFalse() {
            // Arrange
            when(repository.existsById(2L)).thenReturn(false);
            // Act & Assert
            assertFalse(service.existById(2L));
        }
    }

    @Nested @DisplayName("getNextId()")
    class GetNextId {
        @Test @DisplayName("debe retornar 0L por defecto")
        void getNextId_retornaCero() {
            // Act & Assert
            assertEquals(0L, service.getNextId());
        }
    }

    @Nested @DisplayName("getComboGrande()")
    class GetComboGrande {
        @Test @DisplayName("debe retornar resultado paginado vacio por defecto")
        void getComboGrande_retornaVacio() {
            // Arrange
            var pag = new PaginationRequest(0, 10, "id", "asc", null);
            // Act
            IPageableResult<String> r = service.getComboGrande("filtro", pag);
            // Assert
            assertNotNull(r);
            assertEquals(0L, r.getTotalElements());
            assertTrue(r.getContent().isEmpty());
        }
    }

    @Nested @DisplayName("getComboSencillo()")
    class GetComboSencillo {
        @Test @DisplayName("debe retornar lista vacia por defecto")
        void getComboSencillo_retornaVacia() {
            // Act & Assert
            assertTrue(service.getComboSencillo("filtro").isEmpty());
        }
    }
}
