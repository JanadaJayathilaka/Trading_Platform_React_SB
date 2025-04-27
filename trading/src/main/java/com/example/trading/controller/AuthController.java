package com.example.trading.controller;

import com.example.trading.model.User;
import com.example.trading.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody User user){
        User newUser = new User();
        newUser.setFullName(user.getFullName());
        newUser.setPassword(user.getPassword());
        newUser.setEmail(user.getEmail());

        User savedUser = userRepository.save(newUser);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
}
