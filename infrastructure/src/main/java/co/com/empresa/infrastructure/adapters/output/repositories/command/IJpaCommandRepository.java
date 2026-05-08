package co.com.empresa.infrastructure.adapters.output.repositories.command;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Base marker interface for all command (write) JPA repositories.
 * <p>
 * Extending this interface ensures that the repository is picked up exclusively
 * by {@code CommandJpaConfig} and bound to the command {@code EntityManager} and
 * transaction manager. This enforces the CQRS write-side data source separation.
 *
 * <pre>{@code
 * @Repository
 * public interface ITypeCategoryCommandJpaRepository
 *         extends IJpaCommandRepository<TypeCategoryEntity, Long> {
 * }
 * }</pre>
 *
 * @param <E> the JPA entity type
 * @param <K> the primary key type
 */
@NoRepositoryBean
public interface IJpaCommandRepository<E, K> extends JpaRepository<E, K> {
}
