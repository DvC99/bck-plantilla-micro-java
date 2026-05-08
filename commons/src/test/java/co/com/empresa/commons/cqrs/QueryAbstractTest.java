package co.com.empresa.commons.cqrs;

import co.com.empresa.commons.exception.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("QueryAbstract")
class QueryAbstractTest {

    private static class EchoQuery extends QueryAbstract<String, String> {
        @Override protected String preProcess(String ctx) { return ctx; }
        @Override protected String process(String ctx) { return "resultado:" + ctx; }
    }

    private static class PreProcessNuloQuery extends QueryAbstract<String, String> {
        @Override protected String preProcess(String ctx) { return null; }
        @Override protected String process(String ctx) { return "no-debe-llegar"; }
    }

    private static class PreProcessLanzaQuery extends QueryAbstract<String, String> {
        @Override protected String preProcess(String ctx) { throw new DomainException("error pre"); }
        @Override protected String process(String ctx) { return "no-debe-llegar"; }
    }

    private static class ProcessLanzaQuery extends QueryAbstract<String, String> {
        @Override protected String preProcess(String ctx) { return ctx; }
        @Override protected String process(String ctx) { throw new DomainException("error process"); }
    }

    private static class RuntimeExQuery extends QueryAbstract<String, String> {
        @Override protected String preProcess(String ctx) { return ctx; }
        @Override protected String process(String ctx) { throw new RuntimeException("inesperado"); }
    }

    private static class PostProcessQuery extends QueryAbstract<String, String> {
        @Override protected String preProcess(String ctx) { return ctx; }
        @Override protected String process(String ctx) { return ctx; }
        @Override protected String postProcess(String r) { return "post:" + r; }
    }

    @Nested
    @DisplayName("execute()")
    class Execute {

        @Test
        @DisplayName("debe ejecutar flujo completo y retornar resultado")
        void ejecutar_flujoNormal_retornaResultado() {
            // Arrange
            var q = new EchoQuery();
            // Act
            String r = q.execute("entrada");
            // Assert
            assertEquals("resultado:entrada", r);
        }

        @Test
        @DisplayName("debe retornar null cuando preProcess devuelve null")
        void ejecutar_preProcessNulo_retornaNull() {
            // Arrange
            var q = new PreProcessNuloQuery();
            // Act
            String r = q.execute("x");
            // Assert
            assertNull(r);
        }

        @Test
        @DisplayName("debe propagar DomainException desde preProcess")
        void ejecutar_preProcessLanza_propaga() {
            // Arrange
            var q = new PreProcessLanzaQuery();
            // Act & Assert
            DomainException ex = assertThrows(DomainException.class, () -> q.execute("x"));
            assertEquals("error pre", ex.getMessage());
        }

        @Test
        @DisplayName("debe propagar DomainException desde process")
        void ejecutar_processLanza_propaga() {
            // Arrange
            var q = new ProcessLanzaQuery();
            // Act & Assert
            DomainException ex = assertThrows(DomainException.class, () -> q.execute("x"));
            assertEquals("error process", ex.getMessage());
        }

        @Test
        @DisplayName("debe envolver excepcion inesperada en DomainException")
        void ejecutar_excepcionInesperada_envuelve() {
            // Arrange
            var q = new RuntimeExQuery();
            // Act & Assert
            DomainException ex = assertThrows(DomainException.class, () -> q.execute("x"));
            assertEquals("Error interno al procesar la consulta", ex.getMessage());
        }

        @Test
        @DisplayName("debe aplicar transformacion de postProcess")
        void ejecutar_postProcess_aplicaTransformacion() {
            // Arrange
            var q = new PostProcessQuery();
            // Act
            String r = q.execute("v");
            // Assert
            assertEquals("post:v", r);
        }
    }
}
