package co.com.empresa.domain.type;

import co.com.empresa.commons.exception.DomainException;
import co.com.empresa.commons.cqrs.CommandProcessAbstract;
import org.springframework.stereotype.Service;

/**
 * Procesador de dominio encargado de la eliminación de tipos.
 * <p>
 * Verifica que el identificador sea válido antes de proceder con la eliminación.
 */
@Service
public class TypeDeleteProcessor extends CommandProcessAbstract<TypeDeleteCommand, Type> {

    private final ITypeRepository typeRepository;

    /**
     * Constructor para {@code TypeDeleteProcessor}.
     *
     * @param typeRepository repositorio de tipos
     */
    public TypeDeleteProcessor(ITypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    /**
     * Realiza las validaciones previas a la eliminación del tipo.
     *
     * @param command comando de eliminación
     * @return el comando si es válido, {@code null} en caso contrario
     * @throws DomainException si ocurre un error de negocio
     */
    @Override
    protected TypeDeleteCommand preProcess(TypeDeleteCommand command) throws DomainException {
        if (command.context() == null || command.context().getId() == null) {
            return null;
        }
        return command;
    }

    /**
     * Ejecuta la eliminación del tipo.
     *
     * @param command comando de eliminación
     * @return el objeto {@code Type} que fue eliminado
     * @throws DomainException si ocurre un error durante la eliminación
     */
    @Override
    protected Type process(TypeDeleteCommand command) throws DomainException {
        typeRepository.delete(command.context().getId());
        return command.context();
    }
}
