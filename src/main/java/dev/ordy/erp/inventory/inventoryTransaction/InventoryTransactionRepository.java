package dev.ordy.erp.inventory.inventoryTransaction;

import org.springframework.data.jpa.repository.JpaRepository;

interface InventoryTransactionRepository extends JpaRepository<InventoryTransaction, Long> {

}
