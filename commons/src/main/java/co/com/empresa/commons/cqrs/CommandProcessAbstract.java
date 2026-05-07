package co.com.empresa.commons.cqrs;

import co.com.empresa.commons.exception.DomainException;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

/**
 * Clase abstracta que representa un proceso de comando ejecutado de forma stateless.
 * Define el esqueleto del proceso: Pre-procesamiento -> Procesamiento -> Post-procesamiento.
 *
 * @param <C> El tipo del objeto de contexto utilizado en el proceso de comando.
 * @param <R> El tipo del resultado devuelto por el proceso de comando.
 */
@Slf4j
public abstract class CommandProcessAbstract<C, R> {

    /**
     * Ejecuta el proceso del comando de forma stateless.
     *
     * @param context El contexto necesario para la ejecución.
     * @return El resultado de la ejecución.
     * @throws DomainException si ocurre un error en el proceso.
     */
    public R execute(C context) throws DomainException {
        try {
            // 1. Pre-procesamiento: Valida el contexto y devuelve el contexto validado (o null si no es válido)
            C validatedContext = preProcess(context);

            if (validatedContext == null) {
                log.warn("El proceso de comando {} fue invalidado en el pre-procesamiento", this.getClass().getSimpleName());
                return null;
            }

            // 2. Procesamiento: Ejecuta la lógica principal
            R result = process(validatedContext);

            // 3. Post-procesamiento: Realiza acciones finales sobre el resultado
            return postProcess(result);

        } catch (DomainException | ParseException e) {
            log.error("Error en el proceso de comando {}: {}", this.getClass().getSimpleName(), e.getMessage(), e);
            throw new DomainException(e.getMessage());
        }
    }

    /**
     * Realiza validaciones y preparaciones previas.
     *
     * @param context Contexto original.
     * @return El contexto validado, o null si el proceso no debe continuar.
     */
    protected abstract C preProcess(C context) throws DomainException, ParseException;

    /**
     * Ejecuta la lógica principal del comando.
     *
     * @param context Contexto validado.
     * @return El resultado del procesamiento.
     */
    protected abstract R process(C context) throws DomainException;

    /**
     * Realiza acciones finales sobre el resultado obtenido.
     *
     * @param result Resultado del procesamiento.
     * @return El resultado final procesado.
     */
    protected R postProcess(R result) {
        return result; // Por defecto devuelve el resultado sin cambios
    }
}
