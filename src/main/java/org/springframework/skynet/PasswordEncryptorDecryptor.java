package org.springframework.skynet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class PasswordEncryptorDecryptor {

    @Value("${secretKey}")
    private String secretKeyEncoded;

    public String encryptPassword(String password) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        byte[] decodedKey = Base64.getDecoder().decode(secretKeyEncoded);
        SecretKeySpec secretKeySpec = new SecretKeySpec(decodedKey, "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        byte[] encryptedBytes = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decryptPassword(String encryptedPassword) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, BadPaddingException {
        byte[] decodedKey = Base64.getDecoder().decode(secretKeyEncoded);
        SecretKeySpec secretKeySpec = new SecretKeySpec(decodedKey, "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));

        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
