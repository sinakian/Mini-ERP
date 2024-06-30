package dev.ordy.erp.inventory.inventoryRequestItem;

class InventoryRequestItemNotFoundException extends RuntimeException {

    InventoryRequestItemNotFoundException(Long id) {
        super("Could not find inventory Request Item " + id);
    }
}
