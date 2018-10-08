package demowebapp.authentication.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
public class ErrorDetails {
    private String timestamp;
    private String errorCode;
    private String message;
}
