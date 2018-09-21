package ee.ria.relyingparty.authentication;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private String nationalIdentityNumber;
    private String hash;
}
