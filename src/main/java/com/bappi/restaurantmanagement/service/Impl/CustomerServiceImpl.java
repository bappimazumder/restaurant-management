package com.bappi.restaurantmanagement.service.Impl;

import com.bappi.restaurantmanagement.model.dto.CustomerResponseDto;
import com.bappi.restaurantmanagement.model.entity.Customer;
import com.bappi.restaurantmanagement.repository.CustomerRepository;
import com.bappi.restaurantmanagement.service.CustomerService;
import com.bappi.restaurantmanagement.utils.ResponsePayload;
import com.bappi.restaurantmanagement.utils.mapper.CustomerMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repository;
    private final CustomerMapper objectMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.repository = customerRepository;
        this.objectMapper = Mappers.getMapper(CustomerMapper.class);;
    }
    public ResponsePayload<CustomerResponseDto> getAllCustomers() {
        List<Customer> customers = repository.findAll();
        return ResponsePayload.<CustomerResponseDto>builder()
                .dataList(objectMapper.map(customers)).build();
    }

    public Customer getCustomer(String code) {
        return repository.findByCode(code);
    }

}
