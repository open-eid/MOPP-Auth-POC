package ee.ria.deviceresponse.validation;

import ee.ria.deviceresponse.authentication.AuthenticationReplyRequest;
import ee.ria.deviceresponse.exception.SignatureValidationException;

import org.bouncycastle.util.encoders.Base64;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

@Component
public class SignatureValidator {

    public boolean isSignatureValid(AuthenticationReplyRequest request) {
        try {
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.decode(request.getCert()));

            X509Certificate certificate = (X509Certificate) factory.generateCertificate(inputStream);
            Signature sig = Signature.getInstance(request.getSignature().getAlgorithm());

            sig.initVerify(certificate);
            sig.update(request.getHash().getBytes());
            byte[] signature = Base64.decode(request.getSignature().getValue());

            return sig.verify(signature);
        } catch (Exception e) {
            throw new SignatureValidationException();
        }
    }
}
