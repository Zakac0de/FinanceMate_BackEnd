package com.example.FinanceMate.repository;

import com.example.FinanceMate.model.SavingsGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SavingsGoalRepository extends JpaRepository<SavingsGoal, Long> {
    List<SavingsGoal> findByUserId(Long userId);
    
    // E.g. for Dashboard: "Find unfinished goals"
    List<SavingsGoal> findByUserIdAndIsCompletedFalse(Long userId);
}