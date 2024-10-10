package com.bappi.restaurantmanagement.controller;

import com.bappi.restaurantmanagement.model.dto.CustomerResponseDto;
import com.bappi.restaurantmanagement.model.dto.OrderResponseDto;
import com.bappi.restaurantmanagement.service.CustomerService;
import com.bappi.restaurantmanagement.utils.ResponsePayload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.bappi.restaurantmanagement.config.ApiPath.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCustomers() throws Exception {

        CustomerResponseDto customerResponse = new CustomerResponseDto("C001","Bappi Mazumder");
        ResponsePayload<CustomerResponseDto> expectedResponse = new ResponsePayload<>(1,1, List.of(customerResponse));


        when(customerService.getAllCustomers()).thenReturn(expectedResponse);


        mockMvc.perform(get(API_BASE_PATH+API_CUSTOMER+API_GET_ALL_CUSTOMER))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dataList").isArray())
                .andExpect(jsonPath("$.dataList", hasSize(1)));

        verify(customerService, times(1)).getAllCustomers();
    }

}
