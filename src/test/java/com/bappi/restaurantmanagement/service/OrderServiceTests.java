package com.bappi.restaurantmanagement.service;

import com.bappi.restaurantmanagement.model.dto.OrderResponseDto;
import com.bappi.restaurantmanagement.model.dto.SaleResponseDto;
import com.bappi.restaurantmanagement.model.entity.Customer;
import com.bappi.restaurantmanagement.model.entity.Order;
import com.bappi.restaurantmanagement.repository.OrderRepository;
import com.bappi.restaurantmanagement.utils.ResponsePayload;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OrderServiceTests {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    public OrderServiceTests() {
        MockitoAnnotations.openMocks(this);
    }

    // Test Case 1 for getTodayOrders() method
    @Test
    public void testGetTodayOrders() {
        LocalDate today = LocalDate.now();
        Order order = new Order();
        order.setId(1L);
        order.setAmount(100.0);
        order.setOrderDate(today);

        when(orderRepository.findByOrderDate(today)).thenReturn(Collections.singletonList(order));

        ResponsePayload<OrderResponseDto> orders = orderService.getTodayOrders();
        List<OrderResponseDto> orderList = orders.getDataList();
        assertEquals(1, orderList.size());
        assertEquals(100.0, orderList.get(0).getAmount());
        assertEquals(today.toString(), orderList.get(0).getOrderDate());
    }

    // Test Case 2 for getTodayOrders() method
    @Test
    public void testGetTodayOrders_NoOrders() {
        LocalDate today = LocalDate.now();
        when(orderRepository.findByOrderDate(today)).thenReturn(Collections.emptyList());
        ResponsePayload<OrderResponseDto> orders = orderService.getTodayOrders();
        assertEquals(0, orders.getDataList().size());
    }

    // Test Case 1 for getTotalSaleAmount() method
    @Test
    public void testGetTotalSaleAmount_WithSales() {
        LocalDate today = LocalDate.now();
        SaleResponseDto expectedResponse = new SaleResponseDto(500.0,today.toString());
        expectedResponse.setAmount(500.0);
        Double expectedAmount = 500.0;
        when(orderRepository.sumAmountBySaleDate(today)).thenReturn(expectedAmount);

        SaleResponseDto actualResponse = orderService.getTodayTotalSaleAmount();

        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getAmount(), actualResponse.getAmount(), "Expected amount should match");
        assertEquals(expectedResponse.getSaleDate(), actualResponse.getSaleDate(), "Expected date should match");
        verify(orderRepository).sumAmountBySaleDate(today);
    }

    // Test Case 2 for getTotalSaleAmount() method
    @Test
    public void testGetTotalSaleAmount_NoSales() {
        LocalDate today = LocalDate.now();

        SaleResponseDto expectedResponse = new SaleResponseDto();

        when(orderRepository.sumAmountBySaleDate(today)).thenReturn(null);

        SaleResponseDto actualResponse = orderService.getTodayTotalSaleAmount();

        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getAmount(), actualResponse.getAmount(), "Expected amount should be null");
        verify(orderRepository).sumAmountBySaleDate(today);
    }

    // Test Case 1 for getOrdersByCustomer() method

    @Test
    public void testGetOrdersByCustomer_WithOrders() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setCode("C001");
        customer.setFullName("Bappi Mazumder");

        Long customerId = 1L;
        Order order1 = new Order();
        order1.setId(1L);
        order1.setAmount(100.0);
        order1.setCustomer(customer);

        Order order2 = new Order();
        order2.setId(2L);
        order2.setAmount(150.0);
        order2.setCustomer(customer);

        List<Order> expectedOrders = Arrays.asList(order1, order2);

        when(orderRepository.findByCustomerId(customerId)).thenReturn(expectedOrders);

        ResponsePayload<OrderResponseDto> orders = orderService.getOrdersByCustomer(customerId);
        List<OrderResponseDto> orderList = orders.getDataList();
        assertEquals(2, orderList.size());
        assertEquals(100.0, orderList.get(0).getAmount());
        assertEquals(150.0, orderList.get(1).getAmount());
    }

    // Test Case 2 for getOrdersByCustomer() method
    @Test
    public void testGetOrdersByCustomer_NoOrders() {
        Long customerId = 2L;
        when(orderRepository.findByCustomerId(customerId)).thenReturn(Collections.emptyList());
        ResponsePayload<OrderResponseDto> orders = orderService.getOrdersByCustomer(customerId);
        assertEquals(0, orders.getDataList().size());
    }

    // Test Case 1 for getMaxSaleDay() method
    @Test
    public void testGetMaxSaleDay_WithSales() {
        LocalDate startDate = LocalDate.of(2024, 10, 1);
        LocalDate endDate = LocalDate.of(2024, 10, 10);

        List<Object[]> salesData = Arrays.asList(
                new Object[]{LocalDate.of(2024, 10, 5), 300.0},
                new Object[]{LocalDate.of(2024, 10, 6), 500.0},
                new Object[]{LocalDate.of(2024, 10, 7), 200.0}
        );

        when(orderRepository.findTotalSalesByDateRange(startDate, endDate)).thenReturn(salesData);

        SaleResponseDto actualResponse = orderService.getMaxSaleDay(startDate, endDate);

        assertNotNull(actualResponse);
        assertEquals(500.0, actualResponse.getAmount(), "Expected max sale amount should be 200.0");
        assertEquals(LocalDate.of(2024, 10, 6).toString(), actualResponse.getSaleDate(), "Expected sale date should match");
        verify(orderRepository).findTotalSalesByDateRange(startDate, endDate);
    }

    // Test Case 2 for getMaxSaleDay() method
    @Test
    public void testGetMaxSaleDay_NoSales() {
        LocalDate startDate = LocalDate.of(2024, 10, 1);
        LocalDate endDate = LocalDate.of(2024, 10, 9);
        List<Object[]> salesData = Arrays.asList();
        when(orderRepository.findTotalSalesByDateRange(startDate, endDate)).thenReturn(salesData);

        SaleResponseDto actualResponse = orderService.getMaxSaleDay(startDate, endDate);

        assertNotNull(actualResponse);
        assertEquals(0.0, actualResponse.getAmount(), "Expected max sale amount should be 0.0");
        assertNull(actualResponse.getSaleDate(), "Expected sale date should be null when there are no sales");
        verify(orderRepository).findTotalSalesByDateRange(startDate, endDate);
    }

}