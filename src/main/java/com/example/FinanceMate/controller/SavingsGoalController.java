package com.example.FinanceMate.controller;

import com.example.FinanceMate.model.SavingsGoal;
import com.example.FinanceMate.service.SavingsGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/savings")
@CrossOrigin(origins = "*")
public class SavingsGoalController {

    @Autowired
    private SavingsGoalService savingsService;

    @GetMapping
    public ResponseEntity<List<SavingsGoal>> getGoals(@RequestParam Long userId) {
        return ResponseEntity.ok(savingsService.findAllByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<SavingsGoal> createGoal(@RequestBody SavingsGoal goal) {
        return ResponseEntity.ok(savingsService.save(goal));
    }

    // POST /api/v1/savings/{id}/deposit?amount=50.00
    @PostMapping("/{id}/deposit")
    public ResponseEntity<SavingsGoal> depositToGoal(@PathVariable Long id, @RequestParam Double amount) {
        return ResponseEntity.ok(savingsService.addDeposit(id, amount));
    }
}