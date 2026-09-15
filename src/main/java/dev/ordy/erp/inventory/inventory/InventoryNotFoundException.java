package dev.ordy.erp.inventory.inventory;

class InventoryNotFoundException extends dev.ordy.erp.common.ResourceNotFoundException {

    InventoryNotFoundException(Long id) {
        super("Could not find inventory " + id);
    }
}
