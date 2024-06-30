package dev.ordy.erp.inventory.inventoryAvailableTransaction;

import dev.ordy.erp.inventory.inventoryItem.InventoryItem;
import dev.ordy.erp.inventory.inventoryItem.InventoryItemService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InventoryAvailableTransactionEventListener implements ApplicationListener<InventoryAvailableTransactionCreateEvent> {
    public InventoryItemService inventoryItemService;

    public InventoryAvailableTransactionEventListener(InventoryItemService inventoryItemService){
        this.inventoryItemService=inventoryItemService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(InventoryAvailableTransactionCreateEvent event){
        InventoryAvailableTransaction transaction=event.getInventoryAvailableTransaction();
        Long inventoryItemId=transaction.getInventoryItem().getId();
        double newAvailableBalance=transaction.getNewBalance();

        //update Inventory Item Available Balance
        inventoryItemService.updateAvailableBalance(inventoryItemId,newAvailableBalance);

        System.out.println("Inventory available Creation Event is completely done");


    }






}

