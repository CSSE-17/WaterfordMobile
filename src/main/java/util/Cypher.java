package util;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 *
 * @author Mahendra Tennakoon
 */
public class Cypher {

    /**
     * Generate MD5 hash from input string
     * @param text
     * @return
     */
    public static String generateMD5(String text) {
        String hashtext = "";
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(text.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            hashtext = bigInt.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashtext;
    }
}
