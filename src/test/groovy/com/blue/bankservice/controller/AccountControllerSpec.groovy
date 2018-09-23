package com.blue.bankservice.controller

import com.blue.bankservice.domain.Account
import com.blue.bankservice.domain.Customer
import com.blue.bankservice.service.AccountService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class AccountControllerSpec extends Specification {
    ObjectMapper json
    MockMvc mvc
    AccountService accountService
    AccountController accountController

    void setup() {
        accountService = Mock(AccountService)
        json = new ObjectMapper()
        accountController = new AccountController(accountService)
        mvc = standaloneSetup(accountController).build()
    }

    def "CREATE_SECONDARY_ACCOUNT: Sucess"() {
        given:
        def customerId = 1L
        def initialCredit = 100
        def primaryAccountHolder = new Customer(customerId: 1L, firstName: "TEST_USER")
        def secondaryAccount = new Account(accountId: 2L, accountType: "SECONDARY", customer: primaryAccountHolder)
        def request = post(AccountController.OPEN_SECONDARY_ACCOUNT_URL, customerId, initialCredit)
        request.contentType(MediaType.APPLICATION_JSON_UTF8)

        when:
        def response = mvc.perform(request).andReturn().response

        then:
        1 * accountService.createSecondaryAccount(customerId, initialCredit) >> secondaryAccount
        response.status == 201
    }
}
