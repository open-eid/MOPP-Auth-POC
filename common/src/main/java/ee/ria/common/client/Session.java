package ee.ria.common.client;

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
