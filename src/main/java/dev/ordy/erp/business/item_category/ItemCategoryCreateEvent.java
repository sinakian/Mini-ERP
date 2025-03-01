package dev.ordy.erp.business.item_category;

import org.springframework.context.ApplicationEvent;

public class ItemCategoryCreateEvent extends ApplicationEvent {
    private final ItemCategory itemCategory;

    public ItemCategoryCreateEvent(Object source, ItemCategory itemCategory) {
        super(source);
        this.itemCategory = itemCategory;
    }

    public ItemCategory getItemCategory() {
        return itemCategory;
    }
}