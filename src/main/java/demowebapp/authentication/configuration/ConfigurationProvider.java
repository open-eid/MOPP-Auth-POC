package demowebapp.authentication.configuration;

import lombok.Data;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties(prefix = "auth.demo")
@Validated
@Data
public class ConfigurationProvider {
    @NotBlank
    private String authEndpoint;
    @NotBlank
    private String trustStore;
    @NotBlank
    private String trustStorePassword;
}
