package co.com.empresa.domain.common.events;

import java.time.LocalDateTime;

/**
 * Envoltorio genérico para eventos de dominio.
 * <p>
 * Proporciona metadatos estándar para el seguimiento y la trazabilidad de eventos.
 *
 * @param <T>           el tipo del payload del evento
 * @param eventId       identificador único del evento
 * @param eventType     tipo o nombre del evento
 * @param payload       datos específicos del evento
 * @param timestamp     fecha y hora de ocurrencia del evento
 * @param correlationId identificador para el rastreo de la transacción correlacionada
 */
public record EventEnvelope<T>(
        String eventId,
        String eventType,
        T payload,
        LocalDateTime timestamp,
        String correlationId
) {
    public EventEnvelope {
        if (eventId == null || eventId.isBlank()) {
            throw new IllegalArgumentException("eventId cannot be null or blank");
        }
    }
}
