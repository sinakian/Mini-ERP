package dev.ordy.erp.inventory.inventoryTransaction;


import org.springframework.context.ApplicationEvent;

public class InventoryTransactionCreateEvent extends ApplicationEvent {
    private final InventoryTransaction inventoryTransaction;

    public InventoryTransactionCreateEvent(Object source, InventoryTransaction inventoryTransaction) {
        super(source);
        this.inventoryTransaction = inventoryTransaction;
    }

    public InventoryTransaction getInventoryTransaction() {
        return inventoryTransaction;
    }
}
