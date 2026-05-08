package co.com.empresa.infrastructure.adapters.output.repositories.query.type;

import co.com.empresa.infrastructure.adapters.output.repositories.query.IJpaQueryRepository;
import co.com.empresa.infrastructure.entities.type.TypeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * JPA query repository for {@link TypeEntity}.
 * <p>
 * Bound to the query {@code EntityManager} via {@code QueryJpaConfig}.
 * All read operations are routed through this repository.
 */
@Repository
public interface ITypeQueryJpaRepository extends IJpaQueryRepository<TypeEntity, Long> {

    /**
     * Retrieves the next value from the {@code type_seq} database sequence.
     *
     * @return the next sequence value
     */
    @Query(value = "SELECT nextval('type_seq')", nativeQuery = true)
    Long getNextValSequence();
}
