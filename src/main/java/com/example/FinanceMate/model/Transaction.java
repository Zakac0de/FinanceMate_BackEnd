package com.example.FinanceMate.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;


    private String description;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false, precision = 19, scale = 2)
    @NotNull(message = "The amount is mandatory") 
    @Positive(message = "The sum must be greater than 0.") 
    private BigDecimal amount; 

    @Column(name = "transaction_date", nullable = false)
    @NotNull(message = "Date is required") 
    private LocalDate transactionDate;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}