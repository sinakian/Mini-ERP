package dev.ordy.erp.inventory.inventoryReceiptItem;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class InventoryReceiptItemNotFoundAdvice {

    @ExceptionHandler(InventoryReceiptItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String inventoryReceiptNotFoundHandler(InventoryReceiptItemNotFoundException ex) {
        return ex.getMessage();
    }
}
