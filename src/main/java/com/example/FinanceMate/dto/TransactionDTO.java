package com.example.FinanceMate.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionDTO {
    private Long id;
    private BigDecimal amount;
    private String description;
    private LocalDate transactionDate;
    
    // Simplify the category: not the whole thing, just the necessary ones
    private String categoryName; 
    private String categoryIcon; 
    
    // MOST IMPORTANT: No User object (with password), just an ID
    private Long userId; 
}