package dev.ordy.erp.business.business;

class BusinessNotFoundException extends dev.ordy.erp.common.ResourceNotFoundException {

    BusinessNotFoundException(Long id) {
        super("Could not find business " + id);
    }
}
