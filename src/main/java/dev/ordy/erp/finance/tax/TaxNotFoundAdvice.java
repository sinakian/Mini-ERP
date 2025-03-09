package dev.ordy.erp.finance.tax;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class TaxNotFoundAdvice {

    @ExceptionHandler(TaxNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String taxNotFoundHandler(TaxNotFoundException ex) {
        return ex.getMessage();
    }
}