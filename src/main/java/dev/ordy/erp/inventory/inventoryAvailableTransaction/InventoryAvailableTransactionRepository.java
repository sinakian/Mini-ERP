package dev.ordy.erp.inventory.inventoryAvailableTransaction;

import dev.ordy.erp.inventory.inventoryTransaction.InventoryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryAvailableTransactionRepository extends JpaRepository<InventoryAvailableTransaction, Long> {

}
