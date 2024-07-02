package dev.ordy.erp.inventory.inventoryRequest;

public class InventoryRequestNotFoundException extends RuntimeException {

    public InventoryRequestNotFoundException(Long id) {
        super("Could not find inventory Request " + id);
    }
}
