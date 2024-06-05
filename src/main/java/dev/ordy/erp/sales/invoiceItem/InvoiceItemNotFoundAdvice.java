package dev.ordy.erp.sales.invoiceItem;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class InvoiceItemNotFoundAdvice {

    @ExceptionHandler(InvoiceItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String invoiceItemNotFoundHandler(InvoiceItemNotFoundException ex) {
        return ex.getMessage();
    }
}
