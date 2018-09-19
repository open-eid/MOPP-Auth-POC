package ee.ria.relyingparty.authentication.client;

import lombok.Data;

@Data
public class SessionRequest{
    private String sessionId;
    private String hash;
    private String nationalIdentityNumber;
    private String result;
    private String signature;
    private String cert;
}
