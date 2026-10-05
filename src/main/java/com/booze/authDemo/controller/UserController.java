package com.booze.authDemo.controller;

import com.booze.authDemo.dtos.LoginRequest;
import com.booze.authDemo.dtos.RegisterRequest;
import com.booze.authDemo.dtos.UserResponse;
import com.booze.authDemo.service.JwtService;
import com.booze.authDemo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody RegisterRequest userInput) {
        UserResponse user = userService.register(userInput);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDetails> loginUser(@RequestBody LoginRequest userInput) {
        UserDetails user = userService.login(userInput);

        String jwtToken = jwtService.generateToken(user);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/")
    public List<UserResponse> getUsers() {
        return userService.getUsers();
    }
}
