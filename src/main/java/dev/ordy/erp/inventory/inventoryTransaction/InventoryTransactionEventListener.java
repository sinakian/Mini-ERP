package dev.ordy.erp.inventory.inventoryTransaction;

import dev.ordy.erp.inventory.inventoryAvailableTransaction.InventoryAvailableTransactionCreateEvent;

import dev.ordy.erp.inventory.inventoryItem.InventoryItemService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryTransactionEventListener {
    private final InventoryItemService inventoryItemService;

    public InventoryTransactionEventListener(InventoryItemService inventoryItemService) {
        this.inventoryItemService = inventoryItemService;
    }

    @EventListener
    public void InventoryTransactionCreateEvent(InventoryTransactionCreateEvent event) {

        System.out.println("Handling InInventoryTransactionCreateEvent: " + event);
        InventoryTransaction transaction=event.getInventoryTransaction();
        double newBalance=transaction.getNewBalance();
        inventoryItemService.changeBalance(transaction);

    }

}

