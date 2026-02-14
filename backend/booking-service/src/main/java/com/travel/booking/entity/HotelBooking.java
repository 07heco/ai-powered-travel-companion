package com.travel.booking.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("hotel_booking")
public class HotelBooking {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long bookingId;
    private String hotelName;
    private String hotelAddress;
    private String roomType;
    private Integer roomCount;
    private String guestInfo;
}