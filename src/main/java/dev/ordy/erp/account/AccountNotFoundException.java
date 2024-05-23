package dev.ordy.erp.account;

class AccountNotFoundException extends RuntimeException {

    AccountNotFoundException(Long id) {
        super("Could not find employee " + id);
    }
}
