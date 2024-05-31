package dev.ordy.erp.supply.supplyRequestItem;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class SupplyRequestItemNotFoundAdvice {

    @ExceptionHandler(SupplyRequestItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String supplyRequestItemNotFoundHandler(SupplyRequestItemNotFoundException ex) {
        return ex.getMessage();
    }
}
