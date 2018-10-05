package demowebapp.authentication;

import org.apache.commons.codec.digest.DigestUtils;

public class DigestCalculator {
    public static byte[] calculateDigest(byte[] dataToDigest, String hashType) {
        return DigestUtils.getDigest(hashType).digest(dataToDigest);
    }
}
