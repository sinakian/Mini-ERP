package dev.ordy.erp.supply.supplyRequest;

class SupplyRequestNotFoundException extends RuntimeException {

    SupplyRequestNotFoundException(Long id) {
        super("Could not find supply request " + id);
    }
}
