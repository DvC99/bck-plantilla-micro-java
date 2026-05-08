package co.com.empresa.commons.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DateUtilities")
class DateUtilitiesTest {

    @Nested @DisplayName("formatStringToDate")
    class FormatStringToDate {
        @Test @DisplayName("debe convertir string dd/MM/yyyy a Date")
        void formatoValido_convierte() {
            // Act
            Date r = DateUtilities.formatStringToDate("25/12/2025");
            // Assert
            assertNotNull(r);
            var cal = java.util.Calendar.getInstance();
            cal.setTime(r);
            assertEquals(25, cal.get(java.util.Calendar.DAY_OF_MONTH));
            assertEquals(11, cal.get(java.util.Calendar.MONTH)); // Dec = 11
            assertEquals(2025, cal.get(java.util.Calendar.YEAR));
        }

        @Test @DisplayName("debe retornar null cuando la entrada es null")
        void entradaNula_retornaNull() {
            // Act & Assert
            assertNull(DateUtilities.formatStringToDate(null));
        }
    }

    @Nested @DisplayName("formatDateString")
    class FormatDateString {
        @Test @DisplayName("debe formatear ISO 8601 a dd/MM/yyyy")
        void iso8601_formatea() {
            // Act
            String r = DateUtilities.formatDateString("2025-12-25T10:30:00Z");
            // Assert
            assertEquals("25/12/2025", r);
        }

        @Test @DisplayName("debe retornar null cuando entrada es null")
        void entradaNula_retornaNull() {
            // Act & Assert
            assertNull(DateUtilities.formatDateString(null));
        }

        @Test @DisplayName("debe manejar string sin formato y retornar null")
        void formatoInvalido_retornaNull() {
            // Act
            String r = DateUtilities.formatDateString("no-es-fecha");
            // Assert
            assertNull(r);
        }
    }

    @Nested @DisplayName("numberOfDaysTwoDates")
    class NumberOfDaysTwoDates {
        @Test @DisplayName("debe calcular dias entre dos fechas en formato dd/MM/yyyy")
        void formatoEstandar_calcula() {
            // Act
            int r = DateUtilities.numberOfDaysTwoDates("01/01/2025", "10/01/2025");
            // Assert
            assertEquals(9, r);
        }

        @Test @DisplayName("debe calcular dias en formato yyyy-MM-dd")
        void formatoIso_calcula() {
            // Act
            int r = DateUtilities.numberOfDaysTwoDates("2025-01-01", "2025-01-05");
            // Assert
            assertEquals(4, r);
        }

        @Test @DisplayName("debe retornar -1 cuando una fecha no se puede analizar")
        void fechaInvalida_retornaMenosUno() {
            // Act
            int r = DateUtilities.numberOfDaysTwoDates("invalido", "01/01/2025");
            // Assert
            assertEquals(-1, r);
        }
    }

    @Nested @DisplayName("formatDateToString")
    class FormatDateToString {
        @Test @DisplayName("debe convertir Date a string dd/MM/yyyy")
        void dateValido_convierte() {
            // Arrange
            var cal = java.util.Calendar.getInstance();
            cal.set(2025, 0, 15, 0, 0, 0);
            cal.set(java.util.Calendar.MILLISECOND, 0);
            Date date = cal.getTime();
            // Act
            String r = DateUtilities.formatDateToString(date);
            // Assert
            assertEquals("15/01/2025", r);
        }

        @Test @DisplayName("debe retornar null cuando Date es null")
        void dateNulo_retornaNull() {
            // Act & Assert
            assertNull(DateUtilities.formatDateToString(null));
        }
    }
}
