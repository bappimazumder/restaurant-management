package com.bappi.restaurantmanagement.service;

import com.bappi.restaurantmanagement.model.dto.CustomerResponseDto;
import com.bappi.restaurantmanagement.model.entity.Customer;
import com.bappi.restaurantmanagement.utils.ResponsePayload;

public interface CustomerService {
    public ResponsePayload<CustomerResponseDto> getAllCustomers();

    public Customer getCustomer(String code);
}
