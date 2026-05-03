package co.com.empresa.commons.repository;


/**
 * Interfaz unificada que combina las capacidades de comando y consulta para un repositorio.
 *
 * @param <E> la entidad que maneja el repositorio
 * @param <K> el tipo del identificador de la entidad
 */
public interface IRepository<E, K> extends ICommandRepository<E, K>, IQueryRepository<E, K> {

}