package co.com.empresa.domain.common.events;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("EventEnvelope")
class EventEnvelopeTest {

    private final EasyRandom easyRandom = new EasyRandom();

    @Test
    @DisplayName("debe crear envelope con todos los campos correctamente")
    void crear_envelope_valido() {
        // Arrange
        String eventId = "EV-" + easyRandom.nextObject(String.class);
        String eventType = easyRandom.nextObject(String.class);
        String payload = easyRandom.nextObject(String.class);
        LocalDateTime timestamp = LocalDateTime.now();
        String correlationId = easyRandom.nextObject(String.class);
        // Act
        var envelope = new EventEnvelope<>(eventId, eventType, payload, timestamp, correlationId);
        // Assert
        assertEquals(eventId, envelope.eventId());
        assertEquals(eventType, envelope.eventType());
        assertEquals(payload, envelope.payload());
        assertEquals(timestamp, envelope.timestamp());
        assertEquals(correlationId, envelope.correlationId());
    }

    @Test
    @DisplayName("debe lanzar IllegalArgumentException cuando eventId es null")
    void eventId_nulo_lanza() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                new EventEnvelope<>(null, "type", "payload", LocalDateTime.now(), "corrId"));
    }

    @Test
    @DisplayName("debe lanzar IllegalArgumentException cuando eventId esta vacio")
    void eventId_vacio_lanza() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                new EventEnvelope<>("", "type", "payload", LocalDateTime.now(), "corrId"));
    }

    @Test
    @DisplayName("debe lanzar IllegalArgumentException cuando eventId tiene solo espacios")
    void eventId_blanco_lanza() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                new EventEnvelope<>("   ", "type", "payload", LocalDateTime.now(), "corrId"));
    }
}
