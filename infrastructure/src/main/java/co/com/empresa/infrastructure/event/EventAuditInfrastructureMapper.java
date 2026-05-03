package co.com.empresa.infrastructure.event;import co.com.empresa.commons.mapper.IGenericMapper;import co.com.empresa.domain.event.EventAudit;import co.com.empresa.infrastructure.entities.event.EventAuditEntity;import org.mapstruct.Mapper;/**
 * Mapper for converting between {@link EventAudit} domain models and {@link EventAuditEntity} JPA entities.
 */
@Mapper(componentModel = "spring")
public interface EventAuditInfrastructureMapper extends IGenericMapper<EventAudit, EventAuditEntity> {
    /**
     * Converts a domain model to a JPA entity.
     *
     * @param model the domain model to convert
     * @return the converted JPA entity
     */
    EventAuditEntity modelToEntity(EventAudit model);
}
