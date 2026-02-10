package com.travel.destination.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("destination")
public class Destination {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String country;
    private String city;
    private String description;
    private String coverImage;
    private Double latitude;
    private Double longitude;
    private Integer popularScore;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
