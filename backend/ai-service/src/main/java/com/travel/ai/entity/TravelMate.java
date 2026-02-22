package com.travel.ai.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "travel_mate")
public class TravelMate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String name;
    private String avatar;
    private String personality;
    private String expertise;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
