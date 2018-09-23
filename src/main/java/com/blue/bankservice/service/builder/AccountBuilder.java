package com.blue.bankservice.service.builder;

import com.blue.bankservice.domain.Account;
import com.blue.bankservice.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AccountBuilder {
    private static final Logger logger = LoggerFactory.getLogger(AccountBuilder.class);

    public Account buildSecondaryAccount(Customer customer, double initialCredit) {
        Account accountToCreate = new Account();
        accountToCreate.setAccountType("SECONDARY");
        accountToCreate.setCustomer(customer);
        accountToCreate.setBalance(initialCredit);

        logger.info("Build account");
        return accountToCreate;
    }

}
