package dev.ordy.erp.business.step;


import dev.ordy.erp.business.business.Business;
import dev.ordy.erp.business.business.BusinessService;
import dev.ordy.erp.finance.itemPrice.ItemPriceService;
import dev.ordy.erp.inventory.inventoryItem.InventoryItemService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class StepEventListener implements ApplicationListener<StepCreateEvent> {

    private final BusinessService businessService;



    public StepEventListener(ItemPriceService itemPriceService, InventoryItemService inventoryItemService, BusinessService businessService) {
        this.businessService = businessService;
    }

    @Transactional
    public void onApplicationEvent(StepCreateEvent event) {
        Step step = event.getStep();
        Long businessId = step.getBusiness().getId();
        Optional<Business> optionalBusiness=businessService.getBusinessById(businessId);

        if (optionalBusiness.isPresent()) {
            Business business = optionalBusiness.get();
        } else {
            // Handle the case where the business is not found
            System.err.println("Business not found for ID: " + businessId);
        }

        // Log a message
        System.out.println("Item Creation Event is completely done");
    }
}

