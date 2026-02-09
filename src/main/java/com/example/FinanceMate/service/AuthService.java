package com.example.FinanceMate.service;

import com.example.FinanceMate.dto.LoginRequest;
import com.example.FinanceMate.dto.RegisterRequest;
import com.example.FinanceMate.dto.UserDTO;
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
    public UserDTO register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already in use!");

        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username is already in use!");
        }

       // 1. Convert DTO -> Entity (For Database)
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPasswordHash(request.getPassword()); // This will be done later with encoder.encode()

        User savedUser = userRepository.save(user);

        // 2. Convert Entity -> DTO (For retrieval, without password)
        return mapToDTO(savedUser);
    }

    // Login
    public String login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));


        if (!user.getPasswordHash().equals(request.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }

        return jwtUtil.generateToken(user.getUsername());
    }

    // Helper method for conversion
    private UserDTO mapToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }
}