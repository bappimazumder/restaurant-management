package com.bappi.restaurantmanagement.controller;

import com.bappi.restaurantmanagement.model.dto.CustomerResponseDto;
import com.bappi.restaurantmanagement.service.CustomerService;
import com.bappi.restaurantmanagement.utils.ServiceExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomers() {
        ServiceExceptionHandler<List<CustomerResponseDto>> dataHandler = service::getAllCustomers;
        List<CustomerResponseDto> dto = dataHandler.executeHandler();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
