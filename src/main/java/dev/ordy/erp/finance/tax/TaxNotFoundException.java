package dev.ordy.erp.finance.tax;

class TaxNotFoundException extends dev.ordy.erp.common.ResourceNotFoundException {

    TaxNotFoundException(Long id) {
        super("Could not find tax " + id);
    }

    TaxNotFoundException(String code) {
        super("Could not find tax with code " + code);
    }
}