package ee.ria.deviceresponse.authentication;

import lombok.Data;

@Data
public class Signature {
    private String value;
    private String algorithm;
}
