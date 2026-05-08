package co.com.empresa.commons.util;

import co.com.empresa.commons.exception.InfrastructureException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ObjectUtilities")
class ObjectUtilitiesTest {

    public static class TestPojo {
        private String name;
        private Integer age;
        private Boolean active;
        private Long points;
        private List<String> tags;

        public TestPojo() {}
        public TestPojo(String name, Integer age, Boolean active, Long points, List<String> tags) {
            this.name = name; this.age = age; this.active = active; this.points = points; this.tags = tags;
        }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Integer getAge() { return age; }
        public void setAge(Integer age) { this.age = age; }
        public Boolean getActive() { return active; }
        public void setActive(Boolean active) { this.active = active; }
        public Long getPoints() { return points; }
        public void setPoints(Long points) { this.points = points; }
        public List<String> getTags() { return tags; }
        public void setTags(List<String> tags) { this.tags = tags; }
    }

    public static class TestArrayPojo {
        private String[] items;

        public TestArrayPojo() {}
        public TestArrayPojo(String[] items) { this.items = items; }
        public String[] getItems() { return items; }
        public void setItems(String[] items) { this.items = items; }
    }

    @Nested @DisplayName("getAttribute")
    class GetAttribute {
        @Test @DisplayName("debe obtener valor de un atributo simple")
        void atributoExistente_retornaValor() {
            // Arrange
            TestPojo pojo = new TestPojo("John", 30, true, 100L, List.of("a"));
            // Act
            String name = ObjectUtilities.getAttribute(pojo, "name").toString();
            // Assert
            assertEquals("John", name);
        }

        @Test @DisplayName("debe lanzar InfrastructureException cuando el atributo no existe")
        void atributoInexistente_lanza() {
            // Arrange
            TestPojo pojo = new TestPojo();
            // Act & Assert
            assertThrows(InfrastructureException.class,
                    () -> ObjectUtilities.getAttribute(pojo, "noExiste"));
        }
    }

    @Nested @DisplayName("getListAttribute")
    class GetListAttribute {
        @Test @DisplayName("debe obtener lista de tags")
        void listaExistente_retornaLista() {
            // Arrange
            TestPojo pojo = new TestPojo("x", 1, true, 1L, List.of("a", "b"));
            // Act
            List<Object> r = ObjectUtilities.getListAttribute(pojo, "tags");
            // Assert
            assertEquals(2, r.size());
            assertEquals("a", r.get(0));
        }

        @Test @DisplayName("debe convertir arreglo a lista")
        void arregloExistente_retornaLista() {
            // Arrange
            TestArrayPojo pojo = new TestArrayPojo(new String[]{"x", "y"});
            // Act
            List<Object> r = ObjectUtilities.getListAttribute(pojo, "items");
            // Assert
            assertEquals(2, r.size());
            assertEquals("x", r.get(0));
        }

        @Test @DisplayName("debe lanzar InfrastructureException cuando la propiedad no es lista ni arreglo")
        void propiedadNoColeccion_lanza() {
            // Arrange
            TestPojo pojo = new TestPojo("x", 1, true, 1L, null);
            // Act & Assert
            assertThrows(InfrastructureException.class,
                    () -> ObjectUtilities.getListAttribute(pojo, "name"));
        }
    }

    @Nested @DisplayName("getStringAttribute")
    class GetStringAttribute {
        @Test @DisplayName("debe retornar string cuando el atributo existe")
        void atributoExistente_retorna() {
            // Arrange
            TestPojo pojo = new TestPojo("John", 30, true, 100L, null);
            // Act
            String r = ObjectUtilities.getStringAttribute(pojo, "name");
            // Assert
            assertEquals("John", r);
        }

        @Test @DisplayName("debe retornar vacio cuando falla")
        void atributoInexistente_retornaVacio() {
            // Arrange
            TestPojo pojo = new TestPojo();
            // Act
            String r = ObjectUtilities.getStringAttribute(pojo, "noExiste");
            // Assert
            assertEquals("", r);
        }
    }

    @Nested @DisplayName("getIntegerAttribute")
    class GetIntegerAttribute {
        @Test @DisplayName("debe retornar entero cuando el atributo existe")
        void atributoExistente_retorna() {
            // Arrange
            TestPojo pojo = new TestPojo("x", 42, true, 1L, null);
            // Act
            Integer r = ObjectUtilities.getIntegerAttribute(pojo, "age");
            // Assert
            assertEquals(42, r);
        }

        @Test @DisplayName("debe retornar null cuando el atributo no existe")
        void atributoInexistente_retornaNull() {
            // Arrange
            TestPojo pojo = new TestPojo();
            // Act
            Integer r = ObjectUtilities.getIntegerAttribute(pojo, "noExiste");
            // Assert
            assertNull(r);
        }
    }

    @Nested @DisplayName("getLongAttribute")
    class GetLongAttribute {
        @Test @DisplayName("debe retornar long cuando el atributo existe")
        void atributoExistente_retorna() {
            // Arrange
            TestPojo pojo = new TestPojo("x", 1, true, 999L, null);
            // Act
            Long r = ObjectUtilities.getLongAttribute(pojo, "points");
            // Assert
            assertEquals(999L, r);
        }

        @Test @DisplayName("debe retornar null cuando el atributo no existe")
        void atributoInexistente_retornaNull() {
            // Arrange
            TestPojo pojo = new TestPojo();
            // Act
            Long r = ObjectUtilities.getLongAttribute(pojo, "noExiste");
            // Assert
            assertNull(r);
        }
    }

    @Nested @DisplayName("getBooleanAttribute")
    class GetBooleanAttribute {
        @Test @DisplayName("debe retornar true cuando el atributo es true")
        void atributoTrue_retornaTrue() {
            // Arrange
            TestPojo pojo = new TestPojo("x", 1, true, 1L, null);
            // Act
            Boolean r = ObjectUtilities.getBooleanAttribute(pojo, "active");
            // Assert
            assertTrue(r);
        }

        @Test @DisplayName("debe retornar false cuando el atributo no existe")
        void atributoInexistente_retornaFalse() {
            // Arrange
            TestPojo pojo = new TestPojo();
            // Act
            Boolean r = ObjectUtilities.getBooleanAttribute(pojo, "noExiste");
            // Assert
            assertFalse(r);
        }
    }
}
