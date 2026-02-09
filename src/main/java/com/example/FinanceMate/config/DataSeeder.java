package com.example.FinanceMate.config;

import com.example.FinanceMate.model.Category;
import com.example.FinanceMate.model.CategoryType;
import com.example.FinanceMate.model.User;
import com.example.FinanceMate.repository.CategoryRepository;
import com.example.FinanceMate.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner initData(CategoryRepository categoryRepository, UserRepository userRepository) {
        return args -> {
            // 1. Create categories if they don't exist
            if (categoryRepository.count() == 0) {
                createCategories(categoryRepository);
            }

            // 2. Create a test user if there are no users
            if (userRepository.count() == 0) {
                createTestUser(userRepository);
            }
        };
    }

    private void createCategories(CategoryRepository repository) {
        // Expenses
        List<String> expenses = Arrays.asList(
            "Food", "Housing", "Transportation", "Entertainment", 
            "Health", "Shopping", "Restaurants", "Other expenses"
        );
        
        // Income
        List<String> incomes = Arrays.asList(
            "Salary", "Dividends", "Gifts", "Other income"
        );

        // Save the expenses
        for (String name : expenses) {
            Category c = new Category();
            c.setName(name);
            c.setType(CategoryType.EXPENSE);
            c.setIconName(mapIcon(name)); // Set the icon by name
            repository.save(c);
        }

        // Save the income
        for (String name : incomes) {
            Category c = new Category();
            c.setName(name);
            c.setType(CategoryType.INCOME);
            c.setIconName("wallet");
            repository.save(c);
        }
        
        System.out.println("--- DATABASE FORMATTED WITH CATEGORIES ---");
    }

    private void createTestUser(UserRepository repository) {
        User user = new User();
        user.setUsername("demo");
        user.setEmail("demo@financemate.com");
        // Note: Since AuthService currently compares the raw text, we will store it like this.
        // Later, when we implement BCrypt in AuthService, this will need to be changed.
        user.setPasswordHash("demo123"); 
        
        repository.save(user);
        System.out.println("--- TEST USER CREATED (demo / demo123) ---");
    }

    // Method for selecting icons
    private String mapIcon(String categoryName) {
        switch (categoryName) {
            case "Food": return "shopping-cart";
            case "Housing": return "home";
            case "Transportation": return "car";
            case "Entertainment": return "film";
            case "Health": return "heartbeat";
            case "Restaurants": return "utensils";
            default: return "tag";
        }
    }
}