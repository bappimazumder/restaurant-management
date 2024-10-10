package com.bappi.restaurantmanagement.controller;

import com.bappi.restaurantmanagement.model.dto.OrderResponseDto;
import com.bappi.restaurantmanagement.model.dto.SaleResponseDto;
import com.bappi.restaurantmanagement.model.entity.Customer;
import com.bappi.restaurantmanagement.service.CustomerService;
import com.bappi.restaurantmanagement.service.OrderService;
import com.bappi.restaurantmanagement.utils.ResponsePayload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import java.time.LocalDate;
import java.util.List;

import static com.bappi.restaurantmanagement.config.ApiPath.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
public class OrderControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private CustomerService customerService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTodayOrders() throws Exception {
        ResponsePayload<OrderResponseDto> expectedResponse = new ResponsePayload<>(1,1,List.of(new OrderResponseDto()));

        when(orderService.getTodayOrders()).thenReturn(expectedResponse);

        mockMvc.perform(get(API_BASE_PATH+API_ORDER+API_GET_ALL_ORDER_TODAY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dataList").isArray()) // Check if data is an array
                .andExpect(jsonPath("$.dataList", hasSize(1)));

        verify(orderService, times(1)).getTodayOrders();
    }

    @Test
    void testGetTodayTotalSaleAmount() throws Exception {
        SaleResponseDto saleResponseDto = new SaleResponseDto();

        when(orderService.getTodayTotalSaleAmount()).thenReturn(saleResponseDto);

        mockMvc.perform(get(API_BASE_PATH+API_ORDER+API_GET_ALL_SALE_TODAY))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));

        verify(orderService).getTodayTotalSaleAmount();
    }

    @Test
    void testGetOrdersByCustomer_ValidCustomer() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("customerCode", "C001");
        String customerCode = "C001";
        Customer customer = new Customer();
        customer.setCode(customerCode);
        ResponsePayload<OrderResponseDto> responsePayload = new ResponsePayload<>(1,1,List.of(new OrderResponseDto()));

        when(customerService.getCustomer(customerCode)).thenReturn(customer);
        when(orderService.getOrdersByCustomer(customer.getId())).thenReturn(responsePayload);

        mockMvc.perform(get(API_BASE_PATH+API_ORDER+API_GET_ALL_ORDER_BY_CUSTOMER)
                        .params(requestParams))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));

        verify(customerService).getCustomer(customerCode);
        verify(orderService).getOrdersByCustomer(customer.getId());
    }

    @Test
    void testGetOrdersByCustomer_InvalidCustomer() throws Exception {
        String customerCode = "INVALID_CODE";
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("customerCode", customerCode);

        when(customerService.getCustomer(customerCode)).thenReturn(null);

        mockMvc.perform(get(API_BASE_PATH+API_ORDER+API_GET_ALL_ORDER_BY_CUSTOMER).params(requestParams))
                .andExpect(status().isNotFound());

        verify(customerService).getCustomer(customerCode);
        verify(orderService, never()).getOrdersByCustomer(anyLong());
    }

    @Test
    void testGetMaxSaleDay() throws Exception {

        LocalDate startDate = LocalDate.of(2024, 10, 1);
        LocalDate endDate = LocalDate.of(2024, 10, 7);
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("start", startDate.toString());
        requestParams.add("end", endDate.toString());

        SaleResponseDto saleResponseDto = new SaleResponseDto();

        when(orderService.getMaxSaleDay(startDate, endDate)).thenReturn(saleResponseDto);

        mockMvc.perform(get(API_BASE_PATH+API_ORDER+API_GET_MAX_SALE_A_DAY).params(requestParams))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));

        verify(orderService).getMaxSaleDay(startDate, endDate);
    }
}
