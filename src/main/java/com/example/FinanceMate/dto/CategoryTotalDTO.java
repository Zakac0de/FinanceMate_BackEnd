package com.example.FinanceMate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor // Automatically create a constructor (String, BigDecimal)
public class CategoryTotalDTO {
    private String categoryName;
    private BigDecimal totalAmount;
}