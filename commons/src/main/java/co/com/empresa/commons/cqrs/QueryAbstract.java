package co.com.empresa.commons.cqrs;


import co.com.empresa.commons.exception.DomainException;

import lombok.extern.slf4j.Slf4j;


/**
 * Clase abstracta base para todas las consultas del sistema.
 * <p>
 * Implementa el patrón Template Method para estandarizar el flujo de lectura.
 *
 * @param <T> el tipo del contexto de la consulta (filtro, id, etc.)
 * @param <R> el tipo del resultado de la consulta ({@code DTO})
 */
@Slf4j
public abstract class QueryAbstract<T, R> {

    /**
     * Metodo Plantilla: Ejecuta el flujo de consulta.
     *
     * @param context el contexto necesario para la consulta
     * @return el resultado de la consulta procesado
     * @throws DomainException si ocurre un error de negocio
     */
    public final R execute(T context) throws DomainException {
        try {
            // 1. Pre-procesamiento: Validaciones del contexto
            T validatedContext = preProcess(context);
            if (validatedContext == null) {
                log.warn("La consulta {} fue invalidada en el pre-procesamiento", this.getClass().getSimpleName());
                return null;
            }

            // 2. Procesamiento: Obtención y transformación de datos
            R result = process(validatedContext);

            // 3. Post-procesamiento: Formateo final o envoltorios
            return postProcess(result);

        } catch (DomainException e) {
            log.error("Error de negocio en la consulta {}: {}", this.getClass().getSimpleName(), e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado en la consulta {}: {}", this.getClass().getSimpleName(), e.getMessage(), e);
            throw new DomainException("Error interno al procesar la consulta");
        }
    }


    /**
     * Valida el contexto de la consulta.
     *
     * @param context contexto original
     * @return el contexto validado, o {@code null} si la consulta no debe proceder
     */
    protected abstract T preProcess(T context) throws DomainException;


    /**
     * Lógica principal de obtención de datos y mapeo a {@code DTO}.
     *
     * @param context contexto validado
     * @return el resultado procesado
     */
    protected abstract R process(T context) throws DomainException;


    /**
     * Hook para transformaciones finales del resultado.
     *
     * @param result resultado del proceso
     * @return resultado final
     */
    protected R postProcess(R result) {
        return result;
    }
}
