package co.com.empresa.domain.event;

import lombok.*;

import java.time.LocalDateTime;

/**
 * Modelo de dominio que representa la auditoría de un evento.
 * <p>
 * Se utiliza para registrar el flujo de entrada y salida de eventos,
 * permitiendo la trazabilidad y la detección de duplicados.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class EventAudit {
    /**
     * Identificador único del registro de auditoría.
     */
    private Long id;
    /**
     * Identificador único del evento original.
     */
    private String eventId;
    /**
     * Tipo o nombre del evento auditado.
     */
    private String eventType;
    /**
     * Dirección del flujo: {@code INCOMING} para entrada, {@code OUTGOING} para salida.
     */
    private String direction;
    /**
     * Representación textual del payload del evento.
     */
    private String payload;
    /**
     * Estado actual del procesamiento del evento.
     */
    private String status;
    /**
     * Fecha y hora de registro de la auditoría.
     */
    private LocalDateTime timestamp;
    /**
     * Identificador de correlación para rastrear la transacción.
     */
    private String correlationId;
}
