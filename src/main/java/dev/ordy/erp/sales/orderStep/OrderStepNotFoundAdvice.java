package dev.ordy.erp.sales.orderStep;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class OrderStepNotFoundAdvice {

    @ExceptionHandler(OrderStepNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String orderItemNotFoundHandler(OrderStepNotFoundException ex) {
        return ex.getMessage();
    }
}
