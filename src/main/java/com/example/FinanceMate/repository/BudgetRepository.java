package com.example.FinanceMate.repository;

import com.example.FinanceMate.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long>{
    List<Budget> findByUserId(Long userId);

    // Check if there is already an active budget for this category
    Optional<Budget> findByUserIdAndCategoryId(Long userId, Long categoryId);
} 