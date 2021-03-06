package by.larchanka.tiptopcleaning.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static by.larchanka.tiptopcleaning.util.CommonConstant.SHA;

public class CryptorSHA256 {
    private static final Logger logger = LogManager.getLogger();

    /**
     * Encrypts string parameter
     *
     * @param password a {@code String} object to encrypt from
     * @return encrypted {@code String} object
     */
    public static String cryptWithSHA256(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance(SHA);
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }
}
