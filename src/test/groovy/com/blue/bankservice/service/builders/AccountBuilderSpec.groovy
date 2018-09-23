package com.blue.bankservice.service.builders

import com.blue.bankservice.domain.Account
import com.blue.bankservice.domain.Customer
import com.blue.bankservice.service.builder.AccountBuilder
import spock.lang.Specification

class AccountBuilderSpec extends Specification {

    def accountBuilder = new AccountBuilder()

    def "build secondary account"() {
        given:
        Customer customer = new Customer(customerId: 1L, firstName: "TEST")
        double initialCredit = 100.0

        when:
        Account result = accountBuilder.buildSecondaryAccount(customer, initialCredit)

        then:
        result.customer == customer
        result.accountType == "SECONDARY"
        result.balance == initialCredit
    }
}
