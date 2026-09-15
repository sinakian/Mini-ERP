package dev.ordy.erp.finance.accountBalance;

class AccountBalanceNotFoundException extends dev.ordy.erp.common.ResourceNotFoundException {

    AccountBalanceNotFoundException(Long id) {
        super("Could not find account balance " + id);
    }
}
