package com.example.SmartComplaintRouter.service;

import com.example.SmartComplaintRouter.model.User;
import com.example.SmartComplaintRouter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // ================= BASIC CRUD =================

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // ================= SESSION MANAGEMENT =================

    private final ConcurrentHashMap<String, String> sessions = new ConcurrentHashMap<>();

    // store token after login
    public void storeSession(String email, String token) {
        sessions.put(token, email);
    }

    // MAIN AUTH METHOD
    public User getUserByToken(String token) {

        String email = sessions.get(token);

        if (email == null) {
            throw new RuntimeException("Invalid session. Please login again.");
        }

        User user = getUserByEmail(email);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        return user;
    }
}
