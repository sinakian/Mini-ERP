package dev.ordy.erp.inventory.inventoryRequest;

class InventoryRequestNotFoundException extends RuntimeException {

    InventoryRequestNotFoundException(Long id) {
        super("Could not find inventory Request " + id);
    }
}
