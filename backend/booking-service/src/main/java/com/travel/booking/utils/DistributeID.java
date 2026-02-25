package com.travel.booking.utils;

import cn.hutool.core.util.IdUtil;
import org.springframework.stereotype.Component;

public class DistributeID {
    public static String generateOrderNo(long workerId) {
        long id = IdUtil.getSnowflake(workerId).nextId();
        return "ORD" + id;
    }
}
