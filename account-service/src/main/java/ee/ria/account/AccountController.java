package ee.ria.account;

import ee.ria.account.registration.RegistrationRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @PostMapping("register")
    public void register(@RequestBody RegistrationRequest request) {

    }
}
