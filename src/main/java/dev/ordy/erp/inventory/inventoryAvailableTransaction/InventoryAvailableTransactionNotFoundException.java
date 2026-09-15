package dev.ordy.erp.inventory.inventoryAvailableTransaction;

class InventoryAvailableTransactionNotFoundException extends dev.ordy.erp.common.ResourceNotFoundException {

    InventoryAvailableTransactionNotFoundException(Long id) {
        super("Could not find inventory transaction " + id);
    }
}
