package co.com.empresa.commons.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;

import lombok.Builder;

import lombok.Data;

import lombok.NoArgsConstructor;


import java.io.Serializable;

import java.util.List;


/**
 * Objeto de respuesta unificado para las comunicaciones de la API.
 * <p>
 * Esta clase maneja tanto respuestas simples (un único objeto) como respuestas
 * paginadas (una lista con metadatos de conteo y totales).
 *
 * @param <T> el tipo de datos contenidos en la respuesta
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponse<T> implements Serializable {

    /**
     * Indica si la operación fue exitosa.
     */
    private boolean ok;

    /**
     * El código de estado de la respuesta (típicamente un código de estado HTTP).
     */
    private Integer codigo;

    /**
     * Mensaje que proporciona información adicional sobre el resultado de la respuesta.
     */
    private String mensaje;

    /**
     * Objeto de datos único para respuestas simples.
     */
    private T dato;

    /**
     * Lista de elementos de datos para respuestas de colección o paginadas.
     */
    private List<T> datos;

    /**
     * El conteo total de elementos en la respuesta (utilizado en respuestas paginadas).
     */
    private Integer conteo;

    /**
     * Representación textual de los totales o información de resumen (utilizado en respuestas paginadas).
     */
    private String totales;


    /**
     * Crea una respuesta exitosa con un único objeto de datos.
     *
     * @param codigo  código de estado HTTP
     * @param mensaje mensaje de la respuesta
     * @param dato    el objeto de datos
     * @param <T>     tipo de dato
     * @return instancia de {@code GenericResponse}
     */
    public static <T> GenericResponse<T> success(Integer codigo, String mensaje, T dato) {
        return GenericResponse.<T>builder()
                .ok(true)
                .codigo(codigo)
                .mensaje(mensaje)
                .dato(dato)
                .build();
    }


    /**
     * Crea una respuesta exitosa con una lista de datos.
     *
     * @param codigo  código de estado HTTP
     * @param mensaje mensaje de la respuesta
     * @param datos   lista de elementos de datos
     * @param <T>     tipo de dato
     * @return instancia de {@code GenericResponse}
     */
    public static <T> GenericResponse<T> success(Integer codigo, String mensaje, List<T> datos) {
        return GenericResponse.<T>builder()
                .ok(true)
                .codigo(codigo)
                .mensaje(mensaje)
                .datos(datos)
                .conteo(datos != null ? datos.size() : 0)
                .build();
    }


    /**
     * Crea una respuesta exitosa paginada con metadatos detallados.
     *
     * @param codigo  código de estado HTTP
     * @param mensaje mensaje de la respuesta
     * @param datos   lista de elementos de datos
     * @param conteo  conteo total de registros
     * @param totales información de resumen de totales
     * @param <T>     tipo de dato
     * @return instancia de {@code GenericResponse}
     */
    public static <T> GenericResponse<T> successPaginated(
            Integer codigo,
            String mensaje,
            List<T> datos,
            Integer conteo,
            String totales) {
        return GenericResponse.<T>builder()
                .ok(true)
                .codigo(codigo)
                .mensaje(mensaje)
                .datos(datos)
                .conteo(conteo)
                .totales(totales)
                .build();
    }


    /**
     * Crea una respuesta de error.
     *
     * @param codigo  código de estado HTTP
     * @param mensaje mensaje de error
     * @param <T>     tipo de dato
     * @return instancia de {@code GenericResponse}
     */
    public static <T> GenericResponse<T> error(Integer codigo, String mensaje) {
        return GenericResponse.<T>builder()
                .ok(false)
                .codigo(codigo)
                .mensaje(mensaje)
                .build();
    }

}
