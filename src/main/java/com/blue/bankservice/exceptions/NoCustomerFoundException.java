package com.blue.bankservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoCustomerFoundException extends Exception {
    public NoCustomerFoundException() {
    }

    public NoCustomerFoundException(Long customerId) {
        super("No Customer found with id " + customerId);
    }
}
