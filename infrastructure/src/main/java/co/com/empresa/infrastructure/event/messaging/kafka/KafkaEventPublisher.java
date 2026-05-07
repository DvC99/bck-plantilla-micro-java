package co.com.empresa.infrastructure.event.messaging.kafka;

import co.com.empresa.commons.exception.InfrastructureException;
import co.com.empresa.domain.common.events.EventEnvelope;
import co.com.empresa.domain.common.ports.IEventPublisher;
import co.com.empresa.domain.event.IEventAuditService;
import co.com.empresa.infrastructure.constants.InfrastructureErrors;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Kafka implementation of the {@link IEventPublisher}.
 * <p>
 * This class is responsible for publishing events to Kafka topics and recording
 * the outgoing event in the audit log.
 */
@Component
@ConditionalOnProperty(name = "app.messaging.kafka.enabled", havingValue = "true", matchIfMissing = true)
public class KafkaEventPublisher implements IEventPublisher {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final IEventAuditService eventAuditService;
    private final ObjectMapper objectMapper;

    public KafkaEventPublisher(KafkaTemplate<String, String> kafkaTemplate, IEventAuditService eventAuditService, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.eventAuditService = eventAuditService;
        this.objectMapper = objectMapper;
    }

    /**
     * Publishes an event to Kafka.
     * <p>
     * The event is first registered in the audit log as "SENT". If publishing fails,
     * the audit status is updated to "FAILED".
     *
     * @param event the event envelope to publish
     * @throws InfrastructureException if the event cannot be serialized or published to Kafka
     */
    @Override
    public void publish(EventEnvelope<?> event) {
        eventAuditService.registerOutgoing(event);
        try {
            String payload = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(event.eventType(), event.eventId(), payload);
        } catch (JsonProcessingException e) {
            eventAuditService.updateStatus(event.eventId(), "FAILED");
            throw new InfrastructureException(InfrastructureErrors.ERROR_KAFKA_SERIALIZATION, e);
        } catch (Exception e) {
            eventAuditService.updateStatus(event.eventId(), "FAILED");
            throw new InfrastructureException(InfrastructureErrors.ERROR_KAFKA_PUBLISH, e);
        }
    }
}
