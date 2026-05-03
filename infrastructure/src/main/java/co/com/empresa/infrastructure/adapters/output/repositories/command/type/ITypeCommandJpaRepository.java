package co.com.empresa.infrastructure.adapters.output.repositories.command.type;import co.com.empresa.infrastructure.entities.type.TypeEntity;import org.springframework.data.jpa.repository.JpaRepository;import org.springframework.stereotype.Repository;/**
 * JPA repository for performing command (write) operations on {@link TypeEntity}.
 */
@Repository
public interface ITypeCommandJpaRepository extends JpaRepository<TypeEntity, Long> {
}
