package com.ecommerce.productservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @GetMapping
    public List<String> getAllProducts() {
        return List.of("Laptop", "Smartphone", "Wireless Headphones", "Mechanical Keyboard");
    }

    @GetMapping("/check-stock")
    public boolean checkStock(@RequestParam String skuCode) {
        // For now, return true to simulate that the product is in stock
        // Later, you can check this against a database inventory table!
        return true;
    }
}