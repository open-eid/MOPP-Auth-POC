package demowebapp.authentication;

import lombok.Data;

@Data
public class VerificationResponse {
    private String verificationCode;
    private String hash;
}
