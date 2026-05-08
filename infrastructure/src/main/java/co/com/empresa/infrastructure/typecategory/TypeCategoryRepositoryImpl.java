package co.com.empresa.infrastructure.typecategory;

import co.com.empresa.domain.typecategory.ITypeCategoryRepository;
import co.com.empresa.domain.typecategory.TypeCategory;
import co.com.empresa.infrastructure.adapters.output.repositories.command.typeCategory.ITypeCategoryCommandJpaRepository;
import co.com.empresa.infrastructure.adapters.output.repositories.query.typeCategory.ITypeCategoryQueryJpaRepository;
import co.com.empresa.infrastructure.common.AbstractRepositoryImpl;
import co.com.empresa.infrastructure.entities.typeCategory.TypeCategoryEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Infrastructure implementation of {@link ITypeCategoryRepository}.
 * <p>
 * Delegates all standard CRUD and query operations to {@link AbstractRepositoryImpl}.
 * Only the sequence lookup requires a specific override.
 */
@Repository
@Transactional
public class TypeCategoryRepositoryImpl
        extends AbstractRepositoryImpl<TypeCategory, TypeCategoryEntity, Long>
        implements ITypeCategoryRepository {

    private final ITypeCategoryQueryJpaRepository queryJpaRepository;

    /**
     * Constructs a {@code TypeCategoryRepositoryImpl} with the required JPA repositories and mapper.
     *
     * @param commandJpaRepository the JPA repository for write operations
     * @param queryJpaRepository   the JPA repository for read operations
     * @param mapper               the mapper between {@link TypeCategory} and {@link TypeCategoryEntity}
     */
    public TypeCategoryRepositoryImpl(ITypeCategoryCommandJpaRepository commandJpaRepository,
                                      ITypeCategoryQueryJpaRepository queryJpaRepository,
                                      TypeCategoryInfrastructureMapper mapper) {
        super(commandJpaRepository, queryJpaRepository, mapper);
        this.queryJpaRepository = queryJpaRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getNextValSequence() {
        return queryJpaRepository.getNextValSequence();
    }
}
