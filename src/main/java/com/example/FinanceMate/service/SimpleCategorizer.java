package com.example.FinanceMate.service;

import org.springframework.stereotype.Service;

@Service
public class SimpleCategorizer implements Categorizer {

    @Override
    public String predictCategory(String description, Double amount) {
        if (description == null) return "Other"; // Default if no description
        
        String text = description.toLowerCase();

        // Rule-based detection
        // NOTE: The return strings must match the Category names in your Database exactly!
        
        if (text.contains("k-market") || text.contains("lidl") || text.contains("s-market") || text.contains("alepa")) {
            return "Food"; 
        } else if (text.contains("bensa") || text.contains("abc") || text.contains("neste") || text.contains("shell") || text.contains("hsl"))  {
            return "Transportation"; 
        } else if (text.contains("netflix") || text.contains("spotify") || text.contains("hbo")) {
            return "Entertainment";
        }

        return "Other"; // Default if nothing matches
    }
}