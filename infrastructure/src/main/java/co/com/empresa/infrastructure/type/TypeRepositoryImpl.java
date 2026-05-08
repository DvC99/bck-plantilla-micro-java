package co.com.empresa.infrastructure.type;

import co.com.empresa.domain.type.ITypeRepository;
import co.com.empresa.domain.type.Type;
import co.com.empresa.infrastructure.adapters.output.repositories.command.type.ITypeCommandJpaRepository;
import co.com.empresa.infrastructure.adapters.output.repositories.query.type.ITypeQueryJpaRepository;
import co.com.empresa.infrastructure.common.AbstractRepositoryImpl;
import co.com.empresa.infrastructure.entities.type.TypeEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Infrastructure implementation of {@link ITypeRepository}.
 * <p>
 * Delegates all standard CRUD and query operations to {@link AbstractRepositoryImpl}.
 * Only the sequence lookup requires a specific override.
 */
@Repository
@Transactional
public class TypeRepositoryImpl
        extends AbstractRepositoryImpl<Type, TypeEntity, Long>
        implements ITypeRepository {

    private final ITypeQueryJpaRepository queryJpaRepository;

    /**
     * Constructs a {@code TypeRepositoryImpl} with the required JPA repositories and mapper.
     *
     * @param commandJpaRepository the JPA repository for write operations
     * @param queryJpaRepository   the JPA repository for read operations
     * @param mapper               the mapper between {@link Type} and {@link TypeEntity}
     */
    public TypeRepositoryImpl(ITypeCommandJpaRepository commandJpaRepository,
                               ITypeQueryJpaRepository queryJpaRepository,
                               TypeInfrastructureMapper mapper) {
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
