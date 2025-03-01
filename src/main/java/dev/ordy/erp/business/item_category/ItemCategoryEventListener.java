package dev.ordy.erp.business.item_category;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ItemCategoryEventListener implements ApplicationListener<ItemCategoryCreateEvent> {

    public ItemCategoryEventListener() {
    }

    @Override
    @Transactional
    public void onApplicationEvent(ItemCategoryCreateEvent event) {
        ItemCategory itemCategory = event.getItemCategory();

        // Log category creation
        System.out.println("Item Category Creation Event: Created category " + itemCategory.getName() +
                " for business ID: " + itemCategory.getBusiness().getId());

        // Additional business logic can be added here as needed
        // For example, if you need to integrate with other systems or perform additional actions

        System.out.println("Item Category Creation Event is completely done");
    }
}