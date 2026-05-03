package co.com.empresa.infrastructure.adapters.output.repositories.command.typeCategory;import co.com.empresa.infrastructure.entities.typeCategory.TypeCategoryEntity;import org.springframework.data.jpa.repository.JpaRepository;import org.springframework.stereotype.Repository;/**
 * JPA repository for performing command (write) operations on {@link TypeCategoryEntity}.
 */
@Repository
public interface ITypeCategoryCommandJpaRepository extends JpaRepository<TypeCategoryEntity, Long> {
}
