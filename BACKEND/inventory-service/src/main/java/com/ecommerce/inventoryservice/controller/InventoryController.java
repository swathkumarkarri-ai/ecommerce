package com.ecommerce.inventoryservice.controller;

import com.ecommerce.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryRepository inventoryRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@RequestParam String skuCode) {
        return inventoryRepository.findBySkuCode(skuCode)
                .map(inventory -> inventory.getQuantity() > 0)
                .orElse(false);
    }
}
