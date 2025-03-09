package dev.ordy.erp.finance.tax;

class TaxNotFoundException extends RuntimeException {

    TaxNotFoundException(Long id) {
        super("Could not find tax " + id);
    }

    TaxNotFoundException(String code) {
        super("Could not find tax with code " + code);
    }
}