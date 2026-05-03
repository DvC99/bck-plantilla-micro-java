package co.com.empresa.infrastructure.config.microservices.grpc;import lombok.Getter;import lombok.Setter;import lombok.extern.slf4j.Slf4j;import org.springframework.boot.context.properties.ConfigurationProperties;import org.springframework.stereotype.Component;import java.util.HashMap;import java.util.Map;/**
 * Configuration class for gRPC microservices properties.
 * <p>
 * This class maps configuration properties with the prefix "grpc.services" from
 * the application properties file to a map of gRPC service hosts.
 */
@Slf4j
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "grpc.services")
public class GrpcConfig {
        /**
     * Map of gRPC service configurations.
     * <p>
     * The key represents the gRPC service name and the value its host address.
     */
    private Map<String, String> grpc = new HashMap<>();
        /**
     * Retrieves the host for the specified gRPC service.
     *
     * @param name the name of the gRPC service
     * @return the host of the gRPC service, or {@code null} if not found
     */
    public String getServiceHost(String name) {
        String host = grpc.get(name);        log.debug("gRPC Service Host for {}: {}", name, host);        return host;    }}