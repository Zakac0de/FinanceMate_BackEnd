package com.example.FinanceMate.repository;

import com.example.FinanceMate.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.time.LocalDate;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    // Gets all users all transactions, newest first. 
    List<Transaction> findByUserIdOrderByTransactionDateDesc(Long userId);

    // Retrieves transactions from a specific time range (e.g. monthly report)
    List<Transaction> findByUserIdAndTransactionDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
}