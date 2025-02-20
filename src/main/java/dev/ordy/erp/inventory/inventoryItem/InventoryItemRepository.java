package dev.ordy.erp.inventory.inventoryItem;

import dev.ordy.erp.business.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
    List<InventoryItem> findByBusinessId(Long businessId);

}
