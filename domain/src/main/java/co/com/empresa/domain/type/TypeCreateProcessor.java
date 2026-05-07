package co.com.empresa.domain.type;

import co.com.empresa.commons.exception.DomainException;
import co.com.empresa.commons.cqrs.CommandProcessAbstract;
import org.springframework.stereotype.Service;

/**
 * Procesador de dominio encargado de la creación de tipos.
 * <p>
 * Valida la integridad del objeto, la existencia de la categoría y la unicidad
 * del código antes de persistir la entidad.
 */
@Service
public class TypeCreateProcessor extends CommandProcessAbstract<TypeCreateCommand, Type> {

    private final ITypeRepository typeRepository;
    private final ITypeService iTypeService;

    /**
     * Constructor para {@code TypeCreateProcessor}.
     *
     * @param typeRepository repositorio de tipos
     * @param iTypeService   servicio de dominio para validaciones de tipo
     */
    public TypeCreateProcessor(ITypeRepository typeRepository, ITypeService iTypeService) {
        this.typeRepository = typeRepository;
        this.iTypeService = iTypeService;
    }

    /**
     * Realiza las validaciones previas a la creación del tipo.
     *
     * @param command comando de creación
     * @return el comando si es válido, {@code null} en caso contrario
     * @throws DomainException si alguna validación de negocio falla
     */
    @Override
    protected TypeCreateCommand preProcess(TypeCreateCommand command) throws DomainException {
        Type type = command.context();
        if (type == null) {
            return null;
        }
        type.validate();
        iTypeService.validateCategoryExists(type.getTypeCategoryId());
        iTypeService.validateUniqueness(type);
        return command;
    }

    /**
     * Ejecuta la persistencia del nuevo tipo.
     *
     * @param command comando de creación
     * @return el tipo persistido
     * @throws DomainException si ocurre un error durante la persistencia
     */
    @Override
    protected Type process(TypeCreateCommand command) throws DomainException {
        return typeRepository.save(command.context());
    }
}
