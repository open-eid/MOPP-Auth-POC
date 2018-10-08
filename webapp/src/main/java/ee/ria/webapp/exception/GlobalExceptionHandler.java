package ee.ria.webapp.exception;

import ee.ria.common.exception.DeviceIdNotFoundException;
import ee.ria.common.exception.SessionNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(SessionNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<ErrorDetails> handleSessionNotFoundException(Exception ex) {
        String errorMessage = "Session not found - " + ex.getMessage();
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), "SESSION_NOT_FOUND",
                errorMessage);
        LOGGER.warn("Session not found - {}", ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DeviceIdNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<ErrorDetails> handleDeviceIdNotFoundException(Exception ex) {
        String errorMessage = "Device id not found - " + ex.getMessage();
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), "DEVICE_ID_NOT_FOUND",
                errorMessage);
        LOGGER.warn("Device id not found for that person - {}", ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<ErrorDetails> handleRemainingException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false));
        LOGGER.error("Internal server error - {}", ex.getLocalizedMessage(), ex);
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
