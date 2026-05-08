package co.com.empresa.infrastructure.event;

import co.com.empresa.domain.event.EventAudit;
import co.com.empresa.infrastructure.adapters.output.repositories.command.event.IEventAuditCommandJpaRepository;
import co.com.empresa.infrastructure.adapters.output.repositories.query.event.IEventAuditQueryJpaRepository;
import co.com.empresa.infrastructure.entities.event.EventAuditEntity;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("EventAuditRepositoryImpl")
class EventAuditRepositoryImplTest {

    @Mock private IEventAuditQueryJpaRepository queryJpaRepository;
    @Mock private IEventAuditCommandJpaRepository commandJpaRepository;
    @Mock private EventAuditInfrastructureMapper mapper;
    @InjectMocks private EventAuditRepositoryImpl repo;

    private EasyRandom easyRandom;
    private EventAudit model;
    private EventAuditEntity entity;

    @BeforeEach
    void setUp() {
        easyRandom = new EasyRandom();
        model = EventAudit.builder().id(1L).eventId("evt-1").eventType("test").build();
        entity = EventAuditEntity.builder().id(1L).eventId("evt-1").eventType("test").build();
    }

    @Nested @DisplayName("findById")
    class FindById {
        @Test @DisplayName("debe retornar modelo cuando existe")
        void existe_retorna() {
            when(queryJpaRepository.findById(1L)).thenReturn(Optional.of(entity));
            when(mapper.entityToModel(entity)).thenReturn(model);
            var r = repo.findById(1L);
            assertTrue(r.isPresent());
            assertEquals(model, r.get());
        }
        @Test @DisplayName("debe retornar vacio cuando no existe")
        void noExiste_retornaVacio() {
            when(queryJpaRepository.findById(99L)).thenReturn(Optional.empty());
            assertTrue(repo.findById(99L).isEmpty());
        }
    }

    @Nested @DisplayName("save")
    class Save {
        @Test @DisplayName("debe mapear guardar y mapear de vuelta")
        void save_flujo() {
            when(mapper.modelToEntity(model)).thenReturn(entity);
            when(commandJpaRepository.save(entity)).thenReturn(entity);
            when(mapper.entityToModel(entity)).thenReturn(model);
            assertEquals(model, repo.save(model));
        }
    }

    @Nested @DisplayName("saveAll")
    class SaveAll {
        @Test @DisplayName("debe guardar todos en lote")
        void saveAll_lote() {
            var list = List.of(model);
            when(mapper.toEntityList(list)).thenReturn(List.of(entity));
            when(commandJpaRepository.saveAll(anyList())).thenReturn(List.of(entity));
            when(mapper.toModelList(anyList())).thenReturn(list);
            assertEquals(list, repo.saveAll(list));
        }
    }

    @Nested @DisplayName("update")
    class Update {
        @Test @DisplayName("debe delegar en command repo")
        void update_delega() {
            when(mapper.modelToEntity(model)).thenReturn(entity);
            when(commandJpaRepository.save(entity)).thenReturn(entity);
            when(mapper.entityToModel(entity)).thenReturn(model);
            assertEquals(model, repo.update(model));
        }
    }

    @Nested @DisplayName("updateAll")
    class UpdateAll {
        @Test @DisplayName("debe guardar todos")
        void updateAll_delega() {
            var list = List.of(model);
            when(mapper.toEntityList(list)).thenReturn(List.of(entity));
            when(commandJpaRepository.saveAll(anyList())).thenReturn(List.of(entity));
            when(mapper.toModelList(anyList())).thenReturn(list);
            assertEquals(list, repo.updateAll(list));
        }
        @Test @DisplayName("debe retornar vacio cuando es null")
        void updateAll_nulo_retornaVacio() {
            assertFalse(repo.updateAll(null).iterator().hasNext());
        }
    }

    @Nested @DisplayName("delete")
    class Delete {
        @Test @DisplayName("debe delegar en command repo")
        void delete_delega() {
            repo.delete(1L);
            verify(commandJpaRepository).deleteById(1L);
        }
    }

    @Nested @DisplayName("deleteAll")
    class DeleteAll {
        @Test @DisplayName("debe delegar en batch")
        void deleteAll_batch() {
            var ids = List.of(1L, 2L);
            repo.deleteAll(ids);
            verify(commandJpaRepository).deleteAllByIdInBatch(ids);
        }
        @Test @DisplayName("debe ignorar null")
        void deleteAll_nulo_noop() {
            repo.deleteAll(null);
            verifyNoInteractions(commandJpaRepository);
        }
        @Test @DisplayName("debe ignorar vacio")
        void deleteAll_vacio_noop() {
            repo.deleteAll(Collections.emptyList());
            verifyNoInteractions(commandJpaRepository);
        }
    }

    @Nested @DisplayName("existsById")
    class ExistsById {
        @Test @DisplayName("debe delegar en query repo")
        void existsById_delega() {
            when(queryJpaRepository.existsById(1L)).thenReturn(true);
            assertTrue(repo.existsById(1L));
        }
    }

    @Nested @DisplayName("getNextValSequence")
    class GetNextValSequence {
        @Test @DisplayName("debe delegar en query repo")
        void getNextValSequence_delega() {
            when(queryJpaRepository.getNextValSequence()).thenReturn(100L);
            assertEquals(100L, repo.getNextValSequence());
        }
    }

    @Nested @DisplayName("findAll")
    class FindAll {
        @Test @DisplayName("findAll(Example, Pageable) debe mapear y retornar pagina")
        void findAll_paginado() {
            Example<EventAudit> ex = Example.of(model);
            when(mapper.modelToEntity(model)).thenReturn(entity);
            Page<EventAuditEntity> entityPage = new PageImpl<>(List.of(entity), PageRequest.of(0, 10), 1);
            when(queryJpaRepository.findAll((Example<EventAuditEntity>) any(), any(Pageable.class))).thenReturn(entityPage);
            when(mapper.toModelList(List.of(entity))).thenReturn(List.of(model));
            var r = repo.findAll(ex, PageRequest.of(0, 10));
            assertEquals(1, r.getTotalElements());
            assertEquals(model, r.getContent().get(0));
        }

        @Test @DisplayName("findAll(Example) debe retornar lista")
        void findAll_lista() {
            Example<EventAudit> ex = Example.of(model);
            when(mapper.modelToEntity(model)).thenReturn(entity);
            when(queryJpaRepository.findAll((Example<EventAuditEntity>) any())).thenReturn(List.of(entity));
            when(mapper.toModelList(List.of(entity))).thenReturn(List.of(model));
            assertEquals(List.of(model), repo.findAll(ex));
        }
    }
}
