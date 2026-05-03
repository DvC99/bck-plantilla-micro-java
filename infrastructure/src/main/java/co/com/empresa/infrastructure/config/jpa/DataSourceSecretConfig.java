package co.com.empresa.infrastructure.config.jpa;import org.springframework.beans.factory.annotation.Value;import org.springframework.context.annotation.Bean;import org.springframework.context.annotation.Configuration;/**
 * Configuration class for database connection secrets.
 * <p>
 * This class reads sensitive database credentials from the application properties
 * and exposes them as {@link DBSecretDto} beans for the command and query data sources.
 */
@Configuration
public class DataSourceSecretConfig {
    @Value("${spring.datasource.command.url}")    private String commandUrl;    @Value("${spring.datasource.command.username}")    private String commandUsername;    @Value("${spring.datasource.command.password}")    private String commandPassword;    @Value("${spring.datasource.query.url}")    private String queryUrl;    @Value("${spring.datasource.query.username}")    private String queryUsername;    @Value("${spring.datasource.query.password}")    private String queryPassword;        /**
     * Creates a {@link DBSecretDto} bean for the command database.
     *
     * @return a {@link DBSecretDto} containing command database credentials
     */
    @Bean(name = "commandDBSecret")
    public DBSecretDto commandDBSecret() {
        return DBSecretDto.builder()
                .url(commandUrl)
                .username(commandUsername)
                .password(commandPassword)
                .build();
    }

    /**
     * Creates a {@link DBSecretDto} bean for the query database.
     *
     * @return a {@link DBSecretDto} containing query database credentials
     */
    @Bean(name = "queryDBSecret")
    public DBSecretDto queryDBSecret() {
        return DBSecretDto.builder()
                .url(queryUrl)
                .username(queryUsername)
                .password(queryPassword)
                .build();
    }
}