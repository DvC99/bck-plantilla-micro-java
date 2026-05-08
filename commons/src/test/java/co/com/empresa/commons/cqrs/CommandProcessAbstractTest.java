package co.com.empresa.commons.cqrs;

import co.com.empresa.commons.exception.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CommandProcessAbstract")
class CommandProcessAbstractTest {

    private static class EchoCommand extends CommandProcessAbstract<String, String> {
        @Override protected String preProcess(String ctx) { return ctx; }
        @Override protected String process(String ctx) { return "procesado:" + ctx; }
    }

    private static class PreProcessNuloCommand extends CommandProcessAbstract<String, String> {
        @Override protected String preProcess(String ctx) { return null; }
        @Override protected String process(String ctx) { return "no-debe-llegar"; }
    }

    private static class PreProcessLanzaCommand extends CommandProcessAbstract<String, String> {
        @Override protected String preProcess(String ctx) throws DomainException {
            throw new DomainException("error en preProcess");
        }
        @Override protected String process(String ctx) { return "no-debe-llegar"; }
    }

    private static class ProcessLanzaCommand extends CommandProcessAbstract<String, String> {
        @Override protected String preProcess(String ctx) { return ctx; }
        @Override protected String process(String ctx) throws DomainException {
            throw new DomainException("error en process");
        }
    }

    private static class PostProcessCommand extends CommandProcessAbstract<String, String> {
        @Override protected String preProcess(String ctx) { return ctx; }
        @Override protected String process(String ctx) { return ctx; }
        @Override protected String postProcess(String r) { return "post:" + r; }
    }

    private static class ParseExceptionCommand extends CommandProcessAbstract<String, String> {
        @Override protected String preProcess(String ctx) throws ParseException {
            throw new ParseException("error parse", 0);
        }
        @Override protected String process(String ctx) { return "no-debe-llegar"; }
    }

    @Nested
    @DisplayName("execute()")
    class Execute {

        @Test
        @DisplayName("debe ejecutar el flujo completo y retornar el resultado procesado")
        void ejecutar_flujoCompleto_retornaResultado() {
            // Arrange
            var echo = new EchoCommand();
            // Act
            String resultado = echo.execute("hola");
            // Assert
            assertEquals("procesado:hola", resultado);
        }

        @Test
        @DisplayName("debe retornar null cuando preProcess devuelve null")
        void ejecutar_preProcessNulo_retornaNull() {
            // Arrange
            var cmd = new PreProcessNuloCommand();
            // Act
            String resultado = cmd.execute("cualquiera");
            // Assert
            assertNull(resultado);
        }

        @Test
        @DisplayName("debe relanzar DomainException desde preProcess")
        void ejecutar_preProcessLanza_relanza() {
            // Arrange
            var cmd = new PreProcessLanzaCommand();
            // Act & Assert
            DomainException ex = assertThrows(DomainException.class, () -> cmd.execute("x"));
            assertEquals("error en preProcess", ex.getMessage());
        }

        @Test
        @DisplayName("debe relanzar DomainException desde process")
        void ejecutar_processLanza_relanza() {
            // Arrange
            var cmd = new ProcessLanzaCommand();
            // Act & Assert
            DomainException ex = assertThrows(DomainException.class, () -> cmd.execute("x"));
            assertEquals("error en process", ex.getMessage());
        }

        @Test
        @DisplayName("debe aplicar la transformacion de postProcess")
        void ejecutar_postProcessSobrescrito_aplicaTransformacion() {
            // Arrange
            var cmd = new PostProcessCommand();
            // Act
            String resultado = cmd.execute("valor");
            // Assert
            assertEquals("post:valor", resultado);
        }

        @Test
        @DisplayName("debe envolver ParseException desde preProcess en DomainException")
        void ejecutar_parseException_envuelve() {
            // Arrange
            var cmd = new ParseExceptionCommand();
            // Act & Assert
            DomainException ex = assertThrows(DomainException.class, () -> cmd.execute("x"));
            assertEquals("error parse", ex.getMessage());
        }
    }
}
