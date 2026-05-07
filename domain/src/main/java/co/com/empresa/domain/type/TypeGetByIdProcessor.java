package co.com.empresa.domain.type;

import co.com.empresa.commons.exception.DomainException;
import co.com.empresa.commons.cqrs.QueryAbstract;
import org.springframework.stereotype.Service;

/**
 * Procesador de dominio para la obtención de un tipo mediante su identificador.
 * <p>
 * Coordina la búsqueda en el repositorio y gestiona el error en caso de que el tipo no exista.
 */
@Service
public class TypeGetByIdProcessor extends QueryAbstract<GetTypeByIdQuery, Type> {

    private final ITypeRepository typeRepository;

    /**
     * Constructor para {@code TypeGetByIdProcessor}.
     *
     * @param typeRepository repositorio de tipos
     */
    public TypeGetByIdProcessor(ITypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    /**
     * Valida que el identificador de la consulta no sea nulo.
     *
     * @param context consulta de obtención por ID
     * @return la consulta si es válida, {@code null} en caso contrario
     * @throws DomainException si ocurre un error de negocio
     */
    @Override
    protected GetTypeByIdQuery preProcess(GetTypeByIdQuery context) throws DomainException {
        if (context == null || context.id() == null) {
            return null;
        }
        return context;
    }

    /**
     * Ejecuta la búsqueda del tipo en el repositorio.
     *
     * @param context consulta de obtención por ID
     * @return el objeto {@code Type} encontrado
     * @throws DomainException si no se encuentra ningún tipo con el ID proporcionado
     */
    @Override
    protected Type process(GetTypeByIdQuery context) throws DomainException {
        return typeRepository.findById(context.id())
                .orElseThrow(() -> new DomainException("Type not found with id: " + context.id()));
    }
}
