package co.com.empresa.commons.services.token;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Base64TokenService")
class Base64TokenServiceTest {

    @Test
    @DisplayName("debe generar token base64 valido")
    void getBase64Token_generaToken() {
        // Act
        String token = Base64TokenService.getBase64Token("miServicio", 1234);
        // Assert
        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    @DisplayName("debe generar tokens diferentes para diferentes parametros")
    void parametrosDiferentes_tokenDiferente() {
        // Act
        String t1 = Base64TokenService.getBase64Token("svcA", 111);
        String t2 = Base64TokenService.getBase64Token("svcB", 222);
        // Assert
        assertNotEquals(t1, t2);
    }
}
