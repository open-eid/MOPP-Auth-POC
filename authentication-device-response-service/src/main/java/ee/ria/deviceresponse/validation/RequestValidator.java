package ee.ria.deviceresponse.validation;

import ee.ria.deviceresponse.authentication.AuthenticationReplyRequest;
import ee.ria.deviceresponse.authentication.client.Session;
import ee.ria.deviceresponse.exception.CertificateException;
import ee.ria.deviceresponse.exception.InvalidCommonNameException;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;

@Component
public class RequestValidator {

    public boolean isRequestParametersValid(AuthenticationReplyRequest authenticationReplyRequest, Session session) {
        if (session == null) {
            return false;
        }
        if (!authenticationReplyRequest.getHash().equals(session.getHash())) {
            return false;
        }

        return getNationalIdentityNumberFromCertificate(authenticationReplyRequest.getCert()).equals(session.getNationalIdentityNumber());
    }

    private String getNationalIdentityNumberFromCertificate(String base64Certificate) {
        RDN rdn;
        try {
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.decode(base64Certificate));

            X509Certificate certificate = (X509Certificate) factory.generateCertificate(inputStream);
            X500Name x500name = new JcaX509CertificateHolder(certificate).getSubject();
            rdn = x500name.getRDNs(BCStyle.CN)[0];
        } catch (Exception e) {
            throw new CertificateException();
        }
        String commonName = IETFUtils.valueToString(rdn.getFirst().getValue());
        List<String> commonNameValues = Arrays.asList(commonName.split(","));
        if (commonNameValues.size() != 3) {
            throw new InvalidCommonNameException();
        }
        return commonNameValues.get(commonNameValues.size() - 1);
    }
}
