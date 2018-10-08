package demowebapp.authentication.client;

import lombok.Data;

@Data
public class BeginAuthenticationRequest {
    private String hash;
    private String nationalIdentityNumber;
}
