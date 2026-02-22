package com.travel.ai.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ai_plan")
public class AIPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String planName;
    private String destination;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String planContent;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
