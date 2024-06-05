package dev.ordy.erp.sales.invoice;

class InvoiceNotFoundException extends RuntimeException {

    InvoiceNotFoundException(Long id) {
        super("Could not find invoice " + id);
    }
}
