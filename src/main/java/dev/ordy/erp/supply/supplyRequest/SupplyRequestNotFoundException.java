package dev.ordy.erp.supply.supplyRequest;

class SupplyRequestNotFoundException extends dev.ordy.erp.common.ResourceNotFoundException {

    SupplyRequestNotFoundException(Long id) {
        super("Could not find supply request " + id);
    }
}
