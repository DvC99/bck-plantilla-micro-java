package co.com.empresa.infrastructure.adapters.output.repositories.query.event;import co.com.empresa.infrastructure.entities.event.EventAuditEntity;import org.springframework.data.jpa.repository.JpaRepository;import org.springframework.data.jpa.repository.Query;import org.springframework.stereotype.Repository;import java.util.Optional;/**
 * JPA repository for performing query operations on {@link EventAuditEntity}.
 */
@Repository
public interface IEventAuditQueryJpaRepository extends JpaRepository<EventAuditEntity, Long> {
    /**
     * Retrieves the next value from the database sequence.
     *
     * @return the next sequence value
     */
    @Query(value = "SELECT nextval('seq_event_audit_id')", nativeQuery = true)
    Long getNextValSequence();

    /**
     * Finds an audit record by the event identifier.
     *
     * @param eventId the unique identifier of the event
     * @return an {@link Optional} containing the audit record if found, otherwise empty
     */
    Optional<EventAuditEntity> findByEventId(String eventId);
}
