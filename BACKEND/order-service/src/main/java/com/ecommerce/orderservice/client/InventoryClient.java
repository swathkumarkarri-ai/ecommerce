package com.ecommerce.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "INVENTORY-SERVICE")
public interface InventoryClient {

    @PostMapping("/api/inventory/reduce")
    void reduceStock(@RequestParam("productId") Long productId, @RequestParam("quantity") int quantity);

    boolean isInStock(String skuCode);
}