package ee.ria.auth;

import ee.ria.auth.messaging.MessagingRequest;
import ee.ria.auth.messaging.MessagingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired
    private MessagingService messagingService;

    @GetMapping("/auth")
    public void authenticate() {
        MessagingRequest request = new MessagingRequest("7AEC73ECFD5504B5DDA6FA9FC82708D4CA8BF7A10C98CAFD4DC717060EBCB900", "SHA256", "1237129-23423423-23423423-4324234");
        messagingService.message(request);
    }

    @PostMapping("/sendSignature")
    public void sendSignature(){
        //TODO: implementation
    }

}
