package com.example.FinanceMate.service;

import com.example.FinanceMate.model.Transaction;
import com.example.FinanceMate.model.CategoryType;
import com.example.FinanceMate.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Map<String, Object> getSummary(Long userId) {
        List<Transaction> transactions = transactionRepository.findByUserIdOrderByTransactionDateDesc(userId);

        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;

        for (Transaction t : transactions) {
            if (t.getCategory() != null) {
                if (t.getCategory().getType() == CategoryType.INCOME) {
                    totalIncome = totalIncome.add(t.getAmount());
                } else {
                    totalExpense = totalExpense.add(t.getAmount());
                }
            }
        }

        BigDecimal balance = totalIncome.subtract(totalExpense);

        Map<String, Object> response = new HashMap<>();
        response.put("totalBalance", balance);
        response.put("totalIncome", totalIncome);
        response.put("totalExpense", totalExpense);
        
        // 5 last transactions
        int limit = Math.min(transactions.size(), 5);
        response.put("recentTransactions", transactions.subList(0, limit));

        return response;
    }
}