package com.ecommerce.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service")
public interface PaymentClient {

    @PostMapping("/api/payments")
    String processPayment(@RequestBody PaymentRequest paymentRequest);

    // DTO record/class for payment request
    record PaymentRequest(String orderNumber, Double amount) {}
}