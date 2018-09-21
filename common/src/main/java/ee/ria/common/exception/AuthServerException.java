package ee.ria.common.exception;

public class AuthServerException extends RuntimeException {

    public AuthServerException() {
    }

    public AuthServerException(String message) {
        super(message);
    }

    public AuthServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
