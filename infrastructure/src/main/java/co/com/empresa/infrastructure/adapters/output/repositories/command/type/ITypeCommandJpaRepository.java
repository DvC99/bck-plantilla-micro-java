package co.com.empresa.infrastructure.adapters.output.repositories.command.type;

import co.com.empresa.infrastructure.adapters.output.repositories.command.IJpaCommandRepository;
import co.com.empresa.infrastructure.entities.type.TypeEntity;
import org.springframework.stereotype.Repository;

/**
 * JPA command repository for {@link TypeEntity}.
 * <p>
 * Bound to the command {@code EntityManager} via {@code CommandJpaConfig}.
 * All write operations (save, update, delete) are routed through this repository.
 */
@Repository
public interface ITypeCommandJpaRepository extends IJpaCommandRepository<TypeEntity, Long> {
}
