package ee.ria.deviceresponse;

import ee.ria.deviceresponse.authentication.AuthenticationReplyRequest;
import ee.ria.deviceresponse.authentication.AuthenticationReplyService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeviceResponseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceResponseController.class);

    @Autowired
    private AuthenticationReplyService authenticationReplyService;

    @PostMapping("authentication/reply")
    public void authenticate(@RequestBody AuthenticationReplyRequest request) throws Exception {
        LOGGER.info(request.toString());
        authenticationReplyService.processAuthenticationReply(request);
    }

}
