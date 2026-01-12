package com.example.FinanceMate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Disabling CSRF protection in the REST API (because we use tokens, not sessions)
            .csrf(csrf -> csrf.disable())
            
            // Defining access rights
            .authorizeHttpRequests(auth -> auth
                // Allow everyone to register and log in
                .requestMatchers("/api/v1/auth/**").permitAll()
                
                // Enable H2 database console (for testing only)
                .requestMatchers("/h2-console/**").permitAll()
                
                // All other requests require authentication
                .anyRequest().authenticated()
            )
            
            // H2 console requires this to work (frame-options)
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))
            
            // Set the session to stateless (the server does not remember the user, but relies on the token)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    // This bean is needed to encrypt passwords (for the future)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}