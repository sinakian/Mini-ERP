package dev.ordy.erp.business.item;


import org.springframework.context.ApplicationEvent;

public class ItemCreateEvent extends ApplicationEvent {
    private final Item item;

    public ItemCreateEvent(Object source, Item item) {
        super(source);
        this.item = item;
    }

    public Item getItem() {
        return item;
    }
}
