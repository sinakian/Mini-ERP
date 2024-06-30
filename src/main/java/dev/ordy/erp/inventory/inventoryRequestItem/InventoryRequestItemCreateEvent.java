package dev.ordy.erp.inventory.inventoryRequestItem;

import org.springframework.context.ApplicationEvent;

public class InventoryRequestItemCreateEvent extends ApplicationEvent{
    private final InventoryRequestItem inventoryrequestItem;

    public InventoryRequestItemCreateEvent(Object source, InventoryRequestItem inventoryrequestItem) {
        super(source);
        this.inventoryrequestItem = inventoryrequestItem;
    }

    public InventoryRequestItem getInventoryRequestItem() {
        return inventoryrequestItem;
    }
}
