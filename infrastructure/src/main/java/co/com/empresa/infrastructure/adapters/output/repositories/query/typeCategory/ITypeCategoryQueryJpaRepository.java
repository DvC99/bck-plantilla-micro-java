package co.com.empresa.infrastructure.adapters.output.repositories.query.typeCategory;import co.com.empresa.infrastructure.entities.typeCategory.TypeCategoryEntity;import org.springframework.data.jpa.repository.JpaRepository;import org.springframework.data.jpa.repository.Query;import org.springframework.stereotype.Repository;/**
 * JPA repository for performing query operations on {@link TypeCategoryEntity}.
 */
@Repository
public interface ITypeCategoryQueryJpaRepository extends JpaRepository<TypeCategoryEntity, Long> {
    /**
     * Retrieves the next value from the database sequence.
     *
     * @return the next sequence value
     */
    @Query(value = "SELECT nextval('type_category_seq')", nativeQuery = true)
    Long getNextValSequence();
}
