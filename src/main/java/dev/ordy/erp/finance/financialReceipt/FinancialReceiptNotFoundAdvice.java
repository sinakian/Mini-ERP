package dev.ordy.erp.finance.financialReceipt;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class FinancialReceiptNotFoundAdvice {

    @ExceptionHandler(FinancialReceiptNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String financialReceiptNotFoundHandler(FinancialReceiptNotFoundException ex) {
        return ex.getMessage();
    }
}
