package dev.ordy.erp.inventory.inventoryRequest;

public class InventoryRequestNotFoundException extends dev.ordy.erp.common.ResourceNotFoundException {

    public InventoryRequestNotFoundException(Long id) {
        super("Could not find inventory Request " + id);
    }
}
