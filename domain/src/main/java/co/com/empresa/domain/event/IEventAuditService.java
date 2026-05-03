package co.com.empresa.domain.event;

import co.com.empresa.domain.common.events.EventEnvelope;

/**
 * Servicio de dominio para la gestión de auditoría de eventos.
 * <p>
 * Proporciona la lógica necesaria para registrar el ciclo de vida de los eventos
 * y validar la idempotencia mediante la detección de duplicados.
 */
public interface IEventAuditService {
    /**
     * Verifica si un evento ya ha sido procesado.
     *
     * @param eventId identificador único del evento
     * @return {@code true} si el evento es un duplicado, {@code false} en caso contrario
     */
    boolean isDuplicate(String eventId);

    /**
     * Registra la recepción de un evento externo.
     *
     * @param envelope envoltorio del evento recibido
     */
    void registerIncoming(EventEnvelope<?> envelope);

    /**
     * Registra la emisión de un evento hacia el exterior.
     *
     * @param envelope envoltorio del evento emitido
     */
    void registerOutgoing(EventEnvelope<?> envelope);

    /**
     * Actualiza el estado de procesamiento de un evento.
     *
     * @param eventId identificador único del evento
     * @param status  nuevo estado a asignar
     */
    void updateStatus(String eventId, String status);
}
