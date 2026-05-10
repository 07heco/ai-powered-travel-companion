package com.travel.payment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "booking-service", url = "http://localhost:8083")
public interface BookingClient {

    @PutMapping("/booking/order/{id}/status")
    void updateOrderStatus(@PathVariable("id") Long orderId, @RequestParam("status") String status);
}
