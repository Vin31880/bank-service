package com.blue.bankservice.service

import com.blue.bankservice.service.builder.CustomerInfoBuilder
import com.blue.bankservice.domain.Account
import com.blue.bankservice.domain.Customer
import com.blue.bankservice.exceptions.NoCustomerFoundException
import com.blue.bankservice.remote.transaction.Transaction
import com.blue.bankservice.repository.CustomerRepository
import spock.lang.Specification

class CustomerServiceSpec extends Specification {
    CustomerRepository customerRepository
    AccountService accountService
    TransactionService transactionService
    CustomerInfoBuilder customerInfoBuilder
    CustomerService customerService

    void setup() {
        customerRepository = Mock(CustomerRepository)
        accountService = Mock(AccountService)
        transactionService = Mock(TransactionService)
        customerInfoBuilder = Mock(CustomerInfoBuilder)
        customerService = new CustomerService(customerRepository,
                accountService, transactionService, customerInfoBuilder)
    }

    def "Find customer By Id: it returns customer if found"() {
        given:
        def customerId = 1L
        def customer = new Customer(customerId: 1L)
        customerRepository.findById(customerId) >> Optional.of(customer)

        when:
        def result = customerService.findCustomerById(customerId)

        then:
        result == customer
    }

    def "Find customer By Id: it throws exception if customer not found"() {
        given:
        def customerId = 1L
        def customer = new Customer(customerId: 1L)
        customerRepository.findById(customerId) >> Optional.empty()

        when:
        def result = customerService.findCustomerById(customerId)

        then:
        thrown(NoCustomerFoundException)
    }

    def "Get customerInfo By Id: it returns customerinfo if found"() {
        given:
        def customerId = 1L
        def customer = new Customer(customerId: 1L)
        def primaryAccountHolder = new Customer(customerId: 1L, firstName: "TEST_USER")
        def secondaryAccount = new Account(accountId: 2L, accountType: "SECONDARY",
                customer: primaryAccountHolder, balance: 100)

        List<Transaction> transactions = new ArrayList<>()
        transactions.add(new Transaction(transactionId: 1L))


        customerRepository.findById(customerId) >> Optional.of(customer)
        accountService.findAccountByTypeAndCustomerId("SECONDARY", customerId) >> secondaryAccount


        when:
        def result = customerService.getCustomerInfoWithSecondaryAccount(customerId)

        then:
        1 * transactionService.getTransactionByAccountId(secondaryAccount.accountId) >> transactions
        1 * customerInfoBuilder.build(secondaryAccount, customer, transactions)
    }

}
