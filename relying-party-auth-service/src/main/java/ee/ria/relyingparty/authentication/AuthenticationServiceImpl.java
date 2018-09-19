package ee.ria.relyingparty.authentication;

import ee.ria.relyingparty.authentication.client.SessionRequest;
import ee.ria.relyingparty.authentication.client.SessionServiceClient;
import ee.ria.relyingparty.messaging.MessagingRequest;
import ee.ria.relyingparty.messaging.MessagingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{

    @Autowired
    private MessagingService messagingService;
    @Autowired
    private SessionServiceClient sessionServiceClient;

    public AuthenticationResponse activateAuthentication(AuthenticationRequest request) {
        String sessionId = generateSessionId();
        sessionServiceClient.createNewSession(generateSessionRequest(request, sessionId));
        messagingService.message(generateMessagingRequest(request, sessionId));
        return new AuthenticationResponse(sessionId);
    }

    private SessionRequest generateSessionRequest(AuthenticationRequest request, String sessionId) {
        SessionRequest sessionRequest = new SessionRequest();
        sessionRequest.setSessionId(sessionId);
        sessionRequest.setNationalIdentityNumber(request.getNationalIdentityNumber());
        sessionRequest.setHash(request.getHash());
        return sessionRequest;
    }

    private MessagingRequest generateMessagingRequest(AuthenticationRequest request, String sessionId) {
        MessagingRequest messagingRequest = new MessagingRequest();
        messagingRequest.setHash(request.getHash());
        messagingRequest.setHashType(request.getHashType());
        messagingRequest.setSessionId(sessionId);
        return messagingRequest;
    }

    private String generateSessionId() {
        return UUID.randomUUID().toString();
    }
}
