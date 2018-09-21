package ee.ria.common.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@org.springframework.context.annotation.Configuration
public class Configuration {

    private static final int DEFAULT_READ_TIMEOUT = 1000 * 10;
    private static final int DEFAULT_CONNECTION_TIMEOUT = 1000 * 10;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory httpRequestFactory = new SimpleClientHttpRequestFactory();
        httpRequestFactory.setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT);
        httpRequestFactory.setReadTimeout(DEFAULT_READ_TIMEOUT);
        restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(httpRequestFactory));
        return restTemplate;
    }
}
