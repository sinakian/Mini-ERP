package dev.ordy.erp.sales.order;

import org.springframework.context.ApplicationEvent;

public class OrderConfirmEvent extends ApplicationEvent {

    private final Order order;

    public OrderConfirmEvent(Object source, Order order) {
        super(source);
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}
