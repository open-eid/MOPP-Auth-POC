package ee.ria.deviceresponse.authentication.client;

import lombok.Data;

@Data
public class Session {
    private String sessionId;
    private String hash;
    private String nationalIdentityNumber;
    private String result;
    private String signature;
    private String cert;
}
