package co.com.empresa.infrastructure.config.microservices.rest;


import lombok.Getter;

import lombok.Setter;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.stereotype.Component;


import java.util.HashMap;

import java.util.Map;


/**
 * Configuration class for REST microservices properties.
 * <p>
 * This class maps configuration properties with the prefix "rest.services" from
 * the application properties file to a map of microservice configurations.
 */
@Slf4j
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "rest.services")
public class RestConfig {



    /**
     * Map containing the configurations of the microservices.
     * <p>
     * The key represents the microservice name and the value its associated configuration (e.g., URL).
     */
    private Map<String, String> microservice = new HashMap<>();



    /**
     * Retrieves the configuration for the specified microservice.
     *
     * @param name the name of the microservice
     * @return the configuration of the microservice, or {@code null} if not found
     */
    public String getServiceConfig(String name) {


        String config = microservice.get(name);

        log.debug("Microservice Config for {}: {}", name, config);

        return config;

    }

}

























