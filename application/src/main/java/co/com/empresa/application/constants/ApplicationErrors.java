package co.com.empresa.application.constants;


import lombok.AccessLevel;

import lombok.NoArgsConstructor;


/**
 * Catálogo de claves de error de la capa de aplicación para validaciones de DTOs.
 * <p>
 * Estas claves se utilizan en las anotaciones de validación de Bean Validation
 * para proporcionar mensajes internacionalizados.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApplicationErrors {

    /**
     * Clave de validación: ID de categoría requerido.
     */
    public static final String VALIDATION_CATEGORY_ID_REQUIRED = "validation.category.id.required";

    /**
     * Clave de validación: Nombre de la categoría requerido.
     */
    public static final String VALIDATION_CATEGORY_NAME_REQUIRED = "validation.category.name.required";

    /**
     * Clave de validación: Tamaño del nombre de la categoría inválido.
     */
    public static final String VALIDATION_CATEGORY_NAME_SIZE = "validation.category.name.size";

    /**
     * Clave de validación: Código de la categoría requerido.
     */
    public static final String VALIDATION_CATEGORY_CODE_REQUIRED = "validation.category.code.required";

    /**
     * Clave de validación: Tamaño del código de la categoría inválido.
     */
    public static final String VALIDATION_CATEGORY_CODE_SIZE = "validation.category.code.size";

    /**
     * Clave de validación: Tamaño de la descripción de la categoría inválido.
     */
    public static final String VALIDATION_CATEGORY_DESCRIPTION_SIZE = "validation.category.description.size";


    /**
     * Clave de validación: ID del tipo requerido.
     */
    public static final String VALIDATION_TYPE_ID_REQUIRED = "validation.type.id.required";

    /**
     * Clave de validación: ID de la categoría del tipo requerido.
     */
    public static final String VALIDATION_TYPE_CATEGORY_ID_REQUIRED = "validation.type.categoryId.required";

    /**
     * Clave de validación: Nombre del tipo requerido.
     */
    public static final String VALIDATION_TYPE_NAME_REQUIRED = "validation.type.name.required";

    /**
     * Clave de validación: Tamaño del nombre del tipo inválido.
     */
    public static final String VALIDATION_TYPE_NAME_SIZE = "validation.type.name.size";

    /**
     * Clave de validación: Código del tipo requerido.
     */
    public static final String VALIDATION_TYPE_CODE_REQUIRED = "validation.type.code.required";

    /**
     * Clave de validación: Tamaño del código del tipo inválido.
     */
    public static final String VALIDATION_TYPE_CODE_SIZE = "validation.type.code.size";

    /**
     * Clave de validación: Tamaño de la descripción del tipo inválido.
     */
    public static final String VALIDATION_TYPE_DESCRIPTION_SIZE = "validation.type.description.size";
}




























