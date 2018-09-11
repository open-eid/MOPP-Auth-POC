package ee.ria.deviceresponse;

import ee.ria.deviceresponse.authentication.AuthenticationReplyRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeviceResponseController {

    @PostMapping("authentication/reply")
    public void authenticate(@RequestBody AuthenticationReplyRequest request) {

    }
}
