package dev.ordy.erp.business.business;

class BusinessNotFoundException extends RuntimeException {

    BusinessNotFoundException(Long id) {
        super("Could not find business " + id);
    }
}
