package dev.ordy.erp.inventory;

import org.springframework.data.jpa.repository.JpaRepository;

interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {

}
