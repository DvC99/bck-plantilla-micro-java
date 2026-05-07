package co.com.empresa.domain.type;

import co.com.empresa.commons.exception.DomainException;
import co.com.empresa.commons.cqrs.CommandProcessAbstract;
import org.springframework.stereotype.Service;

/**
 * Procesador de dominio encargado de la actualización de tipos.
 * <p>
 * Valida la existencia del registro, la integridad de los datos y la unicidad
 * del código antes de actualizar la entidad.
 */
@Service
public class TypeUpdateProcessor extends CommandProcessAbstract<TypeUpdateCommand, Type> {

    private final ITypeRepository typeRepository;
    private final ITypeService iTypeService;

    /**
     * Constructor para {@code TypeUpdateProcessor}.
     *
     * @param typeRepository repositorio de tipos
     * @param iTypeService   servicio de dominio para validaciones de tipo
     */
    public TypeUpdateProcessor(ITypeRepository typeRepository, ITypeService iTypeService) {
        this.typeRepository = typeRepository;
        this.iTypeService = iTypeService;
    }

    /**
     * Realiza las validaciones previas a la actualización del tipo.
     *
     * @param command comando de actualización
     * @return el comando si es válido, {@code null} en caso contrario
     * @throws DomainException si alguna validación de negocio falla
     */
    @Override
    protected TypeUpdateCommand preProcess(TypeUpdateCommand command) throws DomainException {
        Type type = command.context();
        if (type == null || type.getId() == null) {
            return null;
        }
        type.validate();
        iTypeService.validateCategoryExists(type.getTypeCategoryId());
        iTypeService.validateUniqueness(type);
        return command;
    }

    /**
     * Ejecuta la actualización del tipo en la base de datos.
     *
     * @param command comando de actualización
     * @return el tipo actualizado
     * @throws DomainException si ocurre un error durante la actualización
     */
    @Override
    protected Type process(TypeUpdateCommand command) throws DomainException {
        return typeRepository.update(command.context());
    }
}
