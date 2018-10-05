package demowebapp.authentication.client;

import demowebapp.authentication.configuration.ConfigurationProvider;
import demowebapp.authentication.exception.ErrorDetails;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class AuthenticationServerClient {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ConfigurationProvider configurationProvider;

    public BeginAuthenticationResponse beginAuthentication(BeginAuthenticationRequest request) {
        try {
            ResponseEntity<BeginAuthenticationResponse> responseEntity = restTemplate.exchange(configurationProvider.getAuthEndpoint(),
                    HttpMethod.POST, formHttpEntity(request), BeginAuthenticationResponse.class);
            return responseEntity.getBody();

        } catch (HttpStatusCodeException exception) {
            ErrorDetails errorResponse = parseErrorResponse(exception);
            BeginAuthenticationResponse response = new BeginAuthenticationResponse();
            response.setErrorCode(errorResponse.getErrorCode());
            return response;
        }
    }

    public AuthenticationStatusResponse getStatus(String sessionId) {
        return restTemplate.getForObject(configurationProvider.getAuthEndpoint() + "/session/" + sessionId, AuthenticationStatusResponse.class);

    }

    private ErrorDetails parseErrorResponse(HttpStatusCodeException e) {
        try {
            return new ObjectMapper().readValue(e.getResponseBodyAsString(), ErrorDetails.class);
        } catch (IOException ex) {
            throw new IllegalStateException("Could not parse error response body.", ex);
        }
    }

    private HttpEntity<?> formHttpEntity(Object object) {
        return new HttpEntity<>(object);
    }
}
