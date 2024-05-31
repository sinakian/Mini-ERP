package dev.ordy.erp.supply.supplyRequestItem;

class SupplyRequestItemNotFoundException extends RuntimeException {

    SupplyRequestItemNotFoundException(Long id) {
        super("Could not find supply request item " + id);
    }
}
