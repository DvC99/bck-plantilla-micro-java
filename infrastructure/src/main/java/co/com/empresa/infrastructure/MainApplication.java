package co.com.empresa.infrastructure;import org.springframework.boot.SpringApplication;import org.springframework.boot.autoconfigure.SpringBootApplication;import org.springframework.context.annotation.ComponentScan;/**
 * Main entry point for the Spring Boot application.
 * <p>
 * This class initializes the Spring context and starts the application.
 * It configures component scanning across all project layers: infrastructure, application, domain, and commons.
 */
@SpringBootApplication
@ComponentScan(basePackages = {
        "co.com.empresa.infrastructure",        "co.com.empresa.application",        "co.com.empresa.domain",        "co.com.empresa.commons"})public class MainApplication {        /**
     * Main method that launches the Spring Boot application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);    }}