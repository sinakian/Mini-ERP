package dev.ordy.erp.supply.supplyRequestItem;

class SupplyRequestItemNotFoundException extends dev.ordy.erp.common.ResourceNotFoundException {

    SupplyRequestItemNotFoundException(Long id) {
        super("Could not find supply request item " + id);
    }
}
