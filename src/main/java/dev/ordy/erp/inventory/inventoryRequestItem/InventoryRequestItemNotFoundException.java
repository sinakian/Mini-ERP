package dev.ordy.erp.inventory.inventoryRequestItem;

class InventoryRequestItemNotFoundException extends dev.ordy.erp.common.ResourceNotFoundException {

    InventoryRequestItemNotFoundException(Long id) {
        super("Could not find inventory Request Item " + id);
    }
}
