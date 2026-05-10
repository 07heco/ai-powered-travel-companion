package com.travel.trip.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("trip_detail")
public class TripDetail {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long tripId;
    private Integer day;
    private String title;
    private String description;
    private String attractions;
    private String accommodation;
    private String transportation;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
