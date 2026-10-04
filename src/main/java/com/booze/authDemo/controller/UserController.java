package com.booze.authDemo.controller;

import com.booze.authDemo.model.User;
import com.booze.authDemo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User userInput) {
        User user = userService.register(userInput);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User userInput) {
        User user = userService.login(userInput);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
