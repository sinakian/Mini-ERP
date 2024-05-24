package dev.ordy.erp.supply;

class SupplyRequestNotFoundException extends RuntimeException {

    SupplyRequestNotFoundException(Long id) {
        super("Could not find supply request " + id);
    }
}
