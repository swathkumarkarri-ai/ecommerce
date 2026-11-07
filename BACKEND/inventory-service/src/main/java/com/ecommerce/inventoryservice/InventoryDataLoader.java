package com.ecommerce.inventoryservice.repository;

import com.ecommerce.inventoryservice.entity.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryDataLoader implements CommandLineRunner {

    private final InventoryRepository inventoryRepository;

    @Override
    public void run(String... args) throws Exception {
        Inventory inventory1 = new Inventory();
        inventory1.setSkuCode("LAPTOP-01");
        inventory1.setQuantity(100);

        Inventory inventory2 = new Inventory();
        inventory2.setSkuCode("PHONE-02");
        inventory2.setQuantity(0); // Out of stock example

        inventoryRepository.save(inventory1);
        inventoryRepository.save(inventory2);
    }
}