package demowebapp.authentication.validator;

import demowebapp.authentication.exception.SignatureVerificationException;
import demowebapp.authentication.util.CertificateUtil;

import org.bouncycastle.util.encoders.Base64;
import org.springframework.stereotype.Service;

import java.security.Signature;
import java.security.cert.X509Certificate;

@Service
public class ValidatorService {

    public boolean isSignatureValid(String certificate, String signature, String hash)  {
        try {
            Signature sig = Signature.getInstance("NONEwithECDSAinP1363Format");
            X509Certificate x509Certificate = CertificateUtil.generateCertificateX509Object(certificate);
            sig.initVerify(x509Certificate);
            sig.update(java.util.Base64.getDecoder().decode(hash.getBytes()));
            byte[] decodedSignature = Base64.decode(signature);

            return sig.verify(decodedSignature);
        } catch (Exception e) {
            throw new SignatureVerificationException();
        }
    }
}
