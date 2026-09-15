package dev.ordy.erp.finance.financialTransaction;

class FinancialTransactionNotFoundException extends dev.ordy.erp.common.ResourceNotFoundException {

    FinancialTransactionNotFoundException(Long id) {
        super("Could not find financial transaction " + id);
    }
}
