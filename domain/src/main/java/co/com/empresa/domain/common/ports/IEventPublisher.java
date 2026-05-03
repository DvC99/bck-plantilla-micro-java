package co.com.empresa.domain.common.ports;

import co.com.empresa.domain.common.events.EventEnvelope;

/**
 * Puerto de salida para la publicación de eventos de dominio.
 * <p>
 * Define el contrato para enviar eventos a un bus de mensajería o sistema externo.
 */
public interface IEventPublisher {
    /**
     * Publica un evento de dominio.
     *
     * @param event el envoltorio del evento a publicar
     */
    void publish(EventEnvelope<?> event);
}
