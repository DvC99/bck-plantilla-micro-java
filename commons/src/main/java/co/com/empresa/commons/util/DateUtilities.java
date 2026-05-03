package co.com.empresa.commons.util;


import lombok.AccessLevel;

import lombok.NoArgsConstructor;


import java.text.SimpleDateFormat;

import java.time.LocalDate;

import java.time.LocalDateTime;

import java.time.ZoneId;

import java.time.format.DateTimeFormatter;

import java.time.temporal.ChronoUnit;

import java.util.Date;


/**
 * Clase de utilidad para manejar operaciones relacionadas con fechas.
 * <p>
 * Proporciona métodos para convertir, formatear y calcular diferencias entre fechas.
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)

public final class DateUtilities {

    private static final String FORMAT_DD_MM_YYYY = "dd/MM/yyyy";


    /**
     * Convierte una representación de cadena de una fecha en un objeto {@code Date}.
     * <p>
     * Analiza una cadena de fecha en el formato "dd/MM/yyyy" y la convierte
     * en un objeto {@code java.util.Date}.
     *
     * @param date cadena que representa la fecha en el formato "dd/MM/yyyy"
     * @return objeto {@code Date} que representa la fecha analizada, o {@code null} si la entrada es nulo
     */
    public static Date formatStringToDate(String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DD_MM_YYYY);

        if (date != null) {

            LocalDate localDate = LocalDate.parse(date, formatter);

            return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        } else {

            return null;

        }

    }


    /**
     * Formatea una cadena de fecha desde el formato ISO 8601 a un formato de fecha personalizado.
     * <p>
     * Intenta analizar la cadena de entrada primero como un datetime completo en formato ISO 8601,
     * y si falla, intenta analizarla como una cadena de fecha simple.
     *
     * @param inputDate cadena de fecha de entrada a formatear (ISO 8601 o yyyy-MM-dd)
     * @return cadena de fecha formateada en el formato "dd/MM/yyyy" si el análisis es exitoso,
     * o {@code null} si la entrada es nula o no puede ser analizada
     */
    public static String formatDateString(String inputDate) {

        if (inputDate == null) {

            return null;

        }

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(FORMAT_DD_MM_YYYY);

        try {

            LocalDateTime dateTime = LocalDateTime.parse(inputDate, inputFormatter);

            return dateTime.format(outputFormatter);

        } catch (Exception e) {

            try {

                LocalDate date = LocalDate.parse(inputDate.substring(0, 10));

                return date.format(outputFormatter);

            } catch (Exception ex) {

                return null;

            }

        }

    }


    /**
     * Calcula el número de días entre dos fechas dadas.
     * <p>
     * Toma dos cadenas de fecha como entrada en cualquier formato soportado,
     * las analiza en objetos {@code LocalDate} y luego calcula el número absoluto
     * de días entre estas fechas.
     *
     * @param fechaInicial cadena que representa la fecha inicial en cualquier formato soportado
     * @param fechaFinal   cadena que representa la fecha final en cualquier formato soportado
     * @return entero que representa el número absoluto de días entre las dos fechas,
     * o -1 si alguna de las cadenas no puede ser analizada
     */
    public static int numberOfDaysTwoDates(String fechaInicial, String fechaFinal) {

        LocalDate startDate = parseAnyDateFormat(fechaInicial);

        LocalDate endDate = parseAnyDateFormat(fechaFinal);

        if (startDate == null || endDate == null) {

            return -1; // Indica error en el análisis

        }

        return Integer.parseInt(String.valueOf(Math.abs(ChronoUnit.DAYS.between(startDate, endDate))));

    }


    /**
     * Intenta analizar una cadena de fecha en varios formatos comunes.
     * <p>
     * Prueba múltiples formateadores de fecha para analizar la cadena de entrada en un objeto {@code LocalDate}.
     * Soporta formatos como "dd/MM/yyyy", "yyyy-MM-dd", "MM/dd/yyyy", ISO local date y ISO offset date.
     *
     * @param dateString la cadena de fecha a analizar
     * @return objeto {@code LocalDate} que representa la fecha analizada, o {@code null} si falla el análisis
     */
    private static LocalDate parseAnyDateFormat(String dateString) {

        DateTimeFormatter[] formatters = {

                DateTimeFormatter.ofPattern(FORMAT_DD_MM_YYYY),

                DateTimeFormatter.ofPattern("yyyy-MM-dd"),

                DateTimeFormatter.ofPattern("MM/dd/yyyy"),

                DateTimeFormatter.ISO_LOCAL_DATE,

                DateTimeFormatter.ISO_OFFSET_DATE

        };

        for (DateTimeFormatter formatter : formatters) {

            try {

                return LocalDate.parse(dateString, formatter);

            } catch (Exception e) {

                // Intenta con el siguiente formateador

            }

        }

        return null; // Si ningún formateador pudo analizar la fecha

    }


    /**
     * Convierte un objeto {@code Date} en una representación de cadena formateada.
     *
     * @param date el objeto {@code Date} a formatear
     * @return cadena de la fecha en el formato "dd/MM/yyyy", o {@code null} si la fecha es nula
     */
    public static String formatDateToString(Date date) {

        if (date == null) {

            return null;

        }

        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_DD_MM_YYYY);

        return formatter.format(date);

    }

}
