package dev.ordy.erp.business.account;

class AccountNotFoundException extends dev.ordy.erp.common.ResourceNotFoundException {

    AccountNotFoundException(Long id) {
        super("Could not find account " + id);
    }
}
