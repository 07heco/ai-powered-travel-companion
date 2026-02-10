package com.travel.booking.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("booking")
public class Booking {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long tripId;
    private String bookingType;
    private String bookingName;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private Integer adultCount;
    private Integer childCount;
    private Double totalPrice;
    private String status;
    private String paymentStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
