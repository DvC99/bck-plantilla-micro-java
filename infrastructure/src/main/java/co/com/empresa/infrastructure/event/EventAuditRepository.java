package co.com.empresa.infrastructure.event;

import co.com.empresa.infrastructure.entities.event.EventAuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * JPA repository for {@link EventAuditEntity}.
 * <p>
 * Provides standard CRUD operations and a custom method to find events by their unique event ID.
 */
public interface EventAuditRepository extends JpaRepository<EventAuditEntity, Long> {
    /**
     * Finds an audit record by the event identifier.
     *
     * @param eventId the unique identifier of the event
     * @return an {@link Optional} containing the audit record if found, otherwise empty
     */
    Optional<EventAuditEntity> findByEventId(String eventId);
}
