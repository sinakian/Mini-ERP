package dev.ordy.erp.business.business;

import org.springframework.context.ApplicationEvent;

public class BusinessCreateEvent extends ApplicationEvent {
    private final Business business;

    public BusinessCreateEvent(Object source, Business business) {
        super(source);
        this.business = business;
    }

    public Business getBusiness() {
        return business;
    }
}
