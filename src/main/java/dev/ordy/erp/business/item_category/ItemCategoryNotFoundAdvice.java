package dev.ordy.erp.business.item_category;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class ItemCategoryNotFoundAdvice {

    @ExceptionHandler(ItemCategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String ItemCategoryNotFoundHandler(ItemCategoryNotFoundException ex) {
        return ex.getMessage();
    }
}