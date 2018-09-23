package com.blue.bankservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class SecondaryAccountAlreadyExistException extends Exception {
    public SecondaryAccountAlreadyExistException() {
    }

    public SecondaryAccountAlreadyExistException(Long customerId) {
        super("Secondary account already exists for customer with id" + customerId);
    }
}
