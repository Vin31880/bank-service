package com.blue.bankservice.controller

import com.blue.bankservice.domain.info.CustomerInfo
import com.blue.bankservice.service.CustomerService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.http.HttpStatus.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class CustomerControllerSpec extends Specification {
    ObjectMapper json
    MockMvc mvc
    CustomerService customerService
    CustomerController customerController

    void setup() {
        json = new ObjectMapper()
        customerService = Mock(CustomerService)
        customerController = new CustomerController(customerService)
        mvc = standaloneSetup(customerController).build()
    }

    def "GET_CUSTOMER_INFO: Success"() {
        given:
        def customerId = 1l

        def customerInfo = new CustomerInfo(customerId: 1L)

        def request = get(CustomerController.GET_CUSTOMER_INFO_URL, customerId)

        when:
        def response = mvc.perform(request).andReturn().response

        then:
        1 * customerService.getCustomerInfoWithSecondaryAccount(customerId) >> customerInfo
        response.status == OK.value()
    }
}

