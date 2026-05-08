package co.com.empresa.commons.exception;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Excepciones personalizadas")
class ExceptionsTest {

    private final EasyRandom easyRandom = new EasyRandom();

    @Nested @DisplayName("DomainException")
    class DomainExceptionTest {
        @Test @DisplayName("constructor con mensaje debe almacenar el mensaje")
        void constructor_mensaje_almacena() {
            // Arrange
            String msg = easyRandom.nextObject(String.class);
            // Act
            DomainException ex = new DomainException(msg);
            // Assert
            assertEquals(msg, ex.getMessage());
            assertInstanceOf(RuntimeException.class, ex);
        }

        @Test @DisplayName("constructor con mensaje y causa debe almacenar ambos")
        void constructor_mensajeYCausa_almacena() {
            // Arrange
            String msg = easyRandom.nextObject(String.class);
            Throwable causa = new IllegalStateException("raiz");
            // Act
            DomainException ex = new DomainException(msg, causa);
            // Assert
            assertEquals(msg, ex.getMessage());
            assertEquals(causa, ex.getCause());
        }
    }

    @Nested @DisplayName("ApplicationException")
    class ApplicationExceptionTest {
        @Test @DisplayName("constructor con mensaje debe almacenar el mensaje")
        void constructor_mensaje_almacena() {
            // Arrange
            String msg = easyRandom.nextObject(String.class);
            // Act
            ApplicationException ex = new ApplicationException(msg);
            // Assert
            assertEquals(msg, ex.getMessage());
            assertInstanceOf(RuntimeException.class, ex);
        }

        @Test @DisplayName("constructor con mensaje y causa debe almacenar ambos")
        void constructor_mensajeYCausa_almacena() {
            // Arrange
            String msg = easyRandom.nextObject(String.class);
            Throwable causa = new IllegalArgumentException("raiz");
            // Act
            ApplicationException ex = new ApplicationException(msg, causa);
            // Assert
            assertEquals(msg, ex.getMessage());
            assertEquals(causa, ex.getCause());
        }
    }

    @Nested @DisplayName("InfrastructureException")
    class InfrastructureExceptionTest {
        @Test @DisplayName("constructor con mensaje debe almacenar el mensaje")
        void constructor_mensaje_almacena() {
            // Arrange
            String msg = easyRandom.nextObject(String.class);
            // Act
            InfrastructureException ex = new InfrastructureException(msg);
            // Assert
            assertEquals(msg, ex.getMessage());
        }

        @Test @DisplayName("constructor sin argumentos debe instanciar")
        void constructor_sinArgs_instancia() {
            // Act
            InfrastructureException ex = new InfrastructureException();
            // Assert
            assertNotNull(ex);
        }

        @Test @DisplayName("constructor con causa debe almacenar la causa")
        void constructor_soloCausa_almacena() {
            // Arrange
            Throwable causa = new RuntimeException("raiz");
            // Act
            InfrastructureException ex = new InfrastructureException(causa);
            // Assert
            assertEquals(causa, ex.getCause());
        }

        @Test @DisplayName("constructor con mensaje y causa debe almacenar ambos")
        void constructor_mensajeYCausa_almacena() {
            // Arrange
            String msg = easyRandom.nextObject(String.class);
            Throwable causa = new RuntimeException("raiz");
            // Act
            InfrastructureException ex = new InfrastructureException(msg, causa);
            // Assert
            assertEquals(msg, ex.getMessage());
            assertEquals(causa, ex.getCause());
        }
    }
}
