package com.travel.deals.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("deal")
public class Deal {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String dealName;
    private String description;
    private String type;
    private Double discount;
    private String imageUrl;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
    private Integer priority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
