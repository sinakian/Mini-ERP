package dev.ordy.erp.sales.orderStep;

class OrderStepNotFoundException extends dev.ordy.erp.common.ResourceNotFoundException {

    OrderStepNotFoundException(Long id) {
        super("Could not find order item " + id);
    }
}
