package dev.ordy.erp.sales.order;

public class OrderItemChangeEvent {
    private final Long orderId;

    public OrderItemChangeEvent(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }
}
