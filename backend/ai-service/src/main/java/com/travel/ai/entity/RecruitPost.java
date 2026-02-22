package com.travel.ai.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "recruit_post")
public class RecruitPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String authorName;
    private String destination;
    private String travelDate;
    private String duration;
    private Integer currentMembers;
    private Integer maxMembers;
    private String tags;
    private String budget;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}