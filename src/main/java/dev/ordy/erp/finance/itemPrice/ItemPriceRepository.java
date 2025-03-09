package dev.ordy.erp.finance.itemPrice;

import dev.ordy.erp.inventory.inventoryItem.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemPriceRepository extends JpaRepository<ItemPrice, Long> {
    Optional<ItemPrice> findByInventoryItem(InventoryItem inventoryItem);
}