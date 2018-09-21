package ee.ria.common.exception;

public class SessionNotFoundException extends AuthServerException {
    public SessionNotFoundException(String sessionId) {
        super(sessionId);
    }
}
