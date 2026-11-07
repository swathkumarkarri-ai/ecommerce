package com.ecommerce.paymentservice.controller;

import com.ecommerce.paymentservice.model.Payment;
import com.ecommerce.paymentservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String processPayment(@RequestBody Payment payment) {
        payment.setPaymentStatus("SUCCESS");
        paymentRepository.save(payment);
        return "Payment processed successfully for order: " + payment.getOrderNumber();
    }
}
