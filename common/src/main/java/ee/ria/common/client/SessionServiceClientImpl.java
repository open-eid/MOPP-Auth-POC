package ee.ria.common.client;

import ee.ria.common.configuration.ConfigurationProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("sessionRestTemplate")
    private RestTemplate restTemplate;

    @Override
    public Session getSession(String sessionId) {
        return restTemplate.getForObject(configurationProvider.getSessionEndpoint() + "/" + sessionId, Session.class);
    }

    @Override
    public void deleteCompleteSession(String sessionId) {
        restTemplate.delete(configurationProvider.getSessionEndpoint() + "/" + sessionId);
    }

    @Override
    public void createNewSession(Session request) {
        ResponseEntity<Void> responseEntity = restTemplate.exchange(configurationProvider.getSessionEndpoint(),
                HttpMethod.POST, formHttpEntity(request), Void.class);
        if(!(HttpStatus.CREATED == responseEntity.getStatusCode())){
            throw new RuntimeException("Session could not be created");
        }
    }

    @Override
    public void updateSession(Session request) {
        ResponseEntity<Void> responseEntity = restTemplate.exchange(configurationProvider.getSessionEndpoint(),
                HttpMethod.PUT, formHttpEntity(request), Void.class);
        if (!(HttpStatus.ACCEPTED == responseEntity.getStatusCode())) {
            throw new RuntimeException("Session could not be updated");
        }
    }

    private HttpEntity<?> formHttpEntity(Object object) {
        return new HttpEntity<>(object);
    }
}
