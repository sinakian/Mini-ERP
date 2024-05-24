package dev.ordy.erp.inventory.inventoryReceipt;

class InventoryReceiptNotFoundException extends RuntimeException {

    InventoryReceiptNotFoundException(Long id) {
        super("Could not find inventory Receipt " + id);
    }
}
