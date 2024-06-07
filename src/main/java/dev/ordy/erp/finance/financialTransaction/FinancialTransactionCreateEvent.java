package dev.ordy.erp.finance.financialTransaction;

import org.springframework.context.ApplicationEvent;

public class FinancialTransactionCreateEvent extends ApplicationEvent {
    private final FinancialTransaction transaction;

    public FinancialTransactionCreateEvent(Object source, FinancialTransaction transaction) {
        super(source);
        this.transaction = transaction;
    }

    public FinancialTransaction getTransaction() {
        return transaction;
    }
}

