package ee.ria.deviceresponse.validation;

import ee.ria.common.exception.SignatureValidationException;
import ee.ria.deviceresponse.authentication.AuthenticationReplyRequest;

import org.bouncycastle.util.encoders.Base64;
import org.springframework.stereotype.Component;

import java.security.Signature;
import java.security.cert.X509Certificate;

@Component
public class SignatureValidator {

    public boolean isSignatureValid(AuthenticationReplyRequest request) {
        try {

            Signature sig = Signature.getInstance("NONEwithECDSAinP1363Format");
            X509Certificate certificate = CertificateUtil.generateCertificateX509Object(request.getCert());
            sig.initVerify(certificate);
            sig.update(request.getHash().getBytes());
            byte[] signature = Base64.decode(request.getSignature());

            return sig.verify(signature);
        } catch (Exception e) {
            throw new SignatureValidationException();
        }
    }
}
