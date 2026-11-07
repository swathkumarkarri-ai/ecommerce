package com.ecommerce.orderservice.controller;

import com.ecommerce.orderservice.client.InventoryClient;
import com.ecommerce.orderservice.client.PaymentClient;
import com.ecommerce.orderservice.dto.OrderRequest;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentClient paymentClient;

    @Autowired
    private InventoryClient inventoryClient;

    @PostMapping
    @CircuitBreaker(name = "inventory", fallbackMethod = "placeOrderFallback")
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest) {

        // 1. Check if the product is in stock via OpenFeign
        boolean isInStock = inventoryClient.isInStock(orderRequest.getSkuCode());

        if (!isInStock) {
            return new ResponseEntity<>("Product is out of stock", HttpStatus.BAD_REQUEST);
        }

        Order order = new Order();
        order.setOrderNumber(orderRequest.getOrderNumber());
        order.setSkuCode(orderRequest.getSkuCode());
        order.setPrice(BigDecimal.valueOf(orderRequest.getPrice()));
        order.setQuantity(orderRequest.getQuantity());

        // 2. Save order
        orderRepository.save(order);

        // 3. Trigger payment automatically via OpenFeign
        Double totalAmount = order.getPrice().doubleValue() * order.getQuantity();
        paymentClient.processPayment(new PaymentClient.PaymentRequest(order.getOrderNumber(), totalAmount));

        return new ResponseEntity<>("Order Placed Successfully", HttpStatus.CREATED);
    }

    // Fallback method when inventory or downstream service fails
    public ResponseEntity<String> placeOrderFallback(OrderRequest orderRequest, Throwable throwable) {
        return new ResponseEntity<>("Oops! Something went wrong. Please order later or inventory service is down.", HttpStatus.SERVICE_UNAVAILABLE);
    }
}