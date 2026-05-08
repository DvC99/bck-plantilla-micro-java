package co.com.empresa.infrastructure.event;

import co.com.empresa.commons.exception.InfrastructureException;
import co.com.empresa.domain.common.events.EventEnvelope;
import co.com.empresa.infrastructure.entities.event.EventAuditEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("EventAuditServiceImpl")
class EventAuditServiceImplTest {

    @Mock private EventAuditRepository eventAuditRepository;
    @Mock private ObjectMapper objectMapper;
    @InjectMocks private EventAuditServiceImpl service;

    private EventEnvelope<String> envelope;

    @BeforeEach
    void setUp() {
        envelope = new EventEnvelope<>("evt-123", "test.event", "payload-data",
                LocalDateTime.now(), "corr-456");
    }

    @Nested @DisplayName("isDuplicate")
    class IsDuplicate {
        @Test @DisplayName("debe retornar true cuando el evento ya existe")
        void eventoExistente_retornaTrue() {
            // Arrange
            when(eventAuditRepository.findByEventId("evt-123"))
                    .thenReturn(Optional.of(new EventAuditEntity()));
            // Act
            boolean r = service.isDuplicate("evt-123");
            // Assert
            assertTrue(r);
        }

        @Test @DisplayName("debe retornar false cuando el evento no existe")
        void eventoNoExiste_retornaFalse() {
            // Arrange
            when(eventAuditRepository.findByEventId("evt-123"))
                    .thenReturn(Optional.empty());
            // Act
            boolean r = service.isDuplicate("evt-123");
            // Assert
            assertFalse(r);
        }
    }

    @Nested @DisplayName("registerIncoming")
    class RegisterIncoming {
        @Test @DisplayName("debe guardar evento entrante con direccion INCOMING y estado RECEIVED")
        void eventoValido_guardaIncoming() throws Exception {
            // Arrange
            String jsonPayload = "\"payload-data\"";
            when(objectMapper.writeValueAsString(envelope.payload())).thenReturn(jsonPayload);
            // Act
            service.registerIncoming(envelope);
            // Assert
            ArgumentCaptor<EventAuditEntity> captor = ArgumentCaptor.forClass(EventAuditEntity.class);
            verify(eventAuditRepository).save(captor.capture());
            EventAuditEntity saved = captor.getValue();
            assertEquals("evt-123", saved.getEventId());
            assertEquals("test.event", saved.getEventType());
            assertEquals("INCOMING", saved.getDirection());
            assertEquals("RECEIVED", saved.getStatus());
            assertEquals(jsonPayload, saved.getPayload());
        }

        @Test @DisplayName("debe lanzar InfrastructureException cuando falla la serializacion")
        void serializacionFalla_lanza() throws Exception {
            // Arrange
            when(objectMapper.writeValueAsString(any()))
                    .thenThrow(JsonProcessingException.class);
            // Act & Assert
            assertThrows(InfrastructureException.class, () -> service.registerIncoming(envelope));
        }
    }

    @Nested @DisplayName("registerOutgoing")
    class RegisterOutgoing {
        @Test @DisplayName("debe guardar evento saliente con direccion OUTGOING y estado SENT")
        void eventoValido_guardaOutgoing() throws Exception {
            // Arrange
            String jsonPayload = "\"payload-data\"";
            when(objectMapper.writeValueAsString(envelope.payload())).thenReturn(jsonPayload);
            // Act
            service.registerOutgoing(envelope);
            // Assert
            ArgumentCaptor<EventAuditEntity> captor = ArgumentCaptor.forClass(EventAuditEntity.class);
            verify(eventAuditRepository).save(captor.capture());
            assertEquals("OUTGOING", captor.getValue().getDirection());
            assertEquals("SENT", captor.getValue().getStatus());
        }

        @Test @DisplayName("debe lanzar InfrastructureException cuando falla la serializacion")
        void serializacionFalla_lanza() throws Exception {
            // Arrange
            when(objectMapper.writeValueAsString(any()))
                    .thenThrow(JsonProcessingException.class);
            // Act & Assert
            assertThrows(InfrastructureException.class, () -> service.registerOutgoing(envelope));
        }
    }

    @Nested @DisplayName("updateStatus")
    class UpdateStatus {
        @Test @DisplayName("debe actualizar estado cuando el evento existe")
        void eventoExistente_actualiza() {
            // Arrange
            EventAuditEntity entity = new EventAuditEntity();
            entity.setStatus("RECEIVED");
            when(eventAuditRepository.findByEventId("evt-123"))
                    .thenReturn(Optional.of(entity));
            // Act
            service.updateStatus("evt-123", "PROCESSED");
            // Assert
            verify(eventAuditRepository).save(entity);
            assertEquals("PROCESSED", entity.getStatus());
        }

        @Test @DisplayName("no debe hacer nada cuando el evento no existe")
        void eventoNoExiste_noHaceNada() {
            // Arrange
            when(eventAuditRepository.findByEventId("evt-123"))
                    .thenReturn(Optional.empty());
            // Act
            service.updateStatus("evt-123", "FAILED");
            // Assert
            verify(eventAuditRepository, never()).save(any());
        }
    }
}
