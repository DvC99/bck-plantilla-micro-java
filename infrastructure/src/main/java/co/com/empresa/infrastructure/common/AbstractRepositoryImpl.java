package co.com.empresa.infrastructure.common;

import co.com.empresa.commons.mapper.IGenericMapper;
import co.com.empresa.commons.repository.IRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Abstract base implementation of {@link IRepository} that eliminates boilerplate
 * for infrastructure repository adapters.
 * <p>
 * Subclasses only need to provide the command JPA repository, the query JPA repository,
 * and the infrastructure mapper. All standard CRUD and query operations are handled here.
 *
 * <pre>{@code
 * @Repository
 * @Transactional
 * public class TypeCategoryRepositoryImpl
 *         extends AbstractRepositoryImpl<TypeCategory, TypeCategoryEntity, Long>
 *         implements ITypeCategoryRepository {
 *
 *     public TypeCategoryRepositoryImpl(
 *             ITypeCategoryCommandJpaRepository commandJpaRepository,
 *             ITypeCategoryQueryJpaRepository queryJpaRepository,
 *             TypeCategoryInfrastructureMapper mapper) {
 *         super(commandJpaRepository, queryJpaRepository, mapper);
 *     }
 * }
 * }</pre>
 *
 * @param <M> the domain model type
 * @param <E> the JPA entity type
 * @param <K> the primary key type
 */
@Transactional
public abstract class AbstractRepositoryImpl<M, E, K> implements IRepository<M, K> {

    private final JpaRepository<E, K> commandJpaRepository;
    private final JpaRepository<E, K> queryJpaRepository;
    private final IGenericMapper<M, E> mapper;

    /**
     * Constructs an {@code AbstractRepositoryImpl} with the required JPA repositories and mapper.
     *
     * @param commandJpaRepository the JPA repository for write operations
     * @param queryJpaRepository   the JPA repository for read operations
     * @param mapper               the mapper between domain model and JPA entity
     */
    protected AbstractRepositoryImpl(JpaRepository<E, K> commandJpaRepository,
                                     JpaRepository<E, K> queryJpaRepository,
                                     IGenericMapper<M, E> mapper) {
        this.commandJpaRepository = commandJpaRepository;
        this.queryJpaRepository = queryJpaRepository;
        this.mapper = mapper;
    }

    /**
     * Returns the next sequence value for ID generation.
     * <p>
     * Override this method in subclasses to delegate to the specific
     * {@code getNextValSequence()} method of the query JPA repository.
     *
     * @return the next sequence value
     */
    @Override
    public K getNextValSequence() {
        throw new UnsupportedOperationException(
                "getNextValSequence() must be overridden in " + getClass().getSimpleName());
    }

    @Override
    public Page<M> findAll(Example<M> example, Pageable pageable) {
        E probe = mapper.modelToEntity(example.getProbe());
        Example<E> entityExample = Example.of(probe, example.getMatcher());
        Page<E> page = queryJpaRepository.findAll(entityExample, pageable);
        return new PageImpl<>(mapper.toModelList(page.getContent()), pageable, page.getTotalElements());
    }

    @Override
    public List<M> findAll(Example<M> example) {
        E probe = mapper.modelToEntity(example.getProbe());
        Example<E> entityExample = Example.of(probe, example.getMatcher());
        return mapper.toModelList(queryJpaRepository.findAll(entityExample));
    }

    @Override
    public Optional<M> findById(K id) {
        return queryJpaRepository.findById(id).map(mapper::entityToModel);
    }

    @Override
    public boolean existsById(K id) {
        return queryJpaRepository.existsById(id);
    }

    @Override
    public M save(M entity) {
        return mapper.entityToModel(commandJpaRepository.save(mapper.modelToEntity(entity)));
    }

    @Override
    public Iterable<M> saveAll(Iterable<M> entities) {
        List<M> list = toList(entities);
        return mapper.toModelList(commandJpaRepository.saveAll(mapper.toEntityList(list)));
    }

    @Override
    public M update(M entity) {
        return mapper.entityToModel(commandJpaRepository.save(mapper.modelToEntity(entity)));
    }

    @Override
    public Iterable<M> updateAll(Iterable<M> entities) {
        if (entities == null) {
            return List.of();
        }
        List<M> list = toList(entities);
        return mapper.toModelList(commandJpaRepository.saveAll(mapper.toEntityList(list)));
    }

    @Override
    public void delete(K id) {
        commandJpaRepository.deleteById(id);
    }

    @Override
    public void deleteAll(Iterable<K> ids) {
        if (ids == null) {
            return;
        }
        List<K> idList = toList(ids);
        if (idList.isEmpty()) {
            return;
        }
        commandJpaRepository.deleteAllByIdInBatch(idList);
    }

    /**
     * Converts an {@link Iterable} to a {@link List}, reusing the existing list if possible.
     *
     * @param iterable the iterable to convert
     * @param <T>      the element type
     * @return a list containing all elements
     */
    private <T> List<T> toList(Iterable<T> iterable) {
        if (iterable instanceof Collection<T> col) {
            return new ArrayList<>(col);
        }
        List<T> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }
}
