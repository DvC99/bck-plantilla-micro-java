package co.com.empresa.commons.audit;

import java.time.Instant;

/**
 * Contrato para entidades que requieren auditoria basica.
 */
public interface IAuditable {
    String getCreatedBy();

    Instant getCreatedAt();

    String getUpdatedBy();

    Instant getUpdatedAt();
}
