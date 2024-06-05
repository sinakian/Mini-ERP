package dev.ordy.erp.sales.invoiceItem;

class InvoiceItemNotFoundException extends RuntimeException {

    InvoiceItemNotFoundException(Long id) {
        super("Could not find Invoice item " + id);
    }
}
