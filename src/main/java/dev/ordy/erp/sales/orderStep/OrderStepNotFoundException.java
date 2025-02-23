package dev.ordy.erp.sales.orderStep;

class OrderStepNotFoundException extends RuntimeException {

    OrderStepNotFoundException(Long id) {
        super("Could not find order item " + id);
    }
}
