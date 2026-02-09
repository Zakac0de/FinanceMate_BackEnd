package com.example.FinanceMate.service;

import com.example.FinanceMate.model.SavingsGoal;
import com.example.FinanceMate.repository.SavingsGoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class SavingsGoalService {

    @Autowired
    private SavingsGoalRepository savingsGoalRepository;

    public List<SavingsGoal> findAllByUserId(Long userId) {
        return savingsGoalRepository.findByUserId(userId);
    }

    public SavingsGoal save(SavingsGoal goal) {
        return savingsGoalRepository.save(goal);
    }

    // Method for adding money to a piggy bank
    public SavingsGoal addDeposit(Long goalId, Double amount) {
        SavingsGoal goal = savingsGoalRepository.findById(goalId)
            .orElseThrow(() -> new RuntimeException("Target not found"));

        BigDecimal deposit = BigDecimal.valueOf(amount);
        goal.setCurrentAmount(goal.getCurrentAmount().add(deposit));

        // Check if the goal is full
        if (goal.getCurrentAmount().compareTo(goal.getTargetAmount()) >= 0) {
            goal.setCompleted(true);
        }

        return savingsGoalRepository.save(goal);
    }
}