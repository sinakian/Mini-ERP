package dev.ordy.erp.sales.orderItem;

class OrderItemNotFoundException extends RuntimeException {

    OrderItemNotFoundException(Long id) {
        super("Could not find order item " + id);
    }
}
