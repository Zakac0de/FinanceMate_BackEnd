package com.example.FinanceMate.controller;

import jakarta.validation.Valid;
import com.example.FinanceMate.dto.TransactionDTO;
import com.example.FinanceMate.model.Transaction;
import com.example.FinanceMate.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // GET /api/v1/transactions?userId=1
    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAllTransactions(@RequestParam Long userId) {
        return ResponseEntity.ok(transactionService.findAllByUserId(userId));
    }

    // POST /api/v1/transactions
    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@Valid @RequestBody Transaction transaction) {
    // Note: Here we still take in an Entity (because it's easy),
    // but we return a safe DTO.
        TransactionDTO created = transactionService.save(transaction);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // DELETE /api/v1/transactions/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // Reporting: Retrieves monthly transactions
    // GET /api/v1/transactions/filter?userId=1&month=12&year=2024
    @GetMapping("/filter")
    public ResponseEntity<List<TransactionDTO>> getMonthlyTransactions(
            @RequestParam Long userId,
            @RequestParam int month,
            @RequestParam int year) {
        return ResponseEntity.ok(transactionService.findMonthly(userId, month, year));
    }
}