package dev.ordy.erp.inventory.inventoryAvailableTransaction;

class InventoryAvailableTransactionNotFoundException extends RuntimeException {

    InventoryAvailableTransactionNotFoundException(Long id) {
        super("Could not find inventory transaction " + id);
    }
}
