package com.booze.authDemo.service;

import com.booze.authDemo.model.User;
import com.booze.authDemo.repo.UserRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;

    public UserService(UserRepo userRepo, PasswordEncoder encoder, AuthenticationManager authManager) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.authManager = authManager;
    }


    public User register(User user) {
        String password = encoder.encode(user.getPassword());
        user.setPassword(password);

        return userRepo.save(user);
    }

    public User login(User userInput) {
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(userInput.getEmail(), userInput.getPassword()));

        if(!auth.isAuthenticated()) {
            throw new BadCredentialsException("Invalid credentials");
        }

        User user = userRepo.findByEmail(userInput.getEmail());

        if(user == null) {
            throw new BadCredentialsException("Invalid credentials");
        }

        return user;
    }
}
