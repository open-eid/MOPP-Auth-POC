package ee.ria.relyingparty.authentication.configuration;

import ee.ria.common.client.SessionServiceClient;
import ee.ria.common.client.SessionServiceClientImpl;
import ee.ria.common.configuration.ConfigurationProvider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@org.springframework.context.annotation.Configuration
@Import({ ee.ria.common.configuration.Configuration.class, ConfigurationProvider.class})
public class Configuration {

    @Bean
    public SessionServiceClient sessionServiceClient(){
        return new SessionServiceClientImpl();
    }
}
