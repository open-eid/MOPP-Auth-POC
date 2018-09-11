package ee.ria.relyingparty;

import ee.ria.relyingparty.authentication.*;
import ee.ria.relyingparty.messaging.MessagingRequest;
import ee.ria.relyingparty.messaging.MessagingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RelyingPartyController {

    @Autowired
    private MessagingService messagingService;
    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/auth") //TODO: DELETE THIS ENDPOINT
    public void auth() {
        MessagingRequest request = new MessagingRequest("7AEC73ECFD5504B5DDA6FA9FC82708D4CA8BF7A10C98CAFD4DC717060EBCB900", HashType.SHA256, "1237129-23423423-23423423-4324234");
        messagingService.message(request);
    }

    @PostMapping("authentication")
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest request) {
        return authenticationService.activateActivation(request);
    }

    @GetMapping("/authentication/session/{session}")
    public ResponseEntity<AuthenticationStatusResponse> getSession(@PathVariable String session) {
        System.out.println(session);
        return new ResponseEntity<AuthenticationStatusResponse>(new AuthenticationStatusResponse(), HttpStatus.OK);
    }

}
