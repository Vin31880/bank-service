package com.blue.bankservice.service;

import com.blue.bankservice.domain.Account;
import com.blue.bankservice.domain.Customer;
import com.blue.bankservice.domain.info.CustomerInfo;
import com.blue.bankservice.exceptions.NoAccountFoundException;
import com.blue.bankservice.exceptions.NoCustomerFoundException;
import com.blue.bankservice.remote.transaction.Transaction;
import com.blue.bankservice.repository.CustomerRepository;
import com.blue.bankservice.service.builder.CustomerInfoBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private CustomerRepository customerRepository;
    private AccountService accountService;
    private TransactionService transactionService;
    private CustomerInfoBuilder customerInfoBuilder;

    public CustomerService(@Autowired CustomerRepository customerRepository,
                           @Autowired AccountService accountService,
                           @Autowired TransactionService transactionService,
                           CustomerInfoBuilder customerInfoBuilder) {
        this.customerRepository = customerRepository;
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.customerInfoBuilder = customerInfoBuilder;
    }

    public Customer findCustomerById(Long customerId) throws NoCustomerFoundException {
        logger.info("Retrieve customer for customer Id " + customerId);

        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (!customerOptional.isPresent()) {
            throw new NoCustomerFoundException(customerId);
        }
        return customerOptional.get();
    }

    public CustomerInfo getCustomerInfoWithSecondaryAccount(Long customerId) throws NoAccountFoundException, NoCustomerFoundException {
        logger.info("Retrieve customer info for customer Id " + customerId);

        Account account = accountService.findAccountByTypeAndCustomerId("SECONDARY", customerId);
        Customer customer = findCustomerById(customerId);
        List<Transaction> transactions = transactionService.getTransactionByAccountId(account.getAccountId());

        return customerInfoBuilder.build(account, customer, transactions);
    }
}
