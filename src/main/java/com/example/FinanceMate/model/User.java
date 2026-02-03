package com.example.FinanceMate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Username cannot be empty.")
    @Size(min = 3, max = 50, message = "Username must be 3-50 characters long.")
    private String username;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Email is required")
    @Email(message = "The email format is invalid.") 
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    private String currency = "EUR";
    private String language = "fi";

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}