package co.com.empresa.infrastructure.adapters.output.repositories.command.event;import co.com.empresa.infrastructure.entities.event.EventAuditEntity;import org.springframework.data.jpa.repository.JpaRepository;import org.springframework.stereotype.Repository;/**
 * JPA repository for performing command (write) operations on {@link EventAuditEntity}.
 */
@Repository
public interface IEventAuditCommandJpaRepository extends JpaRepository<EventAuditEntity, Long> {
}
