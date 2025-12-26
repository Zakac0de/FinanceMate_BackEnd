package com.example.FinanceMate.service;

import com.example.FinanceMate.model.Budget;
import com.example.FinanceMate.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    public List<Budget> findAllByUserId(Long userId) {
        return budgetRepository.findByUserId(userId);
    }

    public Budget save(Budget budget) {
       // Prevent double budgets for the same category
        Optional<Budget> existing = budgetRepository.findByUserIdAndCategoryId(
            budget.getUser().getId(),
            budget.getCategory().getId()
        );
        if (existing.isPresent() && (budget.getId() == null || !budget.getId().equals(existing.get().getId()))) {
            throw new RuntimeException("There is already a budget for this category!");
        }
        return budgetRepository.save(budget);
    }
}