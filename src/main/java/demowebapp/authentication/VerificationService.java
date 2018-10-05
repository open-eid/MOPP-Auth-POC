package demowebapp.authentication;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class VerificationService {
    public VerificationResponse getVerificationResponse(){
        SecureRandom random = new SecureRandom();
        byte[] randomBytes = random.generateSeed(20);
        byte[] randomHash = DigestCalculator.calculateDigest(randomBytes, "SHA-256");
        byte[] base64EncodedHash = Base64.getEncoder().encode(randomHash);
        String verificationCode = VerificationCodeCalculator.calculate(base64EncodedHash);
        VerificationResponse verificationResponse = new VerificationResponse();

        verificationResponse.setHash(new String(base64EncodedHash));
        verificationResponse.setVerificationCode(verificationCode);
        return verificationResponse;
    }
}
