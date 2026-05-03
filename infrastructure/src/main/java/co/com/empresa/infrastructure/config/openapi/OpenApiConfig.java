package co.com.empresa.infrastructure.config.openapi;import io.swagger.v3.oas.models.Components;import io.swagger.v3.oas.models.OpenAPI;import io.swagger.v3.oas.models.info.Contact;import io.swagger.v3.oas.models.info.Info;import io.swagger.v3.oas.models.info.License;import io.swagger.v3.oas.models.security.SecurityRequirement;import io.swagger.v3.oas.models.security.SecurityScheme;import io.swagger.v3.oas.models.servers.Server;import org.springframework.beans.factory.annotation.Value;import org.springframework.context.annotation.Bean;import org.springframework.context.annotation.Configuration;import java.util.List;/**
 * OpenAPI/Swagger configuration for API documentation.
 * <p>
 * This configuration provides:
 * <ul>
 *     <li>Detailed API information (title, version, description)</li>
 *     <li>JWT security configuration</li>
 *     <li>Multiple server environments (local, dev, staging, production)</li>
 *     <li>Contact and license information</li>
 * </ul>
 */
@Configuration
public class OpenApiConfig {
    @Value("${application.version:1.0.0}")    private String appVersion;    @Value("${application.name:API de Negocio}")    private String appName;    @Value("${application.description:API RESTful para gestión de microservicios}")    private String appDescription;    @Value("${server.port:8080}")    private String serverPort;        /**
     * Main OpenAPI configuration.
     *
     * @return a configured {@link OpenAPI} instance
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()                .info(apiInfo())                .servers(servers())                .addSecurityItem(securityRequirement())                .components(components());    }        /**
     * General information about the API.
     *
     * @return {@link Info} object containing API details
     */
    private Info apiInfo() {
        return new Info()                .title(appName)                .version(appVersion)                .description(buildDescription())                .contact(contact())                .license(license());    }        /**
     * Builds the enriched API description.
     *
     * @return description in Markdown format
     */
    private String buildDescription() {
        return """                                ## Descripción                                %s                                                                ## Características                                - **Autenticación JWT**: Seguridad basada en tokens                                - **Paginación y Filtrado**: Consultas optimizadas                                - **Validaciones Robustas**: Validación de datos de entrada                                - **Manejo de Errores Estandarizado**: Respuestas consistentes                                - **Internacionalización (i18n)**: Soporte multi-idioma                                - **Versionamiento de API**: Control de versiones                                                                ## Autenticación                                Para usar los endpoints protegidos:                                1. Obtén un token en `/api/auth/login`                                2. Haz clic en el botón **"Authorize"** en la parte superior                                3. Ingresa: `Bearer {tu-token-jwt}`                                4. Haz clic en **"Authorize"** y luego **"Close"**                                                                ## Códigos de Estado HTTP                                | Código | Descripción |                                |--------|-------------|                                | `200 OK` | Solicitud exitosa |                                | `201 Created` | Recurso creado exitosamente |                                | `204 No Content` | Solicitud exitosa sin contenido |                                | `400 Bad Request` | Error en los datos enviados |                                | `401 Unauthorized` | No autenticado (token inválido/expirado) |                                | `403 Forbidden` | Sin permisos para realizar la acción |                                | `404 Not Found` | Recurso no encontrado |                                | `422 Unprocessable Entity` | Error de validación |                                | `500 Internal Server Error` | Error interno del servidor |                                                                ## Internacionalización                                La API soporta múltiples idiomas. Incluye el header:                                ```                                Accept-Language: es                                ```                                Idiomas soportados: `es` (Español), `en` (English), `pt` (Português)                                                                ## Formato de Respuesta                                Todas las respuestas siguen el formato estándar:                                ```json                                {                                  "success": true,                                  "message": "Operación exitosa",                                  "data": { ... },                                  "timestamp": "2026-01-17T10:30:00Z"                                }                                ```                                """.formatted(appDescription);    }        /**
     * Contact information for the development team.
     *
     * @return {@link Contact} information
     */
    private Contact contact() {
        return new Contact()                .name("Equipo de Desarrollo")                .email("desarrollo@empresa.com")                .url("https://empresa.com");    }        /**
     * License information for the project.
     *
     * @return {@link License} information
     */
    private License license() {
        return new License()                .name("Apache 2.0")                .url("https://www.apache.org/licenses/LICENSE-2.0.html");    }        /**
     * Configuration of available servers.
     *
     * @return a list of {@link Server} objects
     */
    private List<Server> servers() {
        return List.of(                new Server()                        .url("http://localhost:" + serverPort)                        .description("Servidor Local"),                new Server()                        .url("https://dev.empresa.com")                        .description("Desarrollo"),                new Server()                        .url("https://staging.empresa.com")                        .description("Staging"),                new Server()                        .url("https://api.empresa.com")                        .description("Producción")        );    }        /**
     * Global security requirement for all endpoints.
     *
     * @return {@link SecurityRequirement} object
     */
    private SecurityRequirement securityRequirement() {
        return new SecurityRequirement()                .addList("Bearer Authentication");    }        /**
     * Security components for the API.
     *
     * @return {@link Components} object containing security schemes
     */
    private Components components() {
        return new Components()                .addSecuritySchemes("Bearer Authentication",                        new SecurityScheme()                                .name("Authorization")                                .type(SecurityScheme.Type.HTTP)                                .scheme("bearer")                                .bearerFormat("JWT")                                .in(SecurityScheme.In.HEADER)                                .description("""                                                                                Ingresa el token JWT en el formato: **Bearer {token}**                                                                                                                                                                Ejemplo:                                                                                ```                                                                                Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...                                                                                ```                                                                                """));    }}