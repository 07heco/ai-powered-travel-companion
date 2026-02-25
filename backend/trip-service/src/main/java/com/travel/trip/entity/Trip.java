package com.travel.trip.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("trip")
public class Trip {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String tripName;
    private String tripDesc;
    private Long userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String destination;
    private String theme;
    private BigDecimal budget;
    private Integer isPublic;
    private Integer isShared;
    private Integer collaboratorCount;
    private Integer commentCount;
    private Integer viewCount;
    private Integer status;
    private Integer enabled;
    @TableLogic
    private Integer deleted;
    private Long createdBy;
    private Long updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}