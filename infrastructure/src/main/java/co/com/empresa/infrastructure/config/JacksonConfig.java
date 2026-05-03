package co.com.empresa.infrastructure.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Jackson ObjectMapper.
 * <p>
 * This class configures the default {@link ObjectMapper} to support Java 8 Date/Time API,
 * ignore unknown properties during deserialization, and format dates as ISO-8601.
 */
@Configuration
public class JacksonConfig {

    /**
     * Configures and returns a customized {@link ObjectMapper} instance.
     *
     * @return a configured {@link ObjectMapper}
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // Soporte para Java 8 Date/Time API (LocalDateTime, etc.)
        mapper.registerModule(new JavaTimeModule());

        // Evitar que falle si el JSON tiene campos que no están en el POJO
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Escribir fechas como ISO-8601 en lugar de timestamps numéricos
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return mapper;
    }
}
