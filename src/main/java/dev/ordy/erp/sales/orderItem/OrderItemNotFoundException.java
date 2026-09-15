package dev.ordy.erp.sales.orderItem;

class OrderItemNotFoundException extends dev.ordy.erp.common.ResourceNotFoundException {

    OrderItemNotFoundException(Long id) {
        super("Could not find order item " + id);
    }
}
