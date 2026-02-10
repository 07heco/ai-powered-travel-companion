package com.travel.trip.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("trip")
public class Trip {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String tripName;
    private String destination;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
    private String description;
    private String coverImage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
