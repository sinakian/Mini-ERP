package dev.ordy.erp.finance.accountBalance;

class AccountBalanceNotFoundException extends RuntimeException {

    AccountBalanceNotFoundException(Long id) {
        super("Could not find account balance " + id);
    }
}
