package ee.ria.relyingparty.authentication;

public interface AuthenticationService {
    AuthenticationResponse activateAuthentication(AuthenticationRequest request);
    AuthenticationStatusResponse getStatusResponse(String sessionId);
}
