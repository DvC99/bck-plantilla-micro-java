package co.com.empresa.commons.exception;


/**
 * Excepción personalizada para errores específicos del dominio.
 * <p>
 * Esta excepción representa la violación de reglas de negocio o invariantes del dominio.
 */
public class DomainException extends RuntimeException {


    /**
     * Construye una nueva {@code DomainException} con el mensaje detallado especificado.
     *
     * @param message el mensaje de detalle
     */
    public DomainException(String message) {

        super(message);

    }


    /**
     * Construye una nueva {@code DomainException} con el mensaje detallado y la causa especificada.
     *
     * @param message el mensaje de detalle
     * @param cause   la causa de la excepción
     */
    public DomainException(String message, Throwable cause) {

        super(message, cause);

    }

}