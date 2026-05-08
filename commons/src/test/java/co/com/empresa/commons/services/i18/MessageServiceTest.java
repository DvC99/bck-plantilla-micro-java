package co.com.empresa.commons.services.i18;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("MessageService")
class MessageServiceTest {

    @Mock private MessageSource messageSource;
    @InjectMocks private MessageService service;
    private EasyRandom easyRandom;

    @BeforeEach
    void setUp() {
        easyRandom = new EasyRandom();
    }

    @Nested @DisplayName("getMessage(key)")
    class GetMessageKey {
        @Test @DisplayName("debe retornar mensaje traducido usando el locale del contexto")
        void getMessage_clave_devuelveTraduccion() {
            // Arrange
            String key = easyRandom.nextObject(String.class);
            String translated = easyRandom.nextObject(String.class);
            when(messageSource.getMessage(eq(key), isNull(), any(Locale.class))).thenReturn(translated);
            // Act
            String r = service.getMessage(key);
            // Assert
            assertEquals(translated, r);
        }

        @Test @DisplayName("debe retornar la clave cuando falla la traduccion")
        void getMessage_fallo_devuelveClave() {
            // Arrange
            String key = easyRandom.nextObject(String.class);
            when(messageSource.getMessage(eq(key), isNull(), any(Locale.class)))
                    .thenThrow(new RuntimeException("no message"));
            // Act
            String r = service.getMessage(key);
            // Assert
            assertEquals(key, r);
        }
    }

    @Nested @DisplayName("getMessage(key, params)")
    class GetMessageKeyParams {
        @Test @DisplayName("debe retornar mensaje con parametros")
        void getMessage_params_devuelveTraduccion() {
            // Arrange
            String key = easyRandom.nextObject(String.class);
            String translated = easyRandom.nextObject(String.class);
            when(messageSource.getMessage(eq(key), any(Object[].class), any(Locale.class))).thenReturn(translated);
            // Act
            String r = service.getMessage(key, "param1", "param2");
            // Assert
            assertEquals(translated, r);
        }
    }

    @Nested @DisplayName("getMessage(key, locale)")
    class GetMessageKeyLocale {
        @Test @DisplayName("debe retornar mensaje con locale especifico")
        void getMessage_locale_devuelveTraduccion() {
            // Arrange
            String key = easyRandom.nextObject(String.class);
            String translated = easyRandom.nextObject(String.class);
            Locale locale = Locale.ENGLISH;
            when(messageSource.getMessage(eq(key), isNull(), eq(locale))).thenReturn(translated);
            // Act
            String r = service.getMessage(key, locale);
            // Assert
            assertEquals(translated, r);
        }
    }

    @Nested @DisplayName("getMessage(key, locale, params)")
    class GetMessageKeyLocaleParams {
        @Test @DisplayName("debe retornar mensaje con locale y parametros")
        void getMessage_localeParams_devuelveTraduccion() {
            // Arrange
            String key = easyRandom.nextObject(String.class);
            String translated = easyRandom.nextObject(String.class);
            Locale locale = Locale.FRENCH;
            when(messageSource.getMessage(eq(key), any(Object[].class), eq(locale))).thenReturn(translated);
            // Act
            String r = service.getMessage(key, locale, "p1");
            // Assert
            assertEquals(translated, r);
        }

        @Test @DisplayName("debe retornar la clave cuando falla la traduccion")
        void getMessage_fallo_devuelveClave() {
            // Arrange
            String key = easyRandom.nextObject(String.class);
            Locale locale = Locale.ENGLISH;
            when(messageSource.getMessage(eq(key), any(), eq(locale)))
                    .thenThrow(new RuntimeException("not found"));
            // Act
            String r = service.getMessage(key, locale, "p1");
            // Assert
            assertEquals(key, r);
        }
    }
}
