package ee.ria.session.configuration;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotBlank;

@Configuration
@ConfigurationProperties(prefix = "session.server")
@Data
public class ConfigurationProvider {
    public final static String MAP_SESSION_CACHE = "sessionCache";
    @NotBlank
    private boolean multiCastEnabled;
    private String tcpIp;
    @NotBlank
    private int sessionExpirationInSeconds;
}
