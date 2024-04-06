package org.springframework.skynet;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UsersService {

    private static final Logger logger = LoggerFactory.getLogger(UsersService.class);


    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoderUtil passwordEncoderUtil;


    @Transactional
    public void saveUser(Users user) {
        logger.info("Saving user: {}", user.getEmail());
        usersRepository.save(user);
        logger.info("User saved with ID: {}", user.getId());
    }

//    public void registerUser(Users user) throws Exception {
//        String encodedPassword = passwordEncoderUtil.encodePassword(user.getPassword());
//        user.setPassword(encodedPassword);
//        usersRepository.save(user);
//    }


}

