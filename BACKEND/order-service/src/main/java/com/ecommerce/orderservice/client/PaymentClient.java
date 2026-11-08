package com.ecommerce.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PAYMENT-SERVICE")
public interface PaymentClient {

    @PostMapping("/api/payments/process")
    void processPayment(@RequestBody Object paymentRequest);

    public class PaymentRequest {
        public PaymentRequest(String orderNumber, Double totalAmount) {
        }
    }
}