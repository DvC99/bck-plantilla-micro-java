package co.com.empresa.infrastructure.event;import co.com.empresa.domain.event.EventAudit;import co.com.empresa.domain.event.IEventAuditRepository;import co.com.empresa.infrastructure.adapters.output.repositories.command.event.IEventAuditCommandJpaRepository;import co.com.empresa.infrastructure.adapters.output.repositories.query.event.IEventAuditQueryJpaRepository;import co.com.empresa.infrastructure.entities.event.EventAuditEntity;import org.springframework.data.domain.Example;import org.springframework.data.domain.Page;import org.springframework.data.domain.PageImpl;import org.springframework.data.domain.Pageable;import org.springframework.stereotype.Repository;import org.springframework.transaction.annotation.Transactional;import java.util.ArrayList;import java.util.Collection;import java.util.List;import java.util.Optional;/**
 * Infrastructure implementation of the {@link IEventAuditRepository}.
 * <p>
 * This class orchestrates data access for {@link EventAudit} entities by delegating
 * read operations to {@link IEventAuditQueryJpaRepository} and write operations to {@link IEventAuditCommandJpaRepository}.
 * It uses {@link EventAuditInfrastructureMapper} to convert between domain models and JPA entities.
 */
@Repository
@Transactional
public class EventAuditRepositoryImpl implements IEventAuditRepository {
    private final IEventAuditQueryJpaRepository queryJpaRepository;    private final IEventAuditCommandJpaRepository commandJpaRepository;    private final EventAuditInfrastructureMapper mapper;    public EventAuditRepositoryImpl(IEventAuditQueryJpaRepository queryJpaRepository,                                    IEventAuditCommandJpaRepository commandJpaRepository,                                    EventAuditInfrastructureMapper mapper) {        this.queryJpaRepository = queryJpaRepository;        this.commandJpaRepository = commandJpaRepository;        this.mapper = mapper;    }        @Override
    public Page<EventAudit> findAll(Example<EventAudit> example, Pageable pageable) {
        EventAuditEntity probe = mapper.modelToEntity(example.getProbe());
        Example<EventAuditEntity> entityExample = Example.of(probe, example.getMatcher());
        Page<EventAuditEntity> page = queryJpaRepository.findAll(entityExample, pageable);
        List<EventAudit> content = mapper.toModelList(page.getContent());
        return new PageImpl<>(content, pageable, page.getTotalElements());
    }

    @Override
    public List<EventAudit> findAll(Example<EventAudit> example) {
        EventAuditEntity probe = mapper.modelToEntity(example.getProbe());
        Example<EventAuditEntity> entityExample = Example.of(probe, example.getMatcher());
        return mapper.toModelList(queryJpaRepository.findAll(entityExample));
    }

    /**
     * Finds an event audit record by its identifier.
     *
     * @param id the identifier of the audit record
     * @return an {@link Optional} containing the event audit if found, otherwise empty
     */
    @Override
    public Optional<EventAudit> findById(Long id) {
        return queryJpaRepository.findById(id).map(mapper::entityToModel);
    }

    /**
     * Checks if an event audit record exists with the given identifier.
     *
     * @param id the identifier of the audit record
     * @return {@code true} if the record exists, {@code false} otherwise
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
     * Saves an event audit entity.
     *
     * @param entity the event audit to save
     * @return the saved event audit
     */
    @Override
    public EventAudit save(EventAudit entity) {
        return mapper.entityToModel(commandJpaRepository.save(mapper.modelToEntity(entity)));
    }

    /**
     * Saves multiple event audit entities.
     *
     * @param entities the event audits to save
     * @return an {@link Iterable} of the saved event audits
     */
    @Override
    public Iterable<EventAudit> saveAll(Iterable<EventAudit> entities) {
        List<EventAudit> list = new ArrayList<>();
        entities.forEach(list::add);
        return mapper.toModelList(commandJpaRepository.saveAll(mapper.toEntityList(list)));
    }

    /**
     * Updates an event audit entity.
     *
     * @param entity the event audit to update
     * @return the updated event audit
     */
    @Override
    public EventAudit update(EventAudit entity) {
        return mapper.entityToModel(commandJpaRepository.save(mapper.modelToEntity(entity)));
    }

    /**
     * Updates multiple event audit entities.
     *
     * @param entities the event audits to update
     * @return an {@link Iterable} of the updated event audits
     */
    @Override
    public Iterable<EventAudit> updateAll(Iterable<EventAudit> entities) {
        if (entities == null) {
            return List.of();
        }
        List<EventAudit> list = new ArrayList<>();
        entities.forEach(list::add);
        return mapper.toModelList(commandJpaRepository.saveAll(mapper.toEntityList(list)));
    }

    /**
     * Deletes an event audit record by its identifier.
     *
     * @param id the identifier of the audit record to delete
     */
    @Override
    public void delete(Long id) {
        commandJpaRepository.deleteById(id);
    }

    /**
     * Deletes multiple event audit records by their identifiers.
     *
     * @param ids the identifiers of the audit records to delete
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