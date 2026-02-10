package com.travel.ai.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("ai_plan")
public class AIPlan {
    @TableId(type = IdType.AUTO)
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
