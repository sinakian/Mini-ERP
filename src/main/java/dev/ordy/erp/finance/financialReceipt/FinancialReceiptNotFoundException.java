package dev.ordy.erp.finance.financialReceipt;

class FinancialReceiptNotFoundException extends dev.ordy.erp.common.ResourceNotFoundException {

    FinancialReceiptNotFoundException(Long id) {
        super("Could not find financial Receipt " + id);
    }
}
