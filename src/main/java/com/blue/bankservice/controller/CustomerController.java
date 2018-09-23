package com.blue.bankservice.controller;

import com.blue.bankservice.domain.info.CustomerInfo;
import com.blue.bankservice.exceptions.NoAccountFoundException;
import com.blue.bankservice.exceptions.NoCustomerFoundException;
import com.blue.bankservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
public class CustomerController {

    static final String BASE_URL = "/api/v1";

    static final String GET_CUSTOMER_INFO_URL = BASE_URL + "/customer/{customerId}";

    private CustomerService customerService;

    public CustomerController(@Autowired CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(path = GET_CUSTOMER_INFO_URL, produces = APPLICATION_JSON_UTF8_VALUE)
    CustomerInfo getCustomerById(@PathVariable("customerId") Long customerId) throws NoCustomerFoundException, NoAccountFoundException {
        return customerService.getCustomerInfoWithSecondaryAccount(customerId);
    }

}
