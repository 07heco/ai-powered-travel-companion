package com.travel.ai.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "expense")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long budgetId;
    private String category;
    private Double amount;
    private String description;
    private String date;
    private String paymentMethod;
    private LocalDateTime createdAt;
}