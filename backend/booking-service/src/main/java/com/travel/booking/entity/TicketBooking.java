package com.travel.booking.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;

@Data
@TableName("ticket_booking")
public class TicketBooking {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long bookingId;
    private String attractionName;
    private String ticketType;
    private Integer ticketCount;
    private LocalDate visitDate;
    private String visitorInfo;
}