package co.com.empresa.infrastructure.type;import co.com.empresa.domain.type.ITypeRepository;import co.com.empresa.domain.type.Type;import co.com.empresa.infrastructure.adapters.output.repositories.command.type.ITypeCommandJpaRepository;import co.com.empresa.infrastructure.adapters.output.repositories.query.type.ITypeQueryJpaRepository;import co.com.empresa.infrastructure.entities.type.TypeEntity;import org.springframework.data.domain.Example;import org.springframework.data.domain.Page;import org.springframework.data.domain.PageImpl;import org.springframework.data.domain.Pageable;import org.springframework.stereotype.Repository;import org.springframework.transaction.annotation.Transactional;import java.util.ArrayList;import java.util.Collection;import java.util.List;import java.util.Optional;/**
 * Infrastructure implementation of the {@link ITypeRepository}.
 * <p>
 * This class orchestrates data access for {@link Type} entities by delegating
 * read operations to {@link ITypeQueryJpaRepository} and write operations to {@link ITypeCommandJpaRepository}.
 * It uses {@link TypeInfrastructureMapper} to convert between domain models and JPA entities.
 */
@Repository
@Transactional
public class TypeRepositoryImpl implements ITypeRepository {
    private final ITypeQueryJpaRepository queryJpaRepository;    private final ITypeCommandJpaRepository commandJpaRepository;    private final TypeInfrastructureMapper mapper;    public TypeRepositoryImpl(ITypeQueryJpaRepository queryJpaRepository,                              ITypeCommandJpaRepository commandJpaRepository,                              TypeInfrastructureMapper mapper) {        this.queryJpaRepository = queryJpaRepository;        this.commandJpaRepository = commandJpaRepository;        this.mapper = mapper;    }        @Override
    public Page<Type> findAll(Example<Type> example, Pageable pageable) {
        TypeEntity probe = mapper.modelToEntity(example.getProbe());
        Example<TypeEntity> entityExample = Example.of(probe, example.getMatcher());
        Page<TypeEntity> page = queryJpaRepository.findAll(entityExample, pageable);
        List<Type> content = mapper.toModelList(page.getContent());
        return new PageImpl<>(content, pageable, page.getTotalElements());
    }

    @Override
    public List<Type> findAll(Example<Type> example) {
        TypeEntity probe = mapper.modelToEntity(example.getProbe());
        Example<TypeEntity> entityExample = Example.of(probe, example.getMatcher());
        return mapper.toModelList(queryJpaRepository.findAll(entityExample));
    }

    /**
     * Finds a type by its identifier.
     *
     * @param id the identifier of the type
     * @return an {@link Optional} containing the type if found, otherwise empty
     */
    @Override
    public Optional<Type> findById(Long id) {
        return queryJpaRepository.findById(id).map(mapper::entityToModel);
    }

    /**
     * Checks if a type exists with the given identifier.
     *
     * @param id the identifier of the type
     * @return {@code true} if the type exists, {@code false} otherwise
     */
    @Override
    public boolean existsById(Long id) {
        return queryJpaRepository.existsById(id);
    }

    /**
     * Retrieves the next value from the sequence.
     *
     * @return the next sequence value
     */
    @Override
    public Long getNextValSequence() {
        return queryJpaRepository.getNextValSequence();
    }

    /**
     * Saves a type entity.
     *
     * @param entity the type to save
     * @return the saved type
     */
    @Override
    public Type save(Type entity) {
        return mapper.entityToModel(commandJpaRepository.save(mapper.modelToEntity(entity)));
    }

    /**
     * Saves multiple type entities.
     *
     * @param entities the types to save
     * @return an {@link Iterable} of the saved types
     */
    @Override
    public Iterable<Type> saveAll(Iterable<Type> entities) {
        List<Type> list = new ArrayList<>();
        entities.forEach(list::add);
        return mapper.toModelList(commandJpaRepository.saveAll(mapper.toEntityList(list)));
    }

    /**
     * Updates a type entity.
     *
     * @param entity the type to update
     * @return the updated type
     */
    @Override
    public Type update(Type entity) {
        return mapper.entityToModel(commandJpaRepository.save(mapper.modelToEntity(entity)));
    }

    /**
     * Updates multiple type entities.
     *
     * @param entities the types to update
     * @return an {@link Iterable} of the updated types
     */
    @Override
    public Iterable<Type> updateAll(Iterable<Type> entities) {
        if (entities == null) {
            return List.of();
        }
        List<Type> list = new ArrayList<>();
        entities.forEach(list::add);
        return mapper.toModelList(commandJpaRepository.saveAll(mapper.toEntityList(list)));
    }

    /**
     * Deletes a type by its identifier.
     *
     * @param id the identifier of the type to delete
     */
    @Override
    public void delete(Long id) {
        commandJpaRepository.deleteById(id);
    }

    /**
     * Deletes multiple types by their identifiers.
     *
     * @param ids the identifiers of the types to delete
     */
    @Override
    public void deleteAll(Iterable<Long> ids) {
        if (ids == null) {
            return;
        }
        List<Long> idList;
        if (ids instanceof Collection) {
            idList = new ArrayList<>((Collection<Long>) ids);
        } else {
            idList = new ArrayList<>();
            for (Long id : ids) {
                idList.add(id);
            }
        }
        if (idList.isEmpty()) {
            return;
        }
        commandJpaRepository.deleteAllByIdInBatch(idList);
    }
}