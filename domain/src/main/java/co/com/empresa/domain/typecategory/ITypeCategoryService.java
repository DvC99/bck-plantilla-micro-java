package co.com.empresa.domain.typecategory;import co.com.empresa.commons.services.IGenericService;/**
 * Puerto de salida de servicios de dominio para {@code TypeCategory}.
 */
public interface ITypeCategoryService extends IGenericService<TypeCategory, Long> {
    /**
     * Valida que el código de la categoría sea único a nivel global.
     *
     * @param category la categoría a validar
     * @throws DomainException si el código ya existe en el sistema
     */
    void validateUniqueness(TypeCategory category);

    /**
     * Valida que la categoría no tenga dependencias (tipos asociados) antes de ser eliminada.
     *
     * @param category la categoría a validar
     * @throws DomainException si existen tipos asociados a la categoría
     */
    void validateNoDependencies(TypeCategory category);
}
