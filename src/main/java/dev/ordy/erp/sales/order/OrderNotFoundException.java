package dev.ordy.erp.sales.order;

class OrderNotFoundException extends dev.ordy.erp.common.ResourceNotFoundException {

    OrderNotFoundException(Long id) {
        super("Could not find order " + id);
    }
}
