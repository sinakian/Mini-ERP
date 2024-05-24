package dev.ordy.erp.inventory;

class InventoryNotFoundException extends RuntimeException {

    InventoryNotFoundException(Long id) {
        super("Could not find inventory " + id);
    }
}
