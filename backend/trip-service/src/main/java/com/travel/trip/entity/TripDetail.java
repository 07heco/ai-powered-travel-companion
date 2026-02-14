package com.travel.trip.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@TableName("trip_detail")
public class TripDetail {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long tripId;
    private Integer day;
    private LocalDate date;
    private String title;
    private String description;
    private String location;
    private String address;
    private Double latitude;
    private Double longitude;
    private String activityType;
    private BigDecimal cost;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer status;
    @TableLogic
    private Integer deleted;
    private Long createdBy;
    private Long updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}