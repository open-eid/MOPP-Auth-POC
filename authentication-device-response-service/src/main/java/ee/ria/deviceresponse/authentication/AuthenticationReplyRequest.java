package ee.ria.deviceresponse.authentication;

import lombok.Data;

@Data
public class AuthenticationReplyRequest {
    private String result;
    private String sessionId;
    private String signature;
    private String hash;
    private String cert;
}
