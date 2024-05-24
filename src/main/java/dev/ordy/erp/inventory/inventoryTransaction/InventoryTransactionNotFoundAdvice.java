package dev.ordy.erp.inventory.inventoryTransaction;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class InventoryTransactionNotFoundAdvice {

    @ExceptionHandler(InventoryTransactionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String inventoryTransactionNotFoundHandler(InventoryTransactionNotFoundException ex) {
        return ex.getMessage();
    }
}
