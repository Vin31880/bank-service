package com.blue.bankservice.service;

import com.blue.bankservice.remote.transaction.Transaction;
import com.blue.bankservice.remote.transaction.TransactionClient;
import com.blue.bankservice.remote.transaction.TransactionResourceIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private TransactionClient transactionClient;

    public TransactionService(@Autowired TransactionClient transactionClient) {
        this.transactionClient = transactionClient;
    }

    public Transaction addInitialTransactionToAccount(Long accountId, double initialCredit) {
        logger.info("Add transaction for account Id " + accountId + " with intial credit of " + initialCredit);
        return transactionClient.saveTransaction(buildTransaction(accountId, initialCredit)).getBody();
    }

    public List<Transaction> getTransactionByAccountId(Long accountId) {
        logger.info("Retrieve transactions for account Id " + accountId);
        return transactionClient.getTransactionByAccountId(accountId).getBody();
    }

    private TransactionResourceIn buildTransaction(Long accountId, double initialCredit) {
        TransactionResourceIn transactionResourceIn = new TransactionResourceIn();
        transactionResourceIn.setAccountId(accountId);
        transactionResourceIn.setTransactionAmount(initialCredit);
        transactionResourceIn.setTransactionType("CREDIT");

        return transactionResourceIn;
    }
}
