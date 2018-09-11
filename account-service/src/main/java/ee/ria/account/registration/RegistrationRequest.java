package ee.ria.account.registration;

import lombok.Data;

@Data
public class RegistrationRequest {
    private String deviceId;
    private String nationalIdentityNumber;
    private String surname;
    private String givenName;
}
