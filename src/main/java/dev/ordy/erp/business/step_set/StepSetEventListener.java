package dev.ordy.erp.business.step_set;


import dev.ordy.erp.business.business.Business;
import dev.ordy.erp.business.business.BusinessService;
import dev.ordy.erp.finance.itemPrice.ItemPriceService;
import dev.ordy.erp.inventory.inventoryItem.InventoryItemService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class StepSetEventListener implements ApplicationListener<StepSetCreateEvent> {

    private final BusinessService businessService;



    public StepSetEventListener(BusinessService businessService) {
        this.businessService = businessService;
    }

    @Transactional
    public void onApplicationEvent(StepSetCreateEvent event) {
        StepSet stepSet = event.getStepSet();
        Long businessId = stepSet.getBusiness().getId();
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

