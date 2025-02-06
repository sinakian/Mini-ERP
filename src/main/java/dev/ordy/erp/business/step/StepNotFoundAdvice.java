package dev.ordy.erp.business.step;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class StepNotFoundAdvice {

    @ExceptionHandler(StepNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String ItemNotFoundHandler(StepNotFoundException ex) {
        return ex.getMessage();
    }
}
