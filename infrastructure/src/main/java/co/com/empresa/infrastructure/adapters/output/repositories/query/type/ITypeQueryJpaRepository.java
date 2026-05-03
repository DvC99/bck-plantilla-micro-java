package co.com.empresa.infrastructure.adapters.output.repositories.query.type;import co.com.empresa.infrastructure.entities.type.TypeEntity;import org.springframework.data.jpa.repository.JpaRepository;import org.springframework.data.jpa.repository.Query;import org.springframework.stereotype.Repository;/**
 * JPA repository for performing query operations on {@link TypeEntity}.
 */
@Repository
public interface ITypeQueryJpaRepository extends JpaRepository<TypeEntity, Long> {
    /**
     * Retrieves the next value from the database sequence.
     *
     * @return the next sequence value
     */
    @Query(value = "SELECT nextval('type_seq')", nativeQuery = true)
    Long getNextValSequence();
}
