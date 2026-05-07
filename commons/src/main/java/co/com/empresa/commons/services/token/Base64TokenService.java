package co.com.empresa.commons.services.token;


import co.com.empresa.commons.util.security.Base64TokenGenerator;

import lombok.AccessLevel;

import lombok.NoArgsConstructor;


/**
 * Servicio para la generación de tokens de autorización basados en codificación Base64.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Base64TokenService {
    /**
     * Genera un token de autorización basado en la entidad de servicio y la clase de servicio proporcionadas.
     *
     * @param entidadServicio la entidad de servicio para la cual se genera el token
     * @param claseServicio   el identificador de la clase de servicio
     * @return una cadena codificada en Base64 que representa el token de autorización
     */
    public static String getBase64Token(String entidadServicio, int claseServicio) {
        Base64TokenGenerator base64TokenGenerator = new Base64TokenGenerator(entidadServicio, String.valueOf(claseServicio));
        return base64TokenGenerator.base64Hash();
    }
}
