package dev.ordy.erp.inventory.inventoryReceiptItem;

class InventoryReceiptItemNotFoundException extends RuntimeException {

    InventoryReceiptItemNotFoundException(Long id) {
        super("Could not find inventory Receipt Item " + id);
    }
}
