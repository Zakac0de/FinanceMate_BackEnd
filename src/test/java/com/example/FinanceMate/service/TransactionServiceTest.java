package com.example.FinanceMate.service;

import com.example.FinanceMate.dto.TransactionDTO;
import com.example.FinanceMate.model.Category;
import com.example.FinanceMate.model.Transaction;
import com.example.FinanceMate.model.User;
import com.example.FinanceMate.repository.CategoryRepository;
import com.example.FinanceMate.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) //Enable Mockito
public class TransactionServiceTest {

    @Mock // "Fake" database connection
    private TransactionRepository transactionRepository;

    @Mock // "Fake" categorizer
   private Categorizer categorizer; 

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks // This is the class we are actually testing (it uses the fakes above)
    private TransactionService transactionService;

    private Transaction testTransaction;
    private User testUser;

    @BeforeEach
    void setUp() {
        // Create test data before each test
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testUser");

        testTransaction = new Transaction();
        testTransaction.setAmount(new BigDecimal("50.00"));
        testTransaction.setDescription("TestPurchase");
        testTransaction.setTransactionDate(LocalDate.now());
        testTransaction.setUser(testUser);
    }

    @Test
    void testSaveTransaction_ReturnsDTO() {
        // 1. DEFINE EXPECTATIONS (Given)
        // When repository.save() is called, return our testTransaction with ID 100
        Transaction savedEntity = testTransaction;
        savedEntity.setId(100L);
        
        when(transactionRepository.save(any(Transaction.class))).thenReturn(savedEntity);

        // 2. RUN TEST (When)
        TransactionDTO result = transactionService.save(testTransaction);

        // 3. CHECK THE RESULT (Then)
        assertNotNull(result); // Tulos ei saa olla null
        assertEquals(100L, result.getId()); // ID:n pitää olla sama
        assertEquals(new BigDecimal("50.00"), result.getAmount()); // Summan pitää täsmätä
        assertEquals(1L, result.getUserId()); // UserID pitää olla oikein (DTO-logiikka)
        
        // Make sure that the repository save command was called exactly once
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void testSave_WithMissingCategory_UsesAI() {
        // This test verifies that "Intelligence" works!

        // 1. Situation: The event has no category, but the description is "Prism"
        testTransaction.setCategory(null);
        testTransaction.setDescription("Prisma ostokset");

        Category foodCategory = new Category();
        foodCategory.setName("food");
        foodCategory.setIconName("food-icon");

        // Let's teach the Mocks what to do:
        // When asked, the fortune teller answers "food"
        when(categorizer.predictCategory(anyString(), anyDouble())).thenReturn("food");
        
        // When searching the database for categories, a list containing food
        // Note: Because of the stream() in the code, we need to mock findAll()
        when(categoryRepository.findAll()).thenReturn(java.util.List.of(foodCategory));
        
        when(transactionRepository.save(any(Transaction.class))).thenReturn(testTransaction);

        // 2. Execute
        TransactionDTO result = transactionService.save(testTransaction);

        // 3. Checking: "AI" must have set the category
        assertEquals("food", result.getCategoryName());
        
        // Verify that the predictor was actually used
        verify(categorizer, times(1)).predictCategory(contains("Prisma"), anyDouble());
    }
}