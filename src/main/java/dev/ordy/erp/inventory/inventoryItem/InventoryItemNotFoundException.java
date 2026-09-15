package dev.ordy.erp.inventory.inventoryItem;

class InventoryItemNotFoundException extends dev.ordy.erp.common.ResourceNotFoundException {

    InventoryItemNotFoundException(Long id) {
        super("Could not find inventory item " + id);
    }
}
