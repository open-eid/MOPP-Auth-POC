package ee.ria.deviceresponse.authentication.client;

import ee.ria.deviceresponse.configuration.ReplyConfigurationProvider;

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
public class ReplySessionServiceClientImpl implements ReplySessionServiceClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReplySessionServiceClientImpl.class);
    @Autowired
    private ReplyConfigurationProvider replyConfigurationProvider;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Session getSession(String sessionId) {
        return restTemplate.getForObject(replyConfigurationProvider.getSessionEndpoint() + "/" + sessionId, Session.class);
    }

    @Override
    public void updateSession(Session request) {
        ResponseEntity<Void> responseEntity = restTemplate.exchange(replyConfigurationProvider.getSessionEndpoint(),
                HttpMethod.PUT, formHttpEntity(request), Void.class);
        if (!(HttpStatus.ACCEPTED == responseEntity.getStatusCode())) {
            throw new RuntimeException("Session could not be updated");
        }
    }

    private HttpEntity<?> formHttpEntity(Object object) {
        return new HttpEntity<>(object);
    }
}
