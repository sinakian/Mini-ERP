package dev.ordy.erp.business.business;

import org.springframework.context.ApplicationEvent;

public class BusinessEvent extends ApplicationEvent {
    private final Business business;

    public BusinessEvent(Object source, Business business) {
        super(source);
        this.business = business;
    }

    public Business getBusiness() {
        return business;
    }
}
