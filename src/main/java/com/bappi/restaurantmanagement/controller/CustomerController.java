package com.bappi.restaurantmanagement.controller;

import com.bappi.restaurantmanagement.model.dto.CustomerResponseDto;
import com.bappi.restaurantmanagement.service.Impl.CustomerServiceImpl;
import com.bappi.restaurantmanagement.utils.ResponsePayload;
import com.bappi.restaurantmanagement.utils.ServiceExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.bappi.restaurantmanagement.config.ApiPath.API_CUSTOMER;
import static com.bappi.restaurantmanagement.config.ApiPath.API_GET_ALL_CUSTOMER;
@Slf4j
@RestController
@RequestMapping(API_CUSTOMER)
public class CustomerController {
    private final CustomerServiceImpl service;

    @Autowired
    public CustomerController(CustomerServiceImpl customerServiceService) {
        this.service = customerServiceService;
    }

    @GetMapping(value = API_GET_ALL_CUSTOMER)
    public HttpEntity<ResponsePayload<CustomerResponseDto>> getAllCustomers() {
        log.info("Call Start getAllCustomers() method");
        ServiceExceptionHandler<ResponsePayload<CustomerResponseDto>> dataHandler = service::getAllCustomers;
        ResponsePayload<CustomerResponseDto> dto = dataHandler.executeHandler();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
