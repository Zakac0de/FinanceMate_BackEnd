package com.example.FinanceMate.service;

import com.example.FinanceMate.model.User;
import com.example.FinanceMate.repository.UserRepository;
import com.example.FinanceMate.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    // Registration
    @Transactional
    public User register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email is already in use!");

        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username is already in use!");
        }
        // In real production, the password would be encrypted here
        return userRepository.save(user);
    }

    // Login
    public String login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPasswordHash().equals(password)) {
            throw new RuntimeException("Incorrect password");
        }

        // Returning a "token"
        return jwtUtil.generateToken(username);
    }
}