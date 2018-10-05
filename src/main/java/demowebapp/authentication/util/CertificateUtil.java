package demowebapp.authentication.util;

import demowebapp.authentication.exception.CertificateException;
import demowebapp.authentication.exception.InvalidCommonNameException;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class CertificateUtil {

    public static List<String> getCommonNameValues(String base64Certificate) {
        RDN rdn;
        try {
            X509Certificate certificate = generateCertificateX509Object(base64Certificate);
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
        return commonNameValues;
    }

    public static X509Certificate generateCertificateX509Object(String base64Certificate) throws java.security.cert.CertificateException {
        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(base64Certificate));
        return (X509Certificate) factory.generateCertificate(inputStream);
    }

    public static String getFullNameFromCommonName(List<String> commonNameValues){
        return removeLastChar(commonNameValues.get(1)) + " " + removeLastChar(commonNameValues.get(0));
    }

    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }
}
