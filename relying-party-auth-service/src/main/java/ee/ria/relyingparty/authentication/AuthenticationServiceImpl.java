package ee.ria.relyingparty.authentication;

import ee.ria.common.client.Session;
import ee.ria.common.client.SessionServiceClient;
import ee.ria.common.exception.SessionNotFoundException;
import ee.ria.relyingparty.messaging.MessagingRequest;
import ee.ria.relyingparty.messaging.MessagingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final String STATE_RUNNING = "RUNNING";
    private static final String STATE_COMPLETE = "COMPLETE";

    @Autowired
    private MessagingService messagingService;
    @Autowired
    private SessionServiceClient sessionServiceClient;

    @Override
    public AuthenticationResponse activateAuthentication(AuthenticationRequest request) {
        String sessionId = generateSessionId();
        sessionServiceClient.createNewSession(generateSessionRequest(request, sessionId));
        messagingService.message(generateMessagingRequest(request, sessionId));
        return new AuthenticationResponse(sessionId);
    }

    @Override
    public AuthenticationStatusResponse getStatusResponse(String sessionId) {
        Session session = sessionServiceClient.getSession(sessionId);
        if (session == null) {
            throw new SessionNotFoundException(sessionId);
        }
        AuthenticationStatusResponse response = new AuthenticationStatusResponse();
        if (session.getResult() == null) {
            response.setState(STATE_RUNNING);
        } else {
            response.setState(STATE_COMPLETE);
            response.setCert(session.getCert());
            response.setSignature(session.getSignature());
            response.setResult(session.getResult());
            sessionServiceClient.deleteCompleteSession(sessionId);
        }
        return response;
    }

    private Session generateSessionRequest(AuthenticationRequest request, String sessionId) {
        Session session = new Session();
        session.setSessionId(sessionId);
        session.setNationalIdentityNumber(request.getNationalIdentityNumber());
        session.setHash(request.getHash());
        return session;
    }

    private MessagingRequest generateMessagingRequest(AuthenticationRequest request, String sessionId) {
        MessagingRequest messagingRequest = new MessagingRequest();
        messagingRequest.setHash(request.getHash());
        messagingRequest.setSessionId(sessionId);
        return messagingRequest;
    }

    private String generateSessionId() {
        return UUID.randomUUID().toString();
    }
}
