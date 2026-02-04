package com.example.SmartComplaintRouter.controller;

import com.example.SmartComplaintRouter.dto.LoginRequest;
import com.example.SmartComplaintRouter.model.User;
import com.example.SmartComplaintRouter.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.SmartComplaintRouter.dto.RegisterRequest;


import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:8086") // Allow frontend
public class UserController {

    @Autowired
    private UserService userService;

    // ---------------- LOGIN (UNCHANGED) ----------------
    @PostMapping("/login")
    public User login(@RequestBody LoginRequest loginRequest) {
        User user = userService.getUserByEmail(loginRequest.getEmail());

        if (user == null || !user.getPassword().equals(loginRequest.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return user;
    }

    // ---------------- REGISTER NEW USER ----------------

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {

        if (userService.getUserByEmail(request.getEmail()) != null) {
            throw new RuntimeException("Email already registered");
        }

        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new RuntimeException("Password cannot be empty");
        }

        User user = new User();
        user.setName(request.getName() != null ? request.getName().trim() : null);
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole() != null && !request.getRole().isBlank()
                     ? request.getRole().toLowerCase()
                     : "user");

        return userService.saveUser(user);
    }


    // ---------------- OTHER EXISTING METHODS ----------------
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/test")
    public String test() {
        return "USER CONTROLLER WORKING";
    }
}
