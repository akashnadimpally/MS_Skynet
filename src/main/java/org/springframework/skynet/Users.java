package org.springframework.skynet;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;


import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

////    @Value("${secretKey}")
//    private String secretKey = "6CB3260FBAA93FC3AEC051E25B5A9EF5";

    private static final Logger logger = LoggerFactory.getLogger(PasswordEncryptorDecryptor.class);

    @Column(name="password")
    private String password;

    @Column(name = "contact_number", unique = true)
    private String contactNumber;

    private transient String phone_code;
    private transient String phone;

    @Transient
    private PasswordEncryptorDecryptor passwordEncryptorDecryptor;

    @Autowired
    public void setPasswordEncryptorDecryptor(PasswordEncryptorDecryptor passwordEncryptorDecryptor) {
        this.passwordEncryptorDecryptor = passwordEncryptorDecryptor;
    }


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

    public void setPassword(String password) throws Exception {

        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        try {
            PasswordEncryptorDecryptor encryptorpasswd = new PasswordEncryptorDecryptor();
            this.password = encryptorpasswd.encryptPassword(password);
        } catch (Exception e) {
            logger.error("Error setting password: ", e);
            throw e; // Re-throw the exception to ensure it's not swallowed silently.
        }

    }

}
