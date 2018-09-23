package com.blue.bankservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoAccountFoundException extends Exception {
    public NoAccountFoundException() {
    }

    public NoAccountFoundException(Long customerId) {
        super("No account found for customer with id " + customerId);
    }
}
