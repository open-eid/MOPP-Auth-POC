package ee.ria.relyingparty.authentication;

import lombok.Data;

@Data
public class AuthenticationStatusResponse {
    private String state;
    private String result;
    private Signature signature;
    private String cert;
}
