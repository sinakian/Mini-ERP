package dev.ordy.erp.inventory.inventoryAvailableTransaction;

import org.springframework.context.ApplicationEvent;

public class InventoryAvailableTransactionCreateEvent extends ApplicationEvent {
    private final InventoryAvailableTransaction inventoryAvailableTransaction;

    public InventoryAvailableTransactionCreateEvent(Object source,InventoryAvailableTransaction inventoryAvailableTransaction){
        super(source);
        this.inventoryAvailableTransaction=inventoryAvailableTransaction;
    }

    public InventoryAvailableTransaction getInventoryAvailableTransaction(){return inventoryAvailableTransaction;}
}
