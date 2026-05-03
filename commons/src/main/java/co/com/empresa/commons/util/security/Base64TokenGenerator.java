package co.com.empresa.commons.util.security;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Clase especializada en la generación y validación de tokens de seguridad basados en Base64.
 * <p>
 * Permite crear hashes únicos basados en la entidad y la aplicación, facilitando la
 * validación de solicitudes externas.
 */
@Slf4j
@Getter
@Setter
public class Base64TokenGenerator {
    /**
     * Entidad asociada al token.
     */
    private String entidad;
    /**
     * Código de servicio asociado al token.
     */
    private String codigoServicio;
    /**
     * Resultado del cálculo del hash.
     */
    private int hashCodeResult;

    /**
     * Constructor para {@code Base64TokenGenerator}.
     *
     * @param entidad        la entidad asociada con el token
     * @param codigoServicio el código de servicio asociado con el token
     */
    public Base64TokenGenerator(String entidad, String codigoServicio) {
        this.entidad = entidad;
        this.codigoServicio = codigoServicio;
    }

    /**
     * Genera un código hash basado en la entidad y el código de servicio.
     *
     * @return un entero que representa el código hash del objeto
     */
    @Override
    public int hashCode() {
        int prime = (getEntidad() != null && getEntidad().matches("\\d+"))
                ? Integer.parseInt(getEntidad())
                : 31;
        hashCodeResult = 1;
        setCodigoServicio("11");
        hashCodeResult = prime * hashCodeResult
                + ((getCodigoServicio() == null) ? 0
                : getCodigoServicio().hashCode());
        hashCodeResult = prime * hashCodeResult
                + ((getEntidad() == null) ? 0 : getEntidad().hashCode());
        return hashCodeResult;
    }

    /**
     * Compara este generador con otro objeto para determinar la igualdad basada en sus atributos.
     *
     * @param obj el objeto a comparar
     * @return {@code true} si los objetos son iguales, {@code false} en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Base64TokenGenerator other = (Base64TokenGenerator) obj;
        if (getCodigoServicio() == null) {
            if (other.getCodigoServicio() != null)
                return false;
        } else if (!getCodigoServicio().equals(other.getCodigoServicio()))
            return false;
        if (getEntidad() == null) {
            return other.getEntidad() == null;
        } else return getEntidad().equals(other.getEntidad());
    }

    /**
     * Genera la cadena de token final codificada en Base64.
     *
     * @return la cadena del token en Base64, o {@code null} si ocurre un error
     */
    public String base64Hash() {
        Cripto cri = new Cripto();
        String b64 = null;
        try {
            b64 = cri.encodeBase64(String.valueOf(hashCode()));
        } catch (Exception e) {
            log.error("Error al generar el hash Base64", e);
        }
        return b64;
    }

    /**
     * Valida un token externo comparándolo con el hash generado localmente.
     *
     * @param tokenValidar el token a validar
     * @return {@code true} si el token coincide, {@code false} en caso contrario
     */
    public boolean validarToken(String tokenValidar) {
        try {
            if (base64Hash().equals(tokenValidar))
                return true;
        } catch (Exception e) {
            log.error("Error al validar el token", e);
        }
        return false;
    }

    /**
     * Método estático para generar un token rápidamente a partir de un payload.
     *
     * @param payload la cadena de texto a tokenizar
     * @return la cadena del token resultante
     */
    public static String generateToken(String payload) {
        Base64TokenGenerator generator = new Base64TokenGenerator(payload, "default");
        return generator.base64Hash();
    }

    /**
     * Método estático para decodificar un token.
     *
     * @param token el token a decodificar
     * @return la cadena decodificada
     */
    public static String decodeToken(String token) {
        return token;
    }
}

