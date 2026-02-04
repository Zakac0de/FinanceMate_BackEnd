package com.example.FinanceMate.controller;

import jakarta.validation.Valid;

import com.example.FinanceMate.dto.LoginRequest;
import com.example.FinanceMate.dto.RegisterRequest;
import com.example.FinanceMate.dto.UserDTO;
import com.example.FinanceMate.model.User;
import com.example.FinanceMate.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*") // Allow all connections during development
public class AuthController {

    @Autowired
    private AuthService authService;

    // POST /api/v1/auth/register
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody RegisterRequest request) {
    // The GlobalExceptionHandler now handles errors, so try-catch is not necessarily needed here,
    // but if the AuthService throws a RuntimeException, the Handler will catch it.
        return ResponseEntity.ok(authService.register(request));
    }

    // POST /api/v1/auth/login
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}