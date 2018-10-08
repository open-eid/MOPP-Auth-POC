package demowebapp.authentication.client;

import lombok.Data;

@Data
public class BeginAuthenticationResponse {
    private String sessionId;
    private String errorCode;
}
