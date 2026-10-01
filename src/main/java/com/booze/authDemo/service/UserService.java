package com.booze.authDemo.service;

import com.booze.authDemo.model.User;
import com.booze.authDemo.repo.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder encoder;

    public UserService(UserRepo userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }


    public User register(User user) {
        String password = encoder.encode(user.getPassword());
        user.setPassword(password);

        return userRepo.save(user);
    }
}
