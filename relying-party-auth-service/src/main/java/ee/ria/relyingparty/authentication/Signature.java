package ee.ria.relyingparty.authentication;

import lombok.Data;

@Data
public class Signature {
    private String value;
    private String algorithm;
}
