package ee.ria.session.service;

import lombok.Data;

import java.io.Serializable;

@Data
public class Session implements Serializable {
    private String sessionId;
    private String nationalIdentityNumber;
    private String result;
    private String signature;
    private String cert;
}
