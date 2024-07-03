package dev.ordy.erp.sales.invoice;

import org.springframework.context.ApplicationEvent;

public class InvoiceCreateEvent extends ApplicationEvent {

    private final Invoice invoice;

    public InvoiceCreateEvent(Object source, Invoice invoice) {
        super(source);
        this.invoice = invoice;
    }

    public Invoice getInvoice() {
        return invoice;
    }
}
