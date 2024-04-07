package org.springframework.skynet;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UsersService {

    private static final Logger logger = LoggerFactory.getLogger(UsersService.class);


    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional
    public void saveUser(Users user) throws Exception {
        logger.info("Saving user: {}", user.getEmail());
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        usersRepository.save(user);
        logger.info("User saved with ID: {}", user.getId());
    }


}

