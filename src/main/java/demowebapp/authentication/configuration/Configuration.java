package demowebapp.authentication.configuration;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;

import java.io.InputStream;
import java.security.KeyStore;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Autowired
    private ConfigurationProvider configurationProvider;

    @Bean
    RestTemplate restTemplate() throws Exception {

        InputStream trustStore = new ClassPathResource(configurationProvider.getTrustStore()).getInputStream();
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(trustStore, configurationProvider.getTrustStorePassword().toCharArray());

        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(ks, new TrustSelfSignedStrategy()).build();
        SSLConnectionSocketFactory socketFactory =
                new SSLConnectionSocketFactory(sslContext);
        HttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(socketFactory).build();
        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(factory);
    }
}
