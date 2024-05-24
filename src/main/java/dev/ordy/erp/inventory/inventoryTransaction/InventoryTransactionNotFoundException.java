package dev.ordy.erp.inventory.inventoryTransaction;

class InventoryTransactionNotFoundException extends RuntimeException {

    InventoryTransactionNotFoundException(Long id) {
        super("Could not find inventory transaction " + id);
    }
}
