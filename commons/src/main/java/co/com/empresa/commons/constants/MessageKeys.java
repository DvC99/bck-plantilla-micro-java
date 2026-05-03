package co.com.empresa.commons.constants;


import lombok.AccessLevel;

import lombok.NoArgsConstructor;


/**
 * Constantes para las claves de mensajes internacionalizados.
 * <p>
 * Estas claves se utilizan para obtener mensajes desde los archivos de propiedades
 * gestionados por el {@code MessageService}.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MessageKeys {


    // ========================================

    // MENSAJES DE EXITO

    // ========================================

    /**
     * Clave para el mensaje de operación exitosa.
     */
    public static final String SUCCESS_OPERATION = "success.operation";

    /**
     * Clave para el mensaje de registro creado exitosamente.
     */
    public static final String SUCCESS_CREATED = "success.created";

    /**
     * Clave para el mensaje de respuesta sin contenido.
     */
    public static final String SUCCESS_NO_CONTENT = "success.no.content";

    /**
     * Clave para el mensaje de respuesta paginada exitosa.
     */
    public static final String SUCCESS_PAGINATED = "success.paginated";

    /**
     * Clave para el mensaje de no se encontraron resultados.
     */
    public static final String SUCCESS_NO_RESULTS = "success.no.results";

    /**
     * Clave para la información de paginación.
     */
    public static final String SUCCESS_PAGE_INFO = "success.page.info";


    // ========================================

    // ERRORES GENERALES

    // ========================================

    /**
     * Clave para el error de servidor interno.
     */
    public static final String ERROR_INTERNAL_SERVER = "error.internal.server";

    /**
     * Clave para el error de solicitud incorrecta.
     */
    public static final String ERROR_BAD_REQUEST = "error.bad.request";

    /**
     * Clave para el error de recurso no encontrado.
     */
    public static final String ERROR_NOT_FOUND = "error.not.found";

    /**
     * Clave para el error de no autorizado.
     */
    public static final String ERROR_UNAUTHORIZED = "error.unauthorized";

    /**
     * Clave para el error de prohibido.
     */
    public static final String ERROR_FORBIDDEN = "error.forbidden";

    /**
     * Clave para el error de puntero nulo.
     */
    public static final String ERROR_NULL_POINTER = "error.null.pointer";


    // ========================================

    // ERRORES DE VALIDACION

    // ========================================

    /**
     * Prefijo para los errores de validación.
     */
    public static final String ERROR_VALIDATION_PREFIX = "error.validation.prefix";

    /**
     * Clave para la violación de restricción de validación.
     */
    public static final String ERROR_CONSTRAINT_VIOLATION = "error.constraint.violation";

    /**
     * Clave para el argumento ilegal.
     */
    public static final String ERROR_ILLEGAL_ARGUMENT = "error.illegal.argument";

    /**
     * Clave para el error de discordancia de tipo.
     */
    public static final String ERROR_TYPE_MISMATCH = "error.type.mismatch";

    /**
     * Clave para el error de JSON inválido.
     */
    public static final String ERROR_JSON_INVALID = "error.json.invalid";

    /**
     * Clave para el método no soportado.
     */
    public static final String ERROR_METHOD_NOT_SUPPORTED = "error.method.not.supported";

    /**
     * Clave para el tipo de medio no soportado.
     */
    public static final String ERROR_MEDIA_TYPE_NOT_SUPPORTED = "error.media.type.not.supported";

    /**
     * Clave para el parámetro faltante.
     */
    public static final String ERROR_PARAMETER_MISSING = "error.parameter.missing";

    /**
     * Clave para el endpoint no encontrado.
     */
    public static final String ERROR_ENDPOINT_NOT_FOUND = "error.endpoint.not.found";


    // ========================================

    // ERRORES DE BASE DE DATOS

    // ========================================

    /**
     * Clave para el error de integridad de datos.
     */
    public static final String ERROR_DATA_INTEGRITY = "error.data.integrity";

    /**
     * Clave para la violación de restricción de llave foránea.
     */
    public static final String ERROR_FK_CONSTRAINT = "error.fk.constraint";


    // ========================================

    // ERRORES DE DOMINIO (existentes)

    // ========================================

    /**
     * Clave para el error de valor de enumeración inválido.
     */
    public static final String ERROR_DOMAIN_VALID_ENUM = "error.domain.valid.enum";

    /**
     * Clave para el error de ID vacío.
     */
    public static final String ERROR_DOMAIN_VALID_ID_EMPTY = "error.domain.valid.id.empty";

    /**
     * Clave para el error de contexto nulo.
     */
    public static final String ERROR_DOMAIN_VALID_CONTEXTO_NULL = "error.domain.valid.contexto.null";

    /**
     * Clave para el error de creación vacía.
     */
    public static final String ERROR_DOMAIN_VALID_CREATE_EMPTY = "error.domain.valid.create.empty";

    /**
     * Clave para el error de actualización vacía.
     */
    public static final String ERROR_DOMAIN_VALID_UPDATE_EMPTY = "error.domain.valid.update.empty";


    // ========================================

    // ERRORES DE INFRAESTRUCTURA (existentes)

    // ========================================

    /**
     * Clave para el error de no se encontró registro por ID.
     */
    public static final String ERROR_INFRASTRUCTURE_NO_REGISTRO_BY_ID = "error.infrastructure.no.registro.by.id";
}
