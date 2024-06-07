package dev.ordy.erp.finance.itemPrice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class ItemPriceNotFoundAdvice {

    @ExceptionHandler(ItemPriceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)

    String itemPriceNotFoundHandler(ItemPriceNotFoundException ex) {
        return ex.getMessage();
    }
}
