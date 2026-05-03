package co.com.empresa.domain.event;


import co.com.empresa.commons.repository.ICommandRepository;

import co.com.empresa.commons.repository.IQueryRepository;


/**
 * Puerto de salida unificado para operaciones de {@code EventAudit}.
 * <p>
 * Combina las capacidades de comando y consulta para la gestión de auditorías de eventos.
 */
public interface IEventAuditRepository extends ICommandRepository<EventAudit, Long>, IQueryRepository<EventAudit, Long> {

}