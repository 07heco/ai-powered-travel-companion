package com.travel.ai.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ar_landmark")
public class ARLandmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String englishName;
    private String category;
    private Double rating;
    private String description;
    private String history;
    private String openTime;
    private String ticketPrice;
    private String highlights;
    private String tips;
    private String location;
    private Double latitude;
    private Double longitude;
    private String imageUrl;
    private String recognitionKeywords;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}