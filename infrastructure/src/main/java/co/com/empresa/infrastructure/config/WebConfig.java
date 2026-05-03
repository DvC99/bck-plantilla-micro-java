package co.com.empresa.infrastructure.config;


import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.CorsRegistry;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;


/**
 * Main web configuration class for the application.
 * <p>
 * This class implements {@link WebMvcConfigurer} to customize Spring MVC configuration,
 * including CORS handling and request interceptor registration.
 */
@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {



    private final String[] allowedOrigins;

    private final LocaleChangeInterceptor localeChangeInterceptor;


    /**
     * Constructor to inject necessary configuration dependencies.
     *
     * @param allowedOrigins          array of allowed origins for CORS, injected from {@code cors.allowedOrigins} property
     * @param localeChangeInterceptor the interceptor that handles language changes, injected from Spring context
     */
    public WebConfig(@Value("${cors.allowedOrigins}") String[] allowedOrigins, LocaleChangeInterceptor localeChangeInterceptor) {


        this.allowedOrigins = allowedOrigins;

        this.localeChangeInterceptor = localeChangeInterceptor;

    }


    /**
     * Registers application interceptors.
     * <p>
     * Adds the {@link LocaleChangeInterceptor} to the Spring MVC interceptor registry, allowing it
     * to intercept incoming requests and change the session language if the "lang" parameter is found.
     *
     * @param registry the Spring MVC interceptor registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {


        registry.addInterceptor(localeChangeInterceptor);

    }


    /**
     * Configures Cross-Origin Resource Sharing (CORS) mappings for the entire application.
     * <p>
     * Allows requests from the origins defined in the {@code cors.allowedOrigins} property,
     * enabling the most common HTTP methods and headers to facilitate integration with web clients.
     *
     * @param registry the CORS registry used to configure the mappings
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {


        registry.addMapping("/**")

                .allowedOrigins(allowedOrigins)

                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")

                .allowedHeaders("*")

                .allowCredentials(true);

    }

}



























