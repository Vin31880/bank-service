package com.blue.bankservice.service.builder;

import com.blue.bankservice.domain.Account;
import com.blue.bankservice.domain.Customer;
import com.blue.bankservice.domain.info.CustomerInfo;
import com.blue.bankservice.remote.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerInfoBuilder {
    private static final Logger logger = LoggerFactory.getLogger(CustomerInfoBuilder.class);

    public CustomerInfo build(Account account, Customer customer, List<Transaction> transactions) {
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setCustomerId(customer.getCustomerId());
        customerInfo.setAccountId(account.getAccountId());
        customerInfo.setFirstName(customer.getFirstName());
        customerInfo.setLastName(customer.getLastName());
        customerInfo.setBalance(account.getBalance());
        customerInfo.setTransactions(transactions);

        logger.info("Build customer info for customer Id " + customer.getCustomerId());
        return customerInfo;
    }
}
