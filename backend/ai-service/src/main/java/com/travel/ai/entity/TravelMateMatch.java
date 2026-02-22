package com.travel.ai.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "travel_mate_match")
public class TravelMateMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long matchedUserId;
    private String destination;
    private String travelDate;
    private String travelStyle;
    private String interests;
    private Integer matchScore;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}