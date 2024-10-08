package com.bappi.restaurantmanagement.service;

import com.bappi.restaurantmanagement.model.entity.Customer;
import com.bappi.restaurantmanagement.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository customerRepository) {
        this.repository = customerRepository;
    }
    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    public Customer getCustomer(String code) {
        return repository.findByCode(code);
    }

}
