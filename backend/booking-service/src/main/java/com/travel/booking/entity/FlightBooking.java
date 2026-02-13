package com.travel.booking.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("flight_booking")
public class FlightBooking {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long bookingId;
    private String flightNo;
    private String airline;
    private String departureAirport;
    private String arrivalAirport;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String classType;
    private String passengerInfo;
}