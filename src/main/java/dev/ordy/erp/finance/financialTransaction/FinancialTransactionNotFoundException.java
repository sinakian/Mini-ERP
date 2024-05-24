package dev.ordy.erp.finance.financialTransaction;

class FinancialTransactionNotFoundException extends RuntimeException {

    FinancialTransactionNotFoundException(Long id) {
        super("Could not find financial transaction " + id);
    }
}
