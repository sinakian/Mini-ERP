package dev.ordy.erp.inventory.inventoryRequestItem;

import dev.ordy.erp.inventory.inventoryRequest.InventoryRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface InventoryRequestItemRepository extends JpaRepository<InventoryRequestItem, Long> {
    List<InventoryRequestItem> findByInventoryRequest(InventoryRequest inventoryRequest);
}
