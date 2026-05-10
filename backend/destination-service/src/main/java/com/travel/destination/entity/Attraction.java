package com.travel.destination.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("attraction")
public class Attraction {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long destinationId;
    private String name;
    private String description;
    private String address;
    private String imageUrl;
    private Double latitude;
    private Double longitude;
    private Double rating;
    private String category;
    private String openingHours;
    private Double ticketPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
