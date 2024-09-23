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

import com.connect.connect.model.entity.User;
import com.connect.connect.model.service.user.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-management")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> delete(@PathVariable Integer userId) {
        logger.info("Deleting user with ID: {}", userId);
        try {
            userService.deleteUserById(userId);
            return ResponseEntity.ok("User deleted");
        } catch (Exception e) {
            logger.error("Error deleting user: ", e);
            return ResponseEntity.status(500).body("Error deleting user");
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Integer userId) {
        logger.info("Getting user with ID: {}", userId);
        User user = userService.getUserById(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        logger.info("Getting all users");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/find")
    public ResponseEntity<User> getUserByName(HttpServletRequest req) {
        String username = req.getParameter("username");

        User user = userService.getUserByUsername(username);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PostMapping("/update-name/{id}")
    public ResponseEntity<String> updateUserName(HttpServletRequest req, @PathVariable Integer id) {
        String userName = req.getParameter("username");

        if (userName != null) {
            userService.updateUserName(id, userName);
            return ResponseEntity.ok("UserName was Update");
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/update-email/{id}")
    public ResponseEntity<String> updateUserEmail(@PathVariable Integer id, HttpServletRequest req) {
        String userEmail = req.getParameter("email");

        if (userEmail != null) {
            userService.updateUserName(id, userEmail);
            return ResponseEntity.ok("UserEmail was Update");
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/update-password/{id}")
    public ResponseEntity<String> updateUserPassword(@PathVariable Integer id, HttpServletRequest req) {
        String userPassword = req.getParameter("password");

        if (userPassword != null) {
            userService.updateUserPassword(id, userPassword);
            return ResponseEntity.ok("UserPassword was Update");
        }
        return ResponseEntity.notFound().build();
    }
}
