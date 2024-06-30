package dev.ordy.erp.inventory.inventoryAvailableTransaction;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class InventoryAvailableTransactionNotFoundAdvice {

    @ExceptionHandler(InventoryAvailableTransactionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String inventoryTransactionNotFoundHandler(InventoryAvailableTransactionNotFoundException ex) {
        return ex.getMessage();
    }
}
