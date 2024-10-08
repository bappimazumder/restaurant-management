package com.bappi.restaurantmanagement.controller;

import com.bappi.restaurantmanagement.model.entity.Customer;
import com.bappi.restaurantmanagement.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.bappi.restaurantmanagement.config.ApiPath.API_CUSTOMER;
import static com.bappi.restaurantmanagement.config.ApiPath.API_GET_ALL_CUSTOMER;

@RestController
@RequestMapping(API_CUSTOMER)
public class CustomerController {
    private final CustomerService service;


    @Autowired
    public CustomerController(CustomerService customerServiceService) {
        this.service = customerServiceService;
    }

    @GetMapping(value = API_GET_ALL_CUSTOMER)
    public List<Customer> getAllCustomers() {
        return service.getAllCustomers();
    }

}
