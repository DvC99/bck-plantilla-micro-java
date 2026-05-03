package co.com.empresa.infrastructure.config.jpa;import lombok.Builder;import lombok.Getter;/**
 * Data Transfer Object for database connection secrets.
 * <p>
 * This class holds sensitive connection information such as URL, username, and password.
 */
@Builder
@Getter
public class DBSecretDto {
    /**
     * Database connection URL.
     */
    private final String url;

    /**
     * Database username.
     */
    private final String username;

    /**
     * Database password.
     */
    private final String password;
}
