package co.com.empresa.infrastructure.adapters.output.repositories.query.typeCategory;

import co.com.empresa.infrastructure.adapters.output.repositories.query.IJpaQueryRepository;
import co.com.empresa.infrastructure.entities.typeCategory.TypeCategoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * JPA query repository for {@link TypeCategoryEntity}.
 * <p>
 * Bound to the query {@code EntityManager} via {@code QueryJpaConfig}.
 * All read operations are routed through this repository.
 */
@Repository
public interface ITypeCategoryQueryJpaRepository extends IJpaQueryRepository<TypeCategoryEntity, Long> {

    /**
     * Retrieves the next value from the {@code type_category_seq} database sequence.
     *
     * @return the next sequence value
     */
    @Query(value = "SELECT nextval('type_category_seq')", nativeQuery = true)
    Long getNextValSequence();
}
