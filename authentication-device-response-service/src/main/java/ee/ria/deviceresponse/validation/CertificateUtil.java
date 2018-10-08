package ee.ria.deviceresponse.validation;

import org.bouncycastle.util.encoders.Base64;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class CertificateUtil {
    public static X509Certificate generateCertificateX509Object(String base64Certificate) throws CertificateException {
        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.decode(base64Certificate));
        return  (X509Certificate) factory.generateCertificate(inputStream);
    }
}
