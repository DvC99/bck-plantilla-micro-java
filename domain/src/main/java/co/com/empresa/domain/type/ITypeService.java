package co.com.empresa.domain.type;


import co.com.empresa.commons.exception.DomainException;
import co.com.empresa.commons.services.IGenericService;


/**
 * Puerto de salida de servicios de dominio para {@code Type}.
 */
public interface ITypeService extends IGenericService<Type, Long> {
    /**
     * Valida que el código del tipo sea único dentro de su categoría.
     *
     * @param type el objeto {@code Type} a validar
     * @throws DomainException si el código ya existe para la categoría asociada
     */
    void validateUniqueness(Type type);

    /**
     * Valida que la categoría especificada exista en el sistema.
     *
     * @param categoryId identificador de la categoría a validar
     * @throws DomainException si la categoría no es encontrada
     */
    void validateCategoryExists(Long categoryId);
}
