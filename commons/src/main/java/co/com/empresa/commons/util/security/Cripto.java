package co.com.empresa.commons.util.security;


import lombok.Getter;

import lombok.Setter;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.codec.binary.Base64;


/**
 * Clase de utilidad para la codificación y decodificación de cadenas utilizando el estándar Base64.
 */
@Slf4j
@Getter
@Setter
public class Cripto {

    /**
     * Instancia del codificador Base64.
     */
    private Base64 base64;

    /**
     * Versión codificada de la cadena.
     */
    private String encodedVersion;

    /**
     * Versión decodificada de la cadena.
     */
    private String decodedVersion;


    /**
     * Construye una nueva instancia de {@code Cripto} e inicializa el codificador Base64.
     */
    public Cripto() {
        setBase64(new Base64());
    }


    /**
     * Codifica una cadena de texto en formato Base64.
     *
     * @param cadena la cadena de texto a codificar
     * @return la cadena codificada en Base64
     * @throws Exception si ocurre un error durante la codificación
     */
    public String encodeBase64(String cadena) throws Exception {
        setEncodedVersion(new String(base64.encode(cadena.getBytes())));
        return encodedVersion;
    }


    /**
     * Decodifica una cadena de texto desde formato Base64.
     *
     * @param cadena la cadena codificada en Base64 a decodificar
     * @return la cadena decodificada
     * @throws Exception si ocurre un error durante la decodificación
     */
    public String decodeBAse64(String cadena) throws Exception {
        setDecodedVersion(new String(base64.decode(cadena.getBytes())));
        return decodedVersion;
    }


    /**
     * Encripta una cadena de texto utilizando Base64 de forma estática.
     *
     * @param text el texto a encriptar
     * @return la cadena encriptada
     * @throws RuntimeException si ocurre un error durante el proceso de encriptación
     */
    public static String encrypt(String text) {
        try {
            Cripto cri = new Cripto();
            return cri.encodeBase64(text);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting", e);
        }
    }


    /**
     * Desencripta una cadena de texto desde Base64 de forma estática.
     *
     * @param encryptedText la cadena encriptada a desencriptar
     * @return el texto desencriptado
     * @throws IllegalArgumentException si el texto encriptado es nulo o vacío
     * @throws RuntimeException         si ocurre un error durante el proceso de desencriptación
     */
    public static String decrypt(String encryptedText) {
        if (encryptedText == null || encryptedText.trim().isEmpty()) {
            throw new IllegalArgumentException("Encrypted text cannot be null or empty");
        }
        try {
            Cripto cri = new Cripto();
            return cri.decodeBAse64(encryptedText);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting", e);
        }
    }
}


















