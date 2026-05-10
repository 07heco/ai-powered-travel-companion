package com.travel.ai.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("travel_mate")
public class TravelMate {
    @TableId(type = IdType.AUTO)
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
