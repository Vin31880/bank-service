package com.blue.bankservice.controller;

import com.blue.bankservice.domain.Account;
import com.blue.bankservice.exceptions.NoAccountFoundException;
import com.blue.bankservice.exceptions.SecondaryAccountAlreadyExistException;
import com.blue.bankservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
public class AccountController {

    static final String BASE_URL = "/api/v1";

    static final String OPEN_SECONDARY_ACCOUNT_URL = BASE_URL + "/account/secondary/{customerId}/{initialCredit}";

    private AccountService accountService;

    public AccountController(@Autowired AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(path = OPEN_SECONDARY_ACCOUNT_URL, produces = APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<Account> createSecondaryAccount(@PathVariable("customerId") Long customerId,
                                                   @PathVariable("initialCredit") Double initialCredit)
            throws NoAccountFoundException, SecondaryAccountAlreadyExistException {
        if (customerId == null || initialCredit == null) {
            return (ResponseEntity<Account>) ResponseEntity.badRequest();
        }
        return ResponseEntity.status(CREATED).body(accountService.createSecondaryAccount(customerId, initialCredit));
    }

}
