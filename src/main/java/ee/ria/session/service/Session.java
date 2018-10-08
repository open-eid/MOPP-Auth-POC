package ee.ria.session.service;

import lombok.Data;

import java.io.Serializable;

@Data
public class Session implements Serializable {
    private String sessionId;
    private String hash;
    private String nationalIdentityNumber;
    private String result;
    private String signature;
    private String signatureAlgorithm;
    private String cert;
}
