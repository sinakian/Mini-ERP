package dev.ordy.erp.inventory.inventoryItem;

import org.springframework.data.jpa.repository.JpaRepository;

interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {

}
