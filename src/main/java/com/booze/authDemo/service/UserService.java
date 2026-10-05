package com.booze.authDemo.service;

import com.booze.authDemo.dtos.LoginRequest;
import com.booze.authDemo.dtos.RegisterRequest;
import com.booze.authDemo.dtos.UserResponse;
import com.booze.authDemo.model.User;
import com.booze.authDemo.repo.UserRepo;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

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

    public UserResponse register(RegisterRequest req) {
        String email = normalize(req.getEmail());

        if(userRepo.findByEmail(email) != null) {
            throw new BadCredentialsException("Invalid credentials");
        }
        User user = new User();
        user.setName(req.getName());
        user.setEmail(email);
        user.setPassword(encoder.encode(req.getPassword()));

        try {
            User saved = userRepo.save(user);
            return new UserResponse(saved.getId(), saved.getEmail());
        } catch (DataIntegrityViolationException e) {
            throw new BadCredentialsException("Invalid credentials");
        }
    }

    public UserDetails login(LoginRequest req) {
        Authentication auth = authManager.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(normalize(req.getEmail()), req.getPassword()));

        return (UserDetails) auth.getPrincipal();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        return userRepo.findAll().stream()
                .map(u -> new UserResponse(u.getId(), u.getEmail()))
                .toList();
    }

    private String normalize(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }
}
