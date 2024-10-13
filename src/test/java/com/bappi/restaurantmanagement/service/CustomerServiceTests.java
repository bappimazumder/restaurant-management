package com.bappi.restaurantmanagement.service;

import com.bappi.restaurantmanagement.model.dto.CustomerResponseDto;
import com.bappi.restaurantmanagement.model.entity.Customer;
import com.bappi.restaurantmanagement.repository.CustomerRepository;
import com.bappi.restaurantmanagement.service.Impl.CustomerServiceImpl;
import com.bappi.restaurantmanagement.utils.ResponsePayload;
import com.bappi.restaurantmanagement.utils.mapper.CustomerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerServiceTests {
    @Mock
    private CustomerRepository repository;
    @Mock
    private CustomerMapper objectMapper;
    @InjectMocks
    private CustomerServiceImpl service;
    Customer customer1;
    Customer customer2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        customer1 = new Customer();
        customer1.setId(1L);
        customer1.setCode("C001");
        customer1.setFullName("Bappi Mazumder");

        customer2 = new Customer();
        customer2.setId(1L);
        customer2.setCode("C002");
        customer2.setFullName("David Balame");
    }

    @Test
    public void testGetAllCustomers() {

        List<Customer> mockCustomers = new ArrayList<>();
        mockCustomers.add(customer1);
        mockCustomers.add(customer2);

        List<CustomerResponseDto> mockResponseDtos = new ArrayList<>();
        mockResponseDtos.add(CustomerResponseDto.builder().code("C001").fullName("Bappi Mazumder").build());
        mockResponseDtos.add(CustomerResponseDto.builder().code("C002").fullName("David Balame").build());

        when(repository.findAll()).thenReturn(mockCustomers);
        when(objectMapper.map(mockCustomers)).thenReturn(mockResponseDtos);

        ResponsePayload<CustomerResponseDto> responseDtos = service.getAllCustomers();
        List<CustomerResponseDto> customerList = responseDtos.getDataList();


        assertEquals(2, customerList.size());
        assertEquals("C001", customerList.get(0).getCode());
        assertEquals("C002", customerList.get(1).getCode());
        verify(repository).findAll();
    }

    @Test
    public void testGetCustomer() {
        String code = "C001";

        when(repository.findByCode(code)).thenReturn(customer1);

        Customer customer = service.getCustomer(code);

        assertNotNull(customer);
        assertEquals(code, customer.getCode());
        assertEquals("Bappi Mazumder", customer.getFullName());
        verify(repository).findByCode(code);
    }

    @Test
    public void testGetCustomerNotFound() {
        String code = "C999";
        when(repository.findByCode(code)).thenReturn(null);
        Customer customer = service.getCustomer(code);
        assertNull(customer);
        verify(repository).findByCode(code);
    }

}
