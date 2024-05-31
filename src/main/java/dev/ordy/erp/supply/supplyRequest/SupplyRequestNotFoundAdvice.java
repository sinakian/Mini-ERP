package dev.ordy.erp.supply.supplyRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class SupplyRequestNotFoundAdvice {

    @ExceptionHandler(SupplyRequestNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String supplyRequestNotFoundHandler(SupplyRequestNotFoundException ex) {
        return ex.getMessage();
    }
}
