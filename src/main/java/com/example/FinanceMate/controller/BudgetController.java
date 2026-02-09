package com.example.FinanceMate.controller;

import com.example.FinanceMate.model.Budget;
import com.example.FinanceMate.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/budgets")
@CrossOrigin(origins = "*")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @GetMapping
    public ResponseEntity<List<Budget>> getUserBudgets(@RequestParam Long userId) {
        return ResponseEntity.ok(budgetService.findAllByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<?> createBudget(@RequestBody Budget budget) {
        try {
            return new ResponseEntity<>(budgetService.save(budget), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Budget> updateBudget(@PathVariable Long id, @RequestBody Budget budgetDetails) {
        return ResponseEntity.ok(budgetService.update(id, budgetDetails));
    }
}