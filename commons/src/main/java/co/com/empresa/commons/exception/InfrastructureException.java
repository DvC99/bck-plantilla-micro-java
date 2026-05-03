package co.com.empresa.commons.exception;


/**
 * Excepción personalizada para errores relacionados con la infraestructura.
 * <p>
 * Esta excepción se utiliza para capturar fallos en la capa de persistencia,
 * comunicación con servicios externos o configuraciones del entorno.
 */
public class InfrastructureException extends RuntimeException {


    /**
     * Construye una nueva {@code InfrastructureException} sin mensaje ni causa.
     */
    public InfrastructureException() {

        super();

    }


    /**
     * Construye una nueva {@code InfrastructureException} con el mensaje detallado especificado.
     *
     * @param message el mensaje de detalle
     */
    public InfrastructureException(String message) {

        super(message);

    }


    /**
     * Construye una nueva {@code InfrastructureException} con la causa especificada.
     *
     * @param cause la causa de la excepción
     */
    public InfrastructureException(Throwable cause) {

        super(cause);

    }


    /**
     * Construye una nueva {@code InfrastructureException} con el mensaje detallado y la causa especificada.
     *
     * @param message el mensaje de detalle
     * @param cause   la causa de la excepción
     */
    public InfrastructureException(String message, Throwable cause) {

        super(message, cause);

    }

}