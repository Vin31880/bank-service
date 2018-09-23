package com.blue.bankservice.service.builders

import com.blue.bankservice.domain.Account
import com.blue.bankservice.domain.Customer
import com.blue.bankservice.domain.info.CustomerInfo
import com.blue.bankservice.remote.transaction.Transaction
import com.blue.bankservice.service.builder.CustomerInfoBuilder
import spock.lang.Specification

class CustomerInfoBuilderSpec extends Specification {
    def customerInfoBuilder = new CustomerInfoBuilder()

    def "build customer info"() {
        given:
        Customer customer = new Customer(customerId: 1L, firstName: "TEST")
        def secondaryAccount = new Account(accountId: 2L, accountType: "SECONDARY", customer: customer)
        List<Transaction> transactionList = new ArrayList<>()
        transactionList.add(new Transaction(transactionId: 1L, accountId: 1L))
        transactionList.add(new Transaction(transactionId: 2L, accountId: 1L))

        when:
        CustomerInfo customerInfo = customerInfoBuilder.build(secondaryAccount, customer, transactionList)

        then:
        customerInfo.customerId == customer.getCustomerId()
        customerInfo.accountId == secondaryAccount.accountId
        customerInfo.balance == secondaryAccount.balance
        customerInfo.transactions == transactionList

    }
}
