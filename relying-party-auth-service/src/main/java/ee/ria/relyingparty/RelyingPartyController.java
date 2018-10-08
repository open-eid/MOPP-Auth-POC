package ee.ria.relyingparty;

import ee.ria.relyingparty.authentication.AuthenticationRequest;
import ee.ria.relyingparty.authentication.AuthenticationResponse;
import ee.ria.relyingparty.authentication.AuthenticationService;
import ee.ria.relyingparty.authentication.AuthenticationStatusResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RelyingPartyController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/authentication")
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest request) {
        return authenticationService.activateAuthentication(request);
    }

    @GetMapping("/authentication/session/{sessionId}")
    public ResponseEntity<AuthenticationStatusResponse> getSession(@PathVariable String sessionId) {
        return new ResponseEntity<>(authenticationService.getStatusResponse(sessionId), HttpStatus.OK);
    }

}
