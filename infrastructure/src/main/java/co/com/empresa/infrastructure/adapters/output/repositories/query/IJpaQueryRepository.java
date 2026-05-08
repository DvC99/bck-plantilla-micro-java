package co.com.empresa.infrastructure.adapters.output.repositories.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Base marker interface for all query (read) JPA repositories.
 * <p>
 * Extending this interface ensures that the repository is picked up exclusively
 * by {@code QueryJpaConfig} and bound to the query {@code EntityManager} and
 * transaction manager. This enforces the CQRS read-side data source separation.
 * <p>
 * Query repositories typically also declare a {@code getNextValSequence()} method
 * annotated with {@code @Query(nativeQuery = true)} pointing to the entity's sequence.
 *
 * <pre>{@code
 * @Repository
 * public interface ITypeCategoryQueryJpaRepository
 *         extends IJpaQueryRepository<TypeCategoryEntity, Long> {
 *
 *     @Query(value = "SELECT nextval('type_category_seq')", nativeQuery = true)
 *     Long getNextValSequence();
 * }
 * }</pre>
 *
 * @param <E> the JPA entity type
 * @param <K> the primary key type
 */
@NoRepositoryBean
public interface IJpaQueryRepository<E, K> extends JpaRepository<E, K> {
}
