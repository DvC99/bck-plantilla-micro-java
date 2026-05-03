package co.com.empresa.infrastructure.typecategory;import co.com.empresa.domain.typecategory.ITypeCategoryRepository;import co.com.empresa.domain.typecategory.TypeCategory;import co.com.empresa.infrastructure.adapters.output.repositories.command.typeCategory.ITypeCategoryCommandJpaRepository;import co.com.empresa.infrastructure.adapters.output.repositories.query.typeCategory.ITypeCategoryQueryJpaRepository;import co.com.empresa.infrastructure.entities.typeCategory.TypeCategoryEntity;import org.springframework.data.domain.Example;import org.springframework.data.domain.Page;import org.springframework.data.domain.PageImpl;import org.springframework.data.domain.Pageable;import org.springframework.stereotype.Repository;import org.springframework.transaction.annotation.Transactional;import java.util.ArrayList;import java.util.Collection;import java.util.List;import java.util.Optional;/**
 * Infrastructure implementation of the {@link ITypeCategoryRepository}.
 * <p>
 * This class orchestrates data access for {@link TypeCategory} entities by delegating
 * read operations to {@link ITypeCategoryQueryJpaRepository} and write operations to {@link ITypeCategoryCommandJpaRepository}.
 * It uses {@link TypeCategoryInfrastructureMapper} to convert between domain models and JPA entities.
 */
@Repository
@Transactional
public class TypeCategoryRepositoryImpl implements ITypeCategoryRepository {
    private final ITypeCategoryQueryJpaRepository queryJpaRepository;    private final ITypeCategoryCommandJpaRepository commandJpaRepository;    private final TypeCategoryInfrastructureMapper mapper;    public TypeCategoryRepositoryImpl(ITypeCategoryQueryJpaRepository queryJpaRepository,                                      ITypeCategoryCommandJpaRepository commandJpaRepository,                                      TypeCategoryInfrastructureMapper mapper) {        this.queryJpaRepository = queryJpaRepository;        this.commandJpaRepository = commandJpaRepository;        this.mapper = mapper;    }        @Override
    public Page<TypeCategory> findAll(Example<TypeCategory> example, Pageable pageable) {
        TypeCategoryEntity probe = mapper.modelToEntity(example.getProbe());
        Example<TypeCategoryEntity> entityExample = Example.of(probe, example.getMatcher());
        Page<TypeCategoryEntity> page = queryJpaRepository.findAll(entityExample, pageable);
        List<TypeCategory> content = mapper.toModelList(page.getContent());
        return new PageImpl<>(content, pageable, page.getTotalElements());
    }

    @Override
    public List<TypeCategory> findAll(Example<TypeCategory> example) {
        TypeCategoryEntity probe = mapper.modelToEntity(example.getProbe());
        Example<TypeCategoryEntity> entityExample = Example.of(probe, example.getMatcher());
        return mapper.toModelList(queryJpaRepository.findAll(entityExample));
    }

    /**
     * Finds a type category by its identifier.
     *
     * @param id the identifier of the type category
     * @return an {@link Optional} containing the type category if found, otherwise empty
     */
    @Override
    public Optional<TypeCategory> findById(Long id) {
        return queryJpaRepository.findById(id).map(mapper::entityToModel);
    }

    /**
     * Checks if a type category exists with the given identifier.
     *
     * @param id the identifier of the type category
     * @return {@code true} if the type category exists, {@code false} otherwise
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
     * Saves a type category entity.
     *
     * @param entity the type category to save
     * @return the saved type category
     */
    @Override
    public TypeCategory save(TypeCategory entity) {
        return mapper.entityToModel(commandJpaRepository.save(mapper.modelToEntity(entity)));
    }

    /**
     * Saves multiple type category entities.
     *
     * @param entities the type categories to save
     * @return an {@link Iterable} of the saved type categories
     */
    @Override
    public Iterable<TypeCategory> saveAll(Iterable<TypeCategory> entities) {
        List<TypeCategory> list = new ArrayList<>();
        entities.forEach(list::add);
        return mapper.toModelList(commandJpaRepository.saveAll(mapper.toEntityList(list)));
    }

    /**
     * Updates a type category entity.
     *
     * @param entity the type category to update
     * @return the updated type category
     */
    @Override
    public TypeCategory update(TypeCategory entity) {
        return mapper.entityToModel(commandJpaRepository.save(mapper.modelToEntity(entity)));
    }

    /**
     * Updates multiple type category entities.
     *
     * @param entities the type categories to update
     * @return an {@link Iterable} of the updated type categories
     */
    @Override
    public Iterable<TypeCategory> updateAll(Iterable<TypeCategory> entities) {
        if (entities == null) {
            return List.of();
        }
        List<TypeCategory> list = new ArrayList<>();
        entities.forEach(list::add);
        return mapper.toModelList(commandJpaRepository.saveAll(mapper.toEntityList(list)));
    }

    /**
     * Deletes a type category by its identifier.
     *
     * @param id the identifier of the type category to delete
     */
    @Override
    public void delete(Long id) {
        commandJpaRepository.deleteById(id);
    }

    /**
     * Deletes multiple type categories by their identifiers.
     *
     * @param ids the identifiers of the type categories to delete
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