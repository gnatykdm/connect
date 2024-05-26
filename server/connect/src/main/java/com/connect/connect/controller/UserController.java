package com.connect.connect.controller;

import com.connect.connect.model.entity.User;
import com.connect.connect.model.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> login(@RequestBody User user) {
        userService.createUser(user);
        return ResponseEntity.ok("User created");
    }

    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestBody User user) {
        userService.updateUser(user);
        return ResponseEntity.ok("User updated");
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> delete(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Integer userId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/find")
    public ResponseEntity<User> login(@RequestParam String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @GetMapping("/login")
    public ResponseEntity<User> login(@RequestParam String username, @RequestParam String password) {
        return ResponseEntity.ok(userService.getUserByUsernameAndPassword(username, password));
    }
}
