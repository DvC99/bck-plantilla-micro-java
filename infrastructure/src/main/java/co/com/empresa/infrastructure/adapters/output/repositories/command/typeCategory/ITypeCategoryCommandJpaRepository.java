package co.com.empresa.infrastructure.adapters.output.repositories.command.typeCategory;

import co.com.empresa.infrastructure.adapters.output.repositories.command.IJpaCommandRepository;
import co.com.empresa.infrastructure.entities.typeCategory.TypeCategoryEntity;
import org.springframework.stereotype.Repository;

/**
 * JPA command repository for {@link TypeCategoryEntity}.
 * <p>
 * Bound to the command {@code EntityManager} via {@code CommandJpaConfig}.
 * All write operations (save, update, delete) are routed through this repository.
 */
@Repository
public interface ITypeCategoryCommandJpaRepository extends IJpaCommandRepository<TypeCategoryEntity, Long> {
}
