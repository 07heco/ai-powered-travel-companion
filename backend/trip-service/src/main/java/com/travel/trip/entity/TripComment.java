package com.travel.trip.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("trip_comment")
public class TripComment {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long tripId;
    private Long tripDetailId;
    private Long userId;
    private Long parentId;
    private String content;
    private Integer status;
    @TableLogic
    private Integer deleted;
    private Long createdBy;
    private Long updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}