package dev.ordy.erp.inventory.inventoryTransaction;

class InventoryTransactionNotFoundException extends dev.ordy.erp.common.ResourceNotFoundException {

    InventoryTransactionNotFoundException(Long id) {
        super("Could not find inventory transaction " + id);
    }
}
