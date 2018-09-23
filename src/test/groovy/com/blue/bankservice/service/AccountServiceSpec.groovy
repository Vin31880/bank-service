package com.blue.bankservice.service

import com.blue.bankservice.service.builder.AccountBuilder
import com.blue.bankservice.domain.Account
import com.blue.bankservice.domain.Customer
import com.blue.bankservice.exceptions.NoAccountFoundException
import com.blue.bankservice.exceptions.SecondaryAccountAlreadyExistException
import com.blue.bankservice.repository.AccountRepository
import spock.lang.Specification

class AccountServiceSpec extends Specification {

    TransactionService transactionService
    AccountService accountService
    AccountRepository accountRepository
    AccountBuilder accountBuilder

    void setup() {
        accountBuilder = Mock(AccountBuilder)
        transactionService = Mock(TransactionService)
        accountRepository = Mock(AccountRepository)
        accountService = new AccountService(accountBuilder, accountRepository, transactionService)
    }

    def "Create Secondary Account :it should throw exception if secondary account already exists"() {
        given:
        def customerId = 1
        def account = new Account(accountId: 1L, accountType: "SECONDARY")
        accountRepository.findByAccountTypeAndCustomer_CustomerId("SECONDARY", customerId) >> Optional.of(account)

        when:
        accountService.createSecondaryAccount(customerId, 100)

        then:
        thrown(SecondaryAccountAlreadyExistException)
    }

    def "Create Secondary Account :it should throw exception if no primary account exists for customer"() {
        given:
        def customerId = 1

        accountRepository.findByAccountTypeAndCustomer_CustomerId("SECONDARY", customerId) >> Optional.empty()
        accountRepository.findByAccountTypeAndCustomer_CustomerId("PRIMARY", customerId) >> Optional.empty()

        when:
        accountService.createSecondaryAccount(customerId, 100)

        then:
        thrown(NoAccountFoundException)
    }

    def "Create Secondary Account :it should build secondary account for customer without transaction"() {
        given:
        def customerId = 1
        def primaryAccountHolder = new Customer(customerId: 1L, firstName: "TEST_USER")
        def primaryAccount = new Account(accountId: 1L, accountType: "PRIMARY", customer: primaryAccountHolder)
        def secondaryAccount = new Account(accountId: 2L, accountType: "SECONDARY", customer: primaryAccountHolder)
        accountRepository.findByAccountTypeAndCustomer_CustomerId("SECONDARY", customerId) >> Optional.empty()
        accountRepository.findByAccountTypeAndCustomer_CustomerId("PRIMARY", customerId) >> Optional.of(primaryAccount)

        when:
        def result = accountService.createSecondaryAccount(customerId, 0)

        then:
        1 * accountBuilder.buildSecondaryAccount(primaryAccountHolder, 0) >> secondaryAccount
        1 * accountRepository.save(secondaryAccount) >> secondaryAccount
        0 * transactionService.addInitialTransactionToAccount(secondaryAccount.accountId, 0)
        result == secondaryAccount
    }

    def "Create Secondary Account :it should build secondary account for customer with transaction"() {
        given:
        def customerId = 1
        def primaryAccountHolder = new Customer(customerId: 1L, firstName: "TEST_USER")
        def primaryAccount = new Account(accountId: 1L, accountType: "PRIMARY", customer: primaryAccountHolder)
        def secondaryAccount = new Account(accountId: 2L, accountType: "SECONDARY", customer: primaryAccountHolder)
        accountRepository.findByAccountTypeAndCustomer_CustomerId("SECONDARY", customerId) >> Optional.empty()
        accountRepository.findByAccountTypeAndCustomer_CustomerId("PRIMARY", customerId) >> Optional.of(primaryAccount)

        when:
        def result = accountService.createSecondaryAccount(customerId, 100)

        then:
        1 * accountBuilder.buildSecondaryAccount(primaryAccountHolder, 100) >> secondaryAccount
        1 * accountRepository.save(secondaryAccount) >> secondaryAccount
        1 * transactionService.addInitialTransactionToAccount(secondaryAccount.accountId, 100)
        result == secondaryAccount
    }
}
