package dev.ordy.erp.finance.financialReceipt;

import dev.ordy.erp.sales.order.Order;
import org.springframework.context.ApplicationEvent;

public class FinancialReceiptConfirmEvent extends ApplicationEvent {

    private final FinancialReceipt financialReceipt;

    public FinancialReceiptConfirmEvent(Object source, FinancialReceipt financialReceipt) {
        super(source);
        this.financialReceipt = financialReceipt;
    }

    public FinancialReceipt getReceipt() {
        return financialReceipt;
    }
}
