package com.travel.trip.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("trip_collaborator")
public class TripCollaborator {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long tripId;
    private Long userId;
    private String role;
    private String permissions;
    private LocalDateTime joinedAt;
    private LocalDateTime updatedAt;
}
