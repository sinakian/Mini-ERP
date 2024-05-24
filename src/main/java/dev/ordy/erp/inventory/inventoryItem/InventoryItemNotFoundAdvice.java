package dev.ordy.erp.inventory.inventoryItem;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class InventoryItemNotFoundAdvice {

    @ExceptionHandler(InventoryItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String inventoryItemNotFoundHandler(InventoryItemNotFoundException ex) {
        return ex.getMessage();
    }
}
