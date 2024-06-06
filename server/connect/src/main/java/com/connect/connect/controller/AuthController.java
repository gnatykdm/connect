package com.connect.connect.controller;

import com.connect.connect.model.dto.UserDTO;
import com.connect.connect.model.entity.User;
import com.connect.connect.model.service.user.IUserService;
import com.connect.connect.model.service.user.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private IUserService userServiceImpl;

    @PostMapping("/register")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        if (userDTO == null) {
            return ResponseEntity.badRequest().body("Invalid user data");
        }

        User user = new User(userDTO.getUsername(), userDTO.getEmail(), userDTO.getPassword(), LocalDateTime.now());
        userServiceImpl.createUser(user);
        return ResponseEntity.ok(userServiceImpl.getUserByUsername(user.getUsername()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String username, @RequestParam String password) {
        logger.info("Logging in user: {}", username);

        boolean validation = userServiceImpl.loginUserValidation(username, password);
        if (validation) {
            logger.info("User login successfully: {}", username);

            User user = userServiceImpl.getUserByUsername(username);
            return ResponseEntity.ok(user);
        }
        logger.info("User login failed: {}", username);
        return ResponseEntity.notFound().build();
    }
}
