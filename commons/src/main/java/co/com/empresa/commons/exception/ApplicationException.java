package co.com.empresa.commons.exception;


/**
 * Excepción específica de la capa de aplicación.
 * <p>
 * Se utiliza para manejar errores de orquestación y fallos lógicos en la capa de aplicación.
 */
public class ApplicationException extends RuntimeException {
    /**
     * Construye una nueva {@code ApplicationException} con el mensaje detallado especificado.
     *
     * @param message el mensaje de detalle
     */
    public ApplicationException(String message) {
        super(message);
    }

    /**
     * Construye una nueva {@code ApplicationException} con el mensaje detallado y la causa especificada.
     *
     * @param message el mensaje de detalle
     * @param cause   la causa de la excepción
     */
    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}