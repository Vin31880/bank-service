package com.blue.bankservice.service;

import com.blue.bankservice.domain.Account;
import com.blue.bankservice.domain.Customer;
import com.blue.bankservice.exceptions.NoAccountFoundException;
import com.blue.bankservice.exceptions.SecondaryAccountAlreadyExistException;
import com.blue.bankservice.repository.AccountRepository;
import com.blue.bankservice.service.builder.AccountBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    private AccountRepository accountRepository;
    private AccountBuilder accountBuilder;
    private TransactionService transactionService;

    public AccountService(@Autowired AccountBuilder accountBuilder,
                          @Autowired AccountRepository accountRepository,
                          @Autowired TransactionService transactionService) {
        this.accountBuilder = accountBuilder;
        this.accountRepository = accountRepository;
        this.transactionService = transactionService;
    }

    public Account createSecondaryAccount(Long customerId, double initialCredit) throws NoAccountFoundException, SecondaryAccountAlreadyExistException {
        if (isSecondaryAccountAlreadyExists(customerId)) {
            throw new SecondaryAccountAlreadyExistException(customerId);
        }
        Customer primaryAccountHolder = getPrimaryAccountDetailsByCustomerId(customerId).getCustomer();
        Account secondaryAccount = accountRepository.save(accountBuilder.buildSecondaryAccount(primaryAccountHolder,
                initialCredit));
        if (initialCredit > 0) {
            transactionService.addInitialTransactionToAccount(secondaryAccount.getAccountId(), initialCredit);
        }
        return secondaryAccount;

    }

    public Account findAccountByTypeAndCustomerId(String accountType, Long customerId) throws NoAccountFoundException {
        logger.info("Request to search for account with type " + accountType + " and customer Id " + customerId);

        Optional<Account> optional = accountRepository.findByAccountTypeAndCustomer_CustomerId(accountType, customerId);
        if (!optional.isPresent()) {
            throw new NoAccountFoundException(customerId);
        }
        return optional.get();
    }

    private boolean isSecondaryAccountAlreadyExists(Long customerId) {
        Optional<Account> optional = accountRepository.findByAccountTypeAndCustomer_CustomerId("SECONDARY", customerId);
        if (!optional.isPresent()) {
            return false;
        }
        return true;
    }

    private Account getPrimaryAccountDetailsByCustomerId(Long customerId) throws NoAccountFoundException {
        logger.info("Retrieve primary account details for customer Id " + customerId);

        return findAccountByTypeAndCustomerId("PRIMARY", customerId);
    }

}
