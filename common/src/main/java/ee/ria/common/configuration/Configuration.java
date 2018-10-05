package ee.ria.common.configuration;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Autowired
    private ConfigurationProvider configurationProvider;

    @Bean
    public RestTemplate sessionRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        HttpComponentsClientHttpRequestFactory httpRequestFactory = clientHttpRequestFactory();
        httpRequestFactory.setConnectTimeout(configurationProvider.getConnectionTimeout());
        httpRequestFactory.setReadTimeout(configurationProvider.getReadTimeout());
        restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(httpRequestFactory));
        return restTemplate;
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory httpRequestFactory = new SimpleClientHttpRequestFactory();
        httpRequestFactory.setConnectTimeout(configurationProvider.getConnectionTimeout());
        httpRequestFactory.setReadTimeout(configurationProvider.getReadTimeout());
        restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(httpRequestFactory));
        return restTemplate;
    }

    @Bean
    public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory(httpClient());
    }

    private HttpClient httpClient() {
        KeyStore keyStore = loadKeyStore(
                configurationProvider.getSessionServiceKeyStore(),
                configurationProvider.getSessionServiceKeyStorePassword(),
                configurationProvider.getSessionServiceKeyStoreType());
        KeyStore trustStore = loadKeyStore(
                configurationProvider.getSessionServiceTrustStore(),
                configurationProvider.getSessionServiceTrustStorePassword(),
                configurationProvider.getSessionServiceTrustStoreType());

        return HttpClients.custom()
                .setSSLSocketFactory(
                        new SSLConnectionSocketFactory(
                                loadSSLContext(keyStore, trustStore),
                                new String[] {"TLSv1.2"},
                                null,
                                SSLConnectionSocketFactory.getDefaultHostnameVerifier()))
                .build();
    }

    private KeyStore loadKeyStore(String keyStoreLocation, String keyStorePassword, String keyStoreType) {
        try {
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(loadKeyStoreFile(keyStoreLocation), keyStorePassword.toCharArray());
            return keyStore;

        } catch (Exception e) {
            throw new IllegalArgumentException("Could not load key store", e);
        }
    }

    private InputStream loadKeyStoreFile(String keyStoreLocation) {
        try {
            return new ClassPathResource(keyStoreLocation).getInputStream();
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not open key store file", e);
        }
    }

    private SSLContext loadSSLContext(KeyStore keyStore, KeyStore trustStore) {
        try {
            return SSLContexts.custom()
                    .loadKeyMaterial(keyStore, configurationProvider.getSessionServiceKeyStorePassword().toCharArray())
                    .loadTrustMaterial(trustStore, (x509Certificates, s) -> false)
                    .build();
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException | UnrecoverableKeyException e) {
            throw new IllegalStateException("Error loading key or trust material", e);
        }
    }
}
