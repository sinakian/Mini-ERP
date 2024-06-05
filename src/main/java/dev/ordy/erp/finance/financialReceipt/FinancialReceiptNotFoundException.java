package dev.ordy.erp.finance.financialReceipt;

class FinancialReceiptNotFoundException extends RuntimeException {

    FinancialReceiptNotFoundException(Long id) {
        super("Could not find financial Receipt " + id);
    }
}
