package org.springframework.skynet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncryptorDecryptor encryptorDecryptor; // Use PasswordEncryptorDecryptor

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String rawPassword = authentication.getCredentials().toString();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        try {
            String decryptedPassword = encryptorDecryptor.decryptPassword(userDetails.getPassword()); // Use decryptPassword method
            if (!decryptedPassword.equals(rawPassword)) {
                throw new BadCredentialsException("Invalid username or password");
            }

            return new UsernamePasswordAuthenticationToken(username, decryptedPassword, userDetails.getAuthorities());
        } catch (Exception e) {
            throw new BadCredentialsException("Decryption failed", e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
