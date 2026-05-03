package co.com.empresa.infrastructure.entities.event;

import co.com.empresa.infrastructure.constants.EntitiesConstants;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * JPA entity representing an event audit log.
 * <p>
 * This entity tracks the exchange of events between microservices for auditing and traceability purposes.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = EntitiesConstants.TABLE_EVENT_AUDIT)
public class EventAuditEntity {
    /**
     * Unique identifier of the audit record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "eventAuditSeqGenerator")
    @SequenceGenerator(name = "eventAuditSeqGenerator", sequenceName = EntitiesConstants.SEQ_EVENT_AUDIT_ID, allocationSize = 1)
    @Column(name = EntitiesConstants.COL_ID, nullable = false)
    private Long id;

    /**
     * Unique identifier of the event.
     */
    @Column(name = "event_id", nullable = false, unique = true)
    private String eventId;

    /**
     * Type of the event.
     */
    @Column(name = "event_type", nullable = false)
    private String eventType;

    /**
     * Direction of the event (e.g., INBOUND, OUTBOUND).
     */
    @Column(name = "direction", nullable = false)
    private String direction;

    /**
     * Payload of the event in JSON format.
     */
    @Column(name = "payload", columnDefinition = "TEXT")
    private String payload;

    /**
     * Processing status of the event.
     */
    @Column(name = "status", nullable = false)
    private String status;

    /**
     * Timestamp when the event occurred.
     */
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    /**
     * Correlation ID to track the event across different services.
     */
    @Column(name = "correlation_id")
    private String correlationId;
}
