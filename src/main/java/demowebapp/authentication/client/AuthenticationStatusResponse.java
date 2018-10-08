package demowebapp.authentication.client;

import lombok.Data;

@Data
public class AuthenticationStatusResponse {
    private String state;
    private String result;
    private String signature;
    private String cert;
}
