package dev.ordy.erp.business.item;


import dev.ordy.erp.business.business.BusinessRepository;
import dev.ordy.erp.business.business.BusinessService;
import dev.ordy.erp.business.business_settings.BusinessSettings;
import dev.ordy.erp.business.business_settings.BusinessSettingsService;
import dev.ordy.erp.finance.itemPrice.ItemPriceService;
import dev.ordy.erp.finance.itemPrice.ItemPrice;
import dev.ordy.erp.business.business.Business;
import dev.ordy.erp.inventory.inventory.Inventory;
import dev.ordy.erp.inventory.inventoryItem.InventoryItem;
import dev.ordy.erp.inventory.inventoryItem.InventoryItemService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class ItemEventListener implements ApplicationListener<ItemCreateEvent> {

    private final ItemPriceService itemPriceService;
    private final InventoryItemService inventoryItemService;
    private final BusinessService businessService;
    private final BusinessSettingsService businessSettingsService;



    public ItemEventListener(ItemPriceService itemPriceService,
                             InventoryItemService inventoryItemService,
                             BusinessService businessService,
                             BusinessSettingsService businessSettingsService
    ) {
        this.itemPriceService = itemPriceService;
        this.inventoryItemService = inventoryItemService;
        this.businessService = businessService;
        this.businessSettingsService = businessSettingsService;
    }

    @Transactional
    public void onApplicationEvent(ItemCreateEvent event) {
        Item item = event.getItem();
        Long businessId = item.getBusiness().getId();
        Optional<Business> optionalBusiness=businessService.getBusinessById(businessId);

        if (optionalBusiness.isPresent()) {
            BusinessSettings businessSettings = businessSettingsService.getDefaultSettings(businessId);
            Inventory defaultProductInventory = businessSettings.getDefaultProductInventory();

            // Create Item Price
            ItemPrice itemPrice = itemPriceService.createItemPrice(item,
                    0.0,
                    item.getUnit(),
                    businessSettings.getCurrency());

            // Create inventory item
            InventoryItem inventoryItem= new InventoryItem(defaultProductInventory,item.getBusiness(),item,0,0,item.getUnit(),item.getName(), item.getRole());
            inventoryItemService.createInventoryItem(inventoryItem);
        } else {
            // Handle the case where the business is not found
            System.err.println("BusinessSettings not found for ID: " + businessId);
        }

        // Log a message
        System.out.println("Item Creation Event is completely done");
    }
}

