package ee.ria.common.configuration;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Configuration
@ConfigurationProperties(prefix = "auth.server")
@Validated
@Data
public class ConfigurationProvider {
    private static final String DEFAULT_KEY_STORE_TYPE = "PKCS12";
    private static final String DEFAULT_TRUST_STORE_TYPE = "PKCS12";
    private static final int DEFAULT_READ_TIMEOUT = 1000 * 10;
    private static final int DEFAULT_CONNECTION_TIMEOUT = 1000 * 10;

    private int connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;
    private int readTimeout = DEFAULT_READ_TIMEOUT;

    @NotBlank
    private String fireBaseEndpoint;
    @NotBlank
    private String sessionEndpoint;
    @NotBlank
    private String sessionServiceKeyStore;
    @NotNull
    private String sessionServiceKeyStorePassword;
    private String sessionServiceKeyStoreType = DEFAULT_KEY_STORE_TYPE;
    @NotBlank
    private String sessionServiceTrustStore;
    @NotNull
    private String sessionServiceTrustStorePassword;
    private String sessionServiceTrustStoreType = DEFAULT_TRUST_STORE_TYPE;
}
