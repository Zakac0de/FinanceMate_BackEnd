package com.example.FinanceMate.service;

import com.example.FinanceMate.model.Transaction;
import com.example.FinanceMate.repository.TransactionRepository;
import com.example.FinanceMate.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private Categorizer categorizer; 
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> findAllByUserId(Long userId) {
        return transactionRepository.findByUserIdOrderByTransactionDateDesc(userId);
    }

    public Transaction save(Transaction transaction) {
        // If category is missing, try to predict it based on description
        if (transaction.getCategory() == null && transaction.getDescription() != null) {
            
            String predictedCategoryName = categorizer.predictCategory(
                transaction.getDescription(), 
                transaction.getAmount().doubleValue()
            );
            
            // Find the category from DB that matches the predicted name
            categoryRepository.findAll().stream()
                .filter(c -> c.getName().equalsIgnoreCase(predictedCategoryName))
                .findFirst()
                .ifPresent(transaction::setCategory);
        }
        
        return transactionRepository.save(transaction);
    }
    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }

    public List<Transaction> findMonthly(Long userId, int month, int year) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.plusMonths(1).minusDays(1);
        return transactionRepository.findByUserIdAndTransactionDateBetween(userId, start, end);
    }
}