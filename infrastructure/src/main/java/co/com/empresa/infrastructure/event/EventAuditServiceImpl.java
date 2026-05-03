package co.com.empresa.infrastructure.event;

import co.com.empresa.commons.exception.InfrastructureException;
import co.com.empresa.domain.common.events.EventEnvelope;
import co.com.empresa.domain.event.IEventAuditService;
import co.com.empresa.infrastructure.constants.InfrastructureErrors;
import co.com.empresa.infrastructure.entities.event.EventAuditEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Infrastructure implementation of the {@link IEventAuditService}.
 * <p>
 * This service manages the auditing of Kafka events, recording both incoming and outgoing
 * events to provide traceability and idempotency checks.
 */
@Service
public class EventAuditServiceImpl implements IEventAuditService {
    private final EventAuditRepository eventAuditRepository;
    private final ObjectMapper objectMapper;

    public EventAuditServiceImpl(EventAuditRepository eventAuditRepository, ObjectMapper objectMapper) {
        this.eventAuditRepository = eventAuditRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * Checks if an event has already been processed based on its identifier.
     *
     * @param eventId the unique identifier of the event
     * @return {@code true} if the event was already processed, {@code false} otherwise
     */
    @Override
    public boolean isDuplicate(String eventId) {
        return eventAuditRepository.findByEventId(eventId).isPresent();
    }

    /**
     * Registers an incoming event in the audit log.
     *
     * @param envelope the event envelope containing the event data
     * @throws InfrastructureException if the event payload cannot be serialized to JSON
     */
    @Override
    public void registerIncoming(EventEnvelope<?> envelope) {
        try {
            String payload = objectMapper.writeValueAsString(envelope.payload());
            EventAuditEntity entity = EventAuditEntity.builder()
                    .eventId(envelope.eventId())
                    .eventType(envelope.eventType())
                    .direction("INCOMING")
                    .payload(payload)
                    .status("RECEIVED")
                    .timestamp(envelope.timestamp())
                    .correlationId(envelope.correlationId())
                    .build();
            eventAuditRepository.save(entity);
        } catch (JsonProcessingException e) {
            throw new InfrastructureException(InfrastructureErrors.ERROR_KAFKA_SERIALIZATION, e);
        }
    }

    /**
     * Registers an outgoing event in the audit log.
     *
     * @param envelope the event envelope containing the event data
     * @throws InfrastructureException if the event payload cannot be serialized to JSON
     */
    @Override
    public void registerOutgoing(EventEnvelope<?> envelope) {
        try {
            String payload = objectMapper.writeValueAsString(envelope.payload());
            EventAuditEntity entity = EventAuditEntity.builder()
                    .eventId(envelope.eventId())
                    .eventType(envelope.eventType())
                    .direction("OUTGOING")
                    .payload(payload)
                    .status("SENT")
                    .timestamp(envelope.timestamp())
                    .correlationId(envelope.correlationId())
                    .build();
            eventAuditRepository.save(entity);
        } catch (JsonProcessingException e) {
            throw new InfrastructureException(InfrastructureErrors.ERROR_KAFKA_SERIALIZATION, e);
        }
    }

    /**
     * Updates the processing status of an audited event.
     *
     * @param eventId the unique identifier of the event
     * @param status  the new status to set
     */
    @Override
    public void updateStatus(String eventId, String status) {
        Optional<EventAuditEntity> auditOpt = eventAuditRepository.findByEventId(eventId);
        auditOpt.ifPresent(entity -> {
            entity.setStatus(status);
            eventAuditRepository.save(entity);
        });
    }

}
