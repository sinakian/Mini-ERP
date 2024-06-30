package dev.ordy.erp.inventory.inventoryRequestItem;

import dev.ordy.erp.inventory.inventoryAvailableTransaction.InventoryAvailableTransactionService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InventoryRequestItemEventListener implements ApplicationListener<InventoryRequestItemCreateEvent> {

    private final InventoryAvailableTransactionService inventoryAvailableTransactionService;

    public InventoryRequestItemEventListener(InventoryAvailableTransactionService inventoryAvailableTransactionService){
        this.inventoryAvailableTransactionService=inventoryAvailableTransactionService;
    }


    @Override
    @Transactional
    public void onApplicationEvent(InventoryRequestItemCreateEvent event){
        InventoryRequestItem inventoryRequestItem=event.getInventoryRequestItem();
        inventoryAvailableTransactionService.createInventoryAvailableTransaction(inventoryRequestItem);
    }

}
