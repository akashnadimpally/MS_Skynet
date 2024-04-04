package org.springframework.skynet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


@Component
public class PasswordDecryptor {

    @Value("${secretKey}")
    private String secretKey;

    public String decrypt(String encryptedPassword) throws Exception {
        // Convert Base64 encoded string to byte array
        byte[] decodedKey = Base64.getDecoder().decode(secretKey);

        // Reconstruct the key using SecretKeySpec
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, originalKey);

        byte[] decodedBytes = Base64.getDecoder().decode(encryptedPassword);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);

        return new String(decryptedBytes);
    }
}
