package org.springframework.skynet;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;


import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @Column(name = "email", unique = true)
    private String email;

//    private String phoneCode;
//    private Long phone;

    @Column(name="country")
    private String country;

    @Value("${secretKey}")
    private String secretKey;

//    @Column(name="password")
    private String password;

    @Column(name = "contact_number", unique = true)
    private String contactNumber;

    private transient String phone_code;
    private transient String phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String phone_code, String phone) {
        this.contactNumber = phone_code + phone;
    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {

        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        // Generate AES Key
//        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
//        keyGen.init(128); // 128-bit AES
//        SecretKey secretKey = keyGen.generateKey();

//        String secretKey = "your-secure-key";
//        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
//
//        // Create Cipher instance and initialize it for encryption
//        Cipher cipher = Cipher.getInstance("AES");
//        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
//
//        byte[] encryptedBytes = cipher.doFinal(password.getBytes());

        PasswordEncryptorDecryptor encryptorpasswd = new PasswordEncryptorDecryptor();


        // Set encrypted password
//        this.password = Base64.getEncoder().encodeToString(encryptedBytes);
        this.password = encryptorpasswd.encryptPassword(password);
    }

}
