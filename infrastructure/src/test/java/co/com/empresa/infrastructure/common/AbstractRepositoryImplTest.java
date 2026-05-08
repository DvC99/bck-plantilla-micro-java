package co.com.empresa.infrastructure.common;

import co.com.empresa.commons.mapper.IGenericMapper;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AbstractRepositoryImpl")
class AbstractRepositoryImplTest {

    @Mock private JpaRepository<String, Long> commandJpaRepository;
    @Mock private JpaRepository<String, Long> queryJpaRepository;
    @Mock private IGenericMapper<String, String> mapper;

    private AbstractRepositoryImpl<String, String, Long> repo;

    private EasyRandom easyRandom;

    @BeforeEach
    void setUp() {
        easyRandom = new EasyRandom();
        var cmd = commandJpaRepository;
        var qry = queryJpaRepository;
        var mp = mapper;
        repo = new AbstractRepositoryImpl<>(cmd, qry, mp) {
            @Override public Long getNextValSequence() { return 99L; }
        };
    }

    @Test @DisplayName("getNextValSequence debe retornar el valor sobrescrito")
    void getNextValSequence_sobrescrito_retornaValor() {
        // Act & Assert
        assertEquals(99L, repo.getNextValSequence());
    }

    @Nested @DisplayName("findById")
    class FindById {
        @Test @DisplayName("debe retornar modelo mapeado cuando existe")
        void findById_existe_retornaModelo() {
            // Arrange
            Long id = easyRandom.nextLong();
            when(queryJpaRepository.findById(id)).thenReturn(Optional.of("entidad"));
            when(mapper.entityToModel("entidad")).thenReturn("modelo");
            // Act
            var r = repo.findById(id);
            // Assert
            assertTrue(r.isPresent());
            assertEquals("modelo", r.get());
        }
        @Test @DisplayName("debe retornar empty cuando no existe")
        void findById_noExiste_retornaVacio() {
            // Arrange
            Long id = easyRandom.nextLong();
            when(queryJpaRepository.findById(id)).thenReturn(Optional.empty());
            // Act
            var r = repo.findById(id);
            // Assert
            assertTrue(r.isEmpty());
        }
    }

    @Nested @DisplayName("existsById")
    class ExistsById {
        @Test @DisplayName("debe delegar al query repository")
        void existsById_delega() {
            // Arrange
            Long id = easyRandom.nextLong();
            when(queryJpaRepository.existsById(id)).thenReturn(true);
            // Act
            boolean r = repo.existsById(id);
            // Assert
            assertTrue(r);
        }
    }

    @Nested @DisplayName("save")
    class Save {
        @Test @DisplayName("debe mapear modelo a entidad, guardar y mapear de vuelta")
        void save_mapeaYGuarda() {
            // Arrange
            String model = easyRandom.nextObject(String.class);
            String entity = easyRandom.nextObject(String.class);
            String saved = easyRandom.nextObject(String.class);
            when(mapper.modelToEntity(model)).thenReturn(entity);
            when(commandJpaRepository.save(entity)).thenReturn(saved);
            when(mapper.entityToModel(saved)).thenReturn(model);
            // Act
            String r = repo.save(model);
            // Assert
            assertEquals(model, r);
        }
    }

    @Nested @DisplayName("saveAll")
    class SaveAll {
        @Test @DisplayName("debe guardar todos y mapear de vuelta")
        void saveAll_lote() {
            // Arrange
            List<String> models = easyRandom.objects(String.class, 3).toList();
            when(mapper.toEntityList(models)).thenReturn(models);
            when(commandJpaRepository.saveAll(models)).thenReturn(models);
            when(mapper.toModelList(models)).thenReturn(models);
            // Act
            var r = repo.saveAll(models);
            // Assert
            assertEquals(models, r);
        }
    }

    @Nested @DisplayName("update")
    class Update {
        @Test @DisplayName("debe delegar en commandJpaRepository.save")
        void update_delega() {
            // Arrange
            String model = easyRandom.nextObject(String.class);
            String entity = easyRandom.nextObject(String.class);
            String savedEntity = easyRandom.nextObject(String.class);
            String mapped = easyRandom.nextObject(String.class);
            when(mapper.modelToEntity(model)).thenReturn(entity);
            when(commandJpaRepository.save(entity)).thenReturn(savedEntity);
            when(mapper.entityToModel(savedEntity)).thenReturn(mapped);
            // Act
            String r = repo.update(model);
            // Assert
            assertEquals(mapped, r);
        }
    }

    @Nested @DisplayName("updateAll")
    class UpdateAll {
        @Test @DisplayName("debe delegar en saveAll")
        void updateAll_delega() {
            // Arrange
            List<String> models = easyRandom.objects(String.class, 2).toList();
            when(mapper.toEntityList(models)).thenReturn(models);
            when(commandJpaRepository.saveAll(models)).thenReturn(models);
            when(mapper.toModelList(models)).thenReturn(models);
            // Act
            var r = repo.updateAll(models);
            // Assert
            assertEquals(models, r);
        }

        @Test @DisplayName("debe retornar lista vacia cuando la entrada es null")
        void updateAll_nulo_retornaVacio() {
            // Act
            var r = repo.updateAll(null);
            // Assert
            assertFalse(r.iterator().hasNext());
        }
    }

    @Nested @DisplayName("delete")
    class Delete {
        @Test @DisplayName("debe delegar en commandJpaRepository.deleteById")
        void delete_delega() {
            // Arrange
            Long id = easyRandom.nextLong();
            // Act
            repo.delete(id);
            // Assert
            verify(commandJpaRepository).deleteById(id);
        }
    }

    @Nested @DisplayName("deleteAll")
    class DeleteAll {
        @Test @DisplayName("debe delegar en deleteAllByIdInBatch")
        void deleteAll_delega() {
            // Arrange
            List<Long> ids = easyRandom.objects(Long.class, 3).toList();
            // Act
            repo.deleteAll(ids);
            // Assert
            verify(commandJpaRepository).deleteAllByIdInBatch(ids);
        }

        @Test @DisplayName("no debe hacer nada cuando la entrada es null")
        void deleteAll_nulo_sinOperacion() {
            // Act
            repo.deleteAll(null);
            // Assert
            verifyNoInteractions(commandJpaRepository);
        }

        @Test @DisplayName("no debe hacer nada cuando la entrada es vacia")
        void deleteAll_vacio_sinOperacion() {
            // Act
            repo.deleteAll(Collections.emptyList());
            // Assert
            verifyNoInteractions(commandJpaRepository);
        }
    }

    @Nested @DisplayName("findAll con Example")
    class FindAll {
        @Test @DisplayName("findAll(Example, Pageable) debe mapear probe y retornar pagina")
        void findAll_paginado() {
            // Arrange
            String probe = easyRandom.nextObject(String.class);
            Example<String> ex = Example.of(probe);
            when(mapper.modelToEntity(probe)).thenReturn("eProbe");
            Page<String> entityPage = new PageImpl<>(List.of("e1"), PageRequest.of(0, 10), 1);
            when(queryJpaRepository.findAll((Example<String>) any(), any(Pageable.class))).thenReturn(entityPage);
            when(mapper.toModelList(List.of("e1"))).thenReturn(List.of("m1"));
            // Act
            Page<String> r = repo.findAll(ex, PageRequest.of(0, 10));
            // Assert
            assertEquals(1, r.getTotalElements());
            assertEquals("m1", r.getContent().get(0));
        }

        @Test @DisplayName("findAll(Example) debe mapear probe y retornar lista")
        void findAll_lista() {
            // Arrange
            String probe = easyRandom.nextObject(String.class);
            Example<String> ex = Example.of(probe);
            when(mapper.modelToEntity(probe)).thenReturn("eF");
            when(queryJpaRepository.findAll(any(Example.class))).thenReturn(List.of("eR"));
            when(mapper.toModelList(List.of("eR"))).thenReturn(List.of("mR"));
            // Act
            var r = repo.findAll(ex);
            // Assert
            assertEquals(List.of("mR"), r);
        }
    }
}
