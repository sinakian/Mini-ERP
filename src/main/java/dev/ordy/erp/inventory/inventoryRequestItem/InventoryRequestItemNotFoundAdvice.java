package dev.ordy.erp.inventory.inventoryRequestItem;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class InventoryRequestItemNotFoundAdvice {

    @ExceptionHandler(InventoryRequestItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String inventoryRequestNotFoundHandler(InventoryRequestItemNotFoundException ex) {
        return ex.getMessage();
    }
}
