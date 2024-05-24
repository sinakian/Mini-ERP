package dev.ordy.erp.inventory.inventoryReceipt;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class InventoryReceiptNotFoundAdvice {

    @ExceptionHandler(InventoryReceiptNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String inventoryReceiptNotFoundHandler(InventoryReceiptNotFoundException ex) {
        return ex.getMessage();
    }
}
