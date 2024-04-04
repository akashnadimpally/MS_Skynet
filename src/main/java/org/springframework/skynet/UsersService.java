package org.springframework.skynet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.skynet.Users;
import org.springframework.skynet.UsersRepository;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    public void saveUser(Users user) {
        usersRepository.save(user);
    }
}

