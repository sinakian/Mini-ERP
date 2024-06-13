package dev.ordy.erp.business.item;


import dev.ordy.erp.business.business.BusinessService;
import dev.ordy.erp.finance.itemPrice.ItemPriceService;
import dev.ordy.erp.finance.itemPrice.ItemPrice;
import dev.ordy.erp.business.business.Business;
import dev.ordy.erp.inventory.inventory.Inventory;
import dev.ordy.erp.inventory.inventoryItem.InventoryItem;
import dev.ordy.erp.inventory.inventoryItem.InventoryItemService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ItemEventListener implements ApplicationListener<ItemCreateEvent> {

    private final ItemPriceService itemPriceService;
    private final InventoryItemService inventoryItemService;



    public ItemEventListener(ItemPriceService itemPriceService, InventoryItemService inventoryItemService) {
        this.itemPriceService = itemPriceService;
        this.inventoryItemService = inventoryItemService;
    }

    @Transactional
    public void onApplicationEvent(ItemCreateEvent event) {
        Item item = event.getItem();
        Business business = item.getBusiness();
        System.out.println(business);
//        Inventory defaultProductInventory = business.getDefaultProductInventory();
//
//        // Create Item Price
//        ItemPrice itemPrice = itemPriceService.createItemPrice(item, 0.0, item.getUnit(),business.getCurrency());
//
//        // Create inventory item
//        InventoryItem inventoryItem= new InventoryItem(defaultProductInventory,item,0,0,item.getUnit(),item.getName(), item.getRole());
//        System.out.println(business);
//        System.out.println(inventoryItem);
//        inventoryItemService.createInventoryItem(inventoryItem);



        // Log a message
        System.out.println("Item Creation Event is completely done");
    }
}

