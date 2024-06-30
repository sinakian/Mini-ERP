package dev.ordy.erp.inventory.inventoryRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class InventoryRequestNotFoundAdvice {

    @ExceptionHandler(InventoryRequestNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String inventoryRequestNotFoundHandler(InventoryRequestNotFoundException ex) {
        return ex.getMessage();
    }
}
