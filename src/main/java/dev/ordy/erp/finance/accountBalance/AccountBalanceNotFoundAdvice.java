package dev.ordy.erp.finance.accountBalance;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class AccountBalanceNotFoundAdvice {

    @ExceptionHandler(AccountBalanceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String accountBalanceNotFoundHandler(AccountBalanceNotFoundException ex) {
        return ex.getMessage();
    }
}
