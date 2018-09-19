package ee.ria.relyingparty.authentication.client;

import ee.ria.relyingparty.configuration.ConfigurationProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SessionServiceClientImpl implements SessionServiceClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionServiceClientImpl.class);
    @Autowired
    private ConfigurationProvider configurationProvider;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void createNewSession(SessionRequest request) {
        ResponseEntity<Void> responseEntity = restTemplate.exchange(configurationProvider.getSessionEndpoint(),
                HttpMethod.POST, formHttpEntity(request), Void.class);
        if(!(HttpStatus.CREATED == responseEntity.getStatusCode())){
            throw new RuntimeException("Session could not be created");
        }
    }

    private HttpEntity<?> formHttpEntity(Object object) {
        return new HttpEntity<>(object);
    }
}
