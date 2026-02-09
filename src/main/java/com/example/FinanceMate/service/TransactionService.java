package com.example.FinanceMate.service;

import com.example.FinanceMate.dto.TransactionDTO;
import com.example.FinanceMate.model.Transaction;
import com.example.FinanceMate.repository.TransactionRepository;
import com.example.FinanceMate.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import com.example.FinanceMate.dto.CategoryTotalDTO;
import java.util.Map;
import java.util.ArrayList;

@Service
public class TransactionService {

    @Autowired
    private Categorizer categorizer; 
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public List<TransactionDTO> findAllByUserId(Long userId) {
        List<Transaction> transactions = transactionRepository.findByUserIdOrderByTransactionDateDesc(userId);
    
        // Change each Transaction -> TransactionDTO
        return transactions.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    // The save also returns a safe DTO
    public TransactionDTO save(Transaction transaction) {
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
        
        // 2. Saving to database
        Transaction savedTransaction = transactionRepository.save(transaction);
        
        // 3. Return as DTO
        return mapToDTO(savedTransaction);
    }

    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }

    public List<TransactionDTO> findMonthly(Long userId, int month, int year) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.plusMonths(1).minusDays(1);
        List<Transaction> transactions = transactionRepository.findByUserIdAndTransactionDateBetween(userId, start, end);
        
        return transactions.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    
    private TransactionDTO mapToDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setAmount(transaction.getAmount());
        dto.setDescription(transaction.getDescription());
        dto.setTransactionDate(transaction.getTransactionDate());
        
      // Secure User ID
        if (transaction.getUser() != null) {
            dto.setUserId(transaction.getUser().getId());
        }

       // Category information (if any)
        if (transaction.getCategory() != null) {
            dto.setCategoryName(transaction.getCategory().getName());
            dto.setCategoryIcon(transaction.getCategory().getIconName());
        }

        return dto;
    }


    public List<CategoryTotalDTO> getCategoryTotals(Long userId, int month, int year) {
        // 1. First, retrieve the events of the selected month (using an existing method)
        List<TransactionDTO> transactions = findMonthly(userId, month, year);

        // 2. Using Java Stream for Grouping (Grouping By)
        // This is like SQL's "GROUP BY category"
        Map<String, BigDecimal> groupedData = transactions.stream()
            .collect(Collectors.groupingBy(
                t -> t.getCategoryName() != null ? t.getCategoryName() : "other", // If there is no category, we will put "Other"
                Collectors.mapping(
                    TransactionDTO::getAmount,
                    Collectors.reducing(BigDecimal.ZERO, BigDecimal::add) // Sum the prices
                )
            ));

        // 3. Change Map -> List<CategoryTotalDTO>
        List<CategoryTotalDTO> result = new ArrayList<>();
        groupedData.forEach((name, total) -> {
            result.add(new CategoryTotalDTO(name, total));
        });

        return result;
    }
}