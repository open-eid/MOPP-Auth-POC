package ee.ria.session.controller;

import lombok.Data;

@Data
public class SessionRequest{
    private String sessionId;
    private String hash;
    private String nationalIdentityNumber;
    private String result;
    private String signature;
    private String signatureAlgorithm;
    private String cert;
}
