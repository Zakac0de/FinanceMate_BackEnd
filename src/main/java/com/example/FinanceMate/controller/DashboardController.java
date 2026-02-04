package com.example.FinanceMate.controller;

import com.example.FinanceMate.dto.CategoryTotalDTO;
import com.example.FinanceMate.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    @Autowired
    private TransactionService transactionService;

    // Endpoint: GET /api/v1/dashboard/summary?userId=1&month=2&year=2026
    @GetMapping("/summary")
    public ResponseEntity<List<CategoryTotalDTO>> getMonthlySummary(
            @RequestParam Long userId,
            @RequestParam int month,
            @RequestParam int year) {
        
        List<CategoryTotalDTO> summary = transactionService.getCategoryTotals(userId, month, year);
        return ResponseEntity.ok(summary);
    }
}