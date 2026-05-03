package co.com.empresa.domain.type;

import co.com.empresa.commons.exception.DomainException;
import co.com.empresa.domain.common.QueryAbstract;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Procesador de dominio para la obtención de una lista simplificada de tipos (Combo).
 * <p>
 * Utiliza el servicio de dominio para obtener los datos basados en el contexto de filtro.
 */
@Service
public class TypeGetComboProcessor extends QueryAbstract<GetTypesByTypeCategoryQuery, List<Type>> {

    private final ITypeService iTypeService;

    /**
     * Constructor para {@code TypeGetComboProcessor}.
     *
     * @param iTypeService servicio de dominio para la gestión de tipos
     */
    public TypeGetComboProcessor(ITypeService iTypeService) {
        this.iTypeService = iTypeService;
    }

    /**
     * Pre-procesamiento de la consulta de combo.
     *
     * @param context consulta de obtención de combo
     * @return la consulta validada
     * @throws DomainException si ocurre un error de negocio
     */
    @Override
    protected GetTypesByTypeCategoryQuery preProcess(GetTypesByTypeCategoryQuery context) throws DomainException {
        return context;
    }

    /**
     * Ejecuta la obtención de la lista de tipos simplificada.
     *
     * @param context consulta de obtención de combo
     * @return una lista de {@code Type} con la información solicitada
     * @throws DomainException si ocurre un error de negocio
     */
    @Override
    protected List<Type> process(GetTypesByTypeCategoryQuery context) throws DomainException {
        return iTypeService.getComboSencillo(context.context());
    }
}
