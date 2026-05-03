package co.com.empresa.infrastructure.event.messaging.kafka;

import co.com.empresa.domain.common.events.EventEnvelope;
import co.com.empresa.domain.event.IEventAuditService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Aspect for intercepting Kafka event listeners.
 * <p>
 * This aspect provides cross-cutting concerns for events processed by {@code @KafkaListener},
 * such as idempotency checks (duplicate detection) and event auditing.
 */
@Aspect
@Component
public class KafkaEventListenerAspect {
    private final IEventAuditService eventAuditService;

    public KafkaEventListenerAspect(IEventAuditService eventAuditService) {
        this.eventAuditService = eventAuditService;
    }

    /**
     * Intercepts methods annotated with {@code @KafkaListener}.
     * <p>
     * If the event is an {@link EventEnvelope}, it checks for duplicates and registers
     * the incoming event in the audit log. The audit status is updated to "PROCESSED"
     * upon successful execution or "FAILED" if an exception occurs.
     *
     * @param joinPoint the proceeding join point
     * @return the result of the listener execution, or {@code null} if the event is a duplicate
     * @throws Throwable any exception thrown by the listener
     */
    @Around("@annotation(org.springframework.kafka.annotation.KafkaListener)")
    public Object interceptKafkaEvent(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        EventEnvelope<?> envelope = null;

        for (Object arg : args) {
            if (arg instanceof EventEnvelope) {
                envelope = (EventEnvelope<?>) arg;
                break;
            }
        }

        if (envelope == null) {
            return joinPoint.proceed();
        }

        if (eventAuditService.isDuplicate(envelope.eventId())) {
            return null; // Evento duplicado, ignorar
        }

        eventAuditService.registerIncoming(envelope);
        try {
            Object result = joinPoint.proceed();
            eventAuditService.updateStatus(envelope.eventId(), "PROCESSED");
            return result;
        } catch (Exception e) {
            eventAuditService.updateStatus(envelope.eventId(), "FAILED");
            throw e;
        }
    }
}
