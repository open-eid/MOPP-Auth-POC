package demowebapp.authentication;

import demowebapp.authentication.client.AuthenticationServerClient;
import demowebapp.authentication.client.AuthenticationStatusResponse;
import demowebapp.authentication.client.BeginAuthenticationRequest;
import demowebapp.authentication.client.BeginAuthenticationResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private static final String STATE_COMPLETE = "COMPLETE";
    @Autowired
    private AuthenticationServerClient client;

    public AuthenticationStatusResponse authenticate(String hash, String nationalIdentityNumber) throws InterruptedException {
        AuthenticationStatusResponse statusResponse = new AuthenticationStatusResponse();
        boolean noResponse = true;
        BeginAuthenticationRequest authenticationRequest = new BeginAuthenticationRequest();
        authenticationRequest.setHash(hash);
        authenticationRequest.setNationalIdentityNumber(nationalIdentityNumber);
        BeginAuthenticationResponse beginAuthenticationResponse = client.beginAuthentication(authenticationRequest);
        if (beginAuthenticationResponse.getErrorCode() != null) {
            statusResponse.setResult(beginAuthenticationResponse.getErrorCode());
            noResponse = false;
        }

        while (noResponse) {
            statusResponse = client.getStatus(beginAuthenticationResponse.getSessionId());
            if (statusResponse != null && statusResponse.getState().equals(STATE_COMPLETE)) {
                noResponse = false;
            }
            Thread.sleep(1000);
        }
        return statusResponse;
    }

}
