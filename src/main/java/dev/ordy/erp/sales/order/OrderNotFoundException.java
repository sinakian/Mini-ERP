package dev.ordy.erp.sales.order;

class OrderNotFoundException extends RuntimeException {

    OrderNotFoundException(Long id) {
        super("Could not find order " + id);
    }
}
