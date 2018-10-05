package demowebapp.authentication;

import java.nio.ByteBuffer;

public class VerificationCodeCalculator {

    public static String calculate(byte[] documentHash) {
        byte[] digest = DigestCalculator.calculateDigest(documentHash, "SHA-256");
        ByteBuffer byteBuffer = ByteBuffer.wrap(digest);
        int shortBytes = Short.SIZE / Byte.SIZE; // Short.BYTES in java 8
        int rightMostBytesIndex = byteBuffer.limit() - shortBytes;
        short twoRightmostBytes = byteBuffer.getShort(rightMostBytesIndex);
        int positiveInteger = ((int) twoRightmostBytes) & 0xffff;
        String code = String.valueOf(positiveInteger);
        String paddedCode = "0000" + code;
        return paddedCode.substring(code.length());
    }
}
