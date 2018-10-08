package ee.ria.relyingparty.authentication;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticationStatusResponse {
    private String state;
    private String result;
    private String signature;
    private String cert;
}
