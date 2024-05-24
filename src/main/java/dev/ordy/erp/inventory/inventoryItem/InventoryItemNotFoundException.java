package dev.ordy.erp.inventory.inventoryItem;

class InventoryItemNotFoundException extends RuntimeException {

    InventoryItemNotFoundException(Long id) {
        super("Could not find inventory item " + id);
    }
}
