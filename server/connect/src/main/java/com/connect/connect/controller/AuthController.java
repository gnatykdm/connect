/*
 * @author Gnatyk Dmytro
 * This file is part of CONNECT.
 *
 * CONNECT is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CONNECT is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CONNECT. If not, see <https://www.gnu.org/licenses/>.
 */

package com.connect.connect.controller;

import com.connect.connect.model.dto.UserDTO;
import com.connect.connect.model.entity.User;
import com.connect.connect.model.service.user.IUserService;
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
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        if (userDTO == null) {
            return ResponseEntity.badRequest().body("Invalid user data");
        }

        User user = new User(userDTO.getUsername(), userDTO.getEmail(), userDTO.getPassword(), LocalDateTime.now());
        userServiceImpl.createUser(user);
        return ResponseEntity.ok(userServiceImpl.getUserByUsername(user.getUsername()));
    }

    @GetMapping("/check-auth-code/{code}")
    public ResponseEntity<String> checkAuthCode(@PathVariable Integer code) {
        if (code == null) {
            return ResponseEntity.badRequest().body("Auth code cannot be null");
        }

        if (code.equals(generateAuthCode())) {
            return ResponseEntity.ok("Code is correct");
        }
        return ResponseEntity.status(404).body("Code is incorrect");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String username, @RequestParam String password) {
        logger.info("Logging in user: {}", username);

        boolean validation = userServiceImpl.loginUserValidation(username, password);
        if (validation) {
            logger.info("User logged in successfully: {}", username);
            User user = userServiceImpl.getUserByUsername(username);
            return ResponseEntity.ok(user);
        }
        logger.info("User login failed: {}", username);
        return ResponseEntity.status(401).body("Invalid username or password");
    }

    private int generateAuthCode() {
        return (int) (Math.random() * 1000000);
    }
}
