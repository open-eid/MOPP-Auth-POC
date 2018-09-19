package ee.ria.deviceresponse.authentication;

import lombok.Data;

@Data
public class AuthenticationReplyRequest {
    private String result;
    private String sessionId;
    private Signature signature;
    private String hash;
    private String cert;
}
