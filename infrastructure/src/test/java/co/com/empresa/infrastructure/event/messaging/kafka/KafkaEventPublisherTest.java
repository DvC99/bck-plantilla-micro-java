package co.com.empresa.infrastructure.event.messaging.kafka;

import co.com.empresa.commons.exception.InfrastructureException;
import co.com.empresa.domain.common.events.EventEnvelope;
import co.com.empresa.domain.event.IEventAuditService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("KafkaEventPublisher")
class KafkaEventPublisherTest {

    @Mock private KafkaTemplate<String, String> kafkaTemplate;
    @Mock private IEventAuditService eventAuditService;
    @Mock private ObjectMapper objectMapper;
    @InjectMocks private KafkaEventPublisher publisher;

    private EventEnvelope<String> envelope;

    @BeforeEach
    void setUp() {
        envelope = new EventEnvelope<>("evt-1", "test.topic", "payload", LocalDateTime.now(), "corr-1");
    }

    @Nested @DisplayName("publish")
    class Publish {
        @Test @DisplayName("debe registrar salida, serializar y enviar a Kafka")
        void flujoNormal_envia() throws Exception {
            // Arrange
            String json = "{\"eventId\":\"evt-1\"}";
            when(objectMapper.writeValueAsString(envelope)).thenReturn(json);
            when(kafkaTemplate.send("test.topic", "evt-1", json))
                    .thenReturn(CompletableFuture.completedFuture(mock(SendResult.class)));
            // Act
            publisher.publish(envelope);
            // Assert
            verify(eventAuditService).registerOutgoing(envelope);
            verify(kafkaTemplate).send("test.topic", "evt-1", json);
        }

        @Test @DisplayName("debe fallar si la serializacion lanza JsonProcessingException")
        void serializacionFalla_registraFailed() throws Exception {
            // Arrange
            when(objectMapper.writeValueAsString(any()))
                    .thenThrow(JsonProcessingException.class);
            // Act & Assert
            assertThrows(InfrastructureException.class, () -> publisher.publish(envelope));
            verify(eventAuditService).registerOutgoing(envelope);
            verify(eventAuditService).updateStatus("evt-1", "FAILED");
        }

        @Test @DisplayName("debe fallar si envio a Kafka lanza excepcion generica")
        void envioFalla_registraFailed() throws Exception {
            // Arrange
            String json = "{\"eventId\":\"evt-1\"}";
            when(objectMapper.writeValueAsString(envelope)).thenReturn(json);
            when(kafkaTemplate.send(anyString(), anyString(), anyString()))
                    .thenThrow(new RuntimeException("broker down"));
            // Act & Assert
            assertThrows(InfrastructureException.class, () -> publisher.publish(envelope));
            verify(eventAuditService).updateStatus("evt-1", "FAILED");
        }
    }
}
