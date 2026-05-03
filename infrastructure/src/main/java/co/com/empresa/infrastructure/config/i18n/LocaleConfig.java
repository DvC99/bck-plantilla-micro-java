package co.com.empresa.infrastructure.config.i18n;


import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import org.springframework.context.support.ResourceBundleMessageSource;

import org.springframework.web.servlet.LocaleResolver;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;


import java.util.Arrays;

import java.util.Locale;


/**
 * Configuration class for internationalization (i18n) settings.
 * <p>
 * This class configures how the application resolves the locale, manages message bundles,
 * and handles language switching via headers or query parameters.
 */
@Configuration
public class LocaleConfig implements WebMvcConfigurer {



    /**
     * Configures the {@link LocaleResolver} bean to determine the user's locale for each request.
     * <p>
     * Uses {@link AcceptHeaderLocaleResolver} which is most suitable for stateless REST APIs.
     * It reads the language from the HTTP "Accept-Language" header sent by the client.
     * If no header is provided or the language is not supported, Spanish is used as the default.
     * <p>
     * Supported languages: Spanish (es), English (en), Portuguese (pt).
     *
     * @return a {@link LocaleResolver} instance configured to manage the locale from the Accept-Language header
     */
    @Bean
    public LocaleResolver localeResolver() {


        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();

        localeResolver.setDefaultLocale(Locale.forLanguageTag("es")); // Idioma por defecto

        localeResolver.setSupportedLocales(Arrays.asList(

                Locale.forLanguageTag("es"),

                Locale.forLanguageTag("en"),

                Locale.forLanguageTag("pt")

        ));

        return localeResolver;

    }


    /**
     * Configures the {@link LocaleChangeInterceptor} bean to allow changing the current locale via a query parameter.
     * <p>
     * This interceptor is optional and works as an alternative to the Accept-Language header.
     * It is activated when the "lang" parameter is detected in the URL (e.g., {@code /api/resource?lang=en}).
     * <p>
     * Note: For stateless REST APIs, using the Accept-Language header is recommended over this parameter.
     *
     * @return a configured {@link LocaleChangeInterceptor} instance
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {


        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();

        lci.setParamName("lang"); // ParÃƒÆ’Ã‚ámetro opcional para cambiar el idioma, ej.: ?lang=en

        return lci;

    }


    /**
     * Registers the locale change interceptor.
     * <p>
     * This method is automatically called by Spring MVC to configure interceptors.
     *
     * @param registry the Spring MVC interceptor registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {


        registry.addInterceptor(localeChangeInterceptor());

    }


    /**
     * Configures the {@link ResourceBundleMessageSource} bean that manages the loading of internationalization messages.
     * <p>
     * This bean is configured to look for property files with the base name "messages" in the classpath
     * (e.g., {@code messages.properties}, {@code messages_en.properties}, {@code messages_pt.properties}).
     * It uses UTF-8 encoding to ensure correct display of special characters.
     *
     * @return a configured {@link ResourceBundleMessageSource} instance to resolve messages
     */
    @Bean
    public ResourceBundleMessageSource messageSource() {


        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        messageSource.setBasename("messages"); // Busca archivos messages.properties, messages_es.properties, etc.

        messageSource.setDefaultEncoding("UTF-8");

        messageSource.setUseCodeAsDefaultMessage(true); // Devuelve la clave si no encuentra el mensaje

        return messageSource;

    }

}



























