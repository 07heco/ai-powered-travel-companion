package com.travel.ai.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "budget")
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String tripName;
    private Double totalBudget;
    private Double spentAmount;
    private String startDate;
    private String endDate;
    private String destination;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}