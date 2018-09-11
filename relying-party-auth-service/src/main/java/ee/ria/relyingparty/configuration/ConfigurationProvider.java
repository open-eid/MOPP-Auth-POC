package ee.ria.relyingparty.configuration;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties(prefix = "auth.server")
@Validated
@Data
public class ConfigurationProvider {

    private String fireBaseEndpoint;
}
