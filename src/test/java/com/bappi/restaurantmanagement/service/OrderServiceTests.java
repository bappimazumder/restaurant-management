package com.bappi.restaurantmanagement.service;

import com.bappi.restaurantmanagement.model.dto.OrderResponseDto;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        Double expectedTotal = 500.0;
        when(orderRepository.sumAmountByOrderDate(today)).thenReturn(expectedTotal);

        Double totalSales = orderService.getTotalSaleAmount();

        assertEquals(expectedTotal, totalSales);
    }

    // Test Case 2 for getTotalSaleAmount() method
    @Test
    public void testGetTotalSaleAmount_NoSales() {
        LocalDate today = LocalDate.now();
        Double expectedTotal = 0.0;

        when(orderRepository.sumAmountByOrderDate(today)).thenReturn(expectedTotal);

        Double totalSales = orderService.getTotalSaleAmount();

        assertEquals(expectedTotal, totalSales);
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

        LocalDate maxSaleDay = orderService.getMaxSaleDay(startDate, endDate);
        assertEquals(LocalDate.of(2024, 10, 6), maxSaleDay);
    }

    // Test Case 2 for getMaxSaleDay() method
    @Test
    public void testGetMaxSaleDay_NoSales() {
        LocalDate startDate = LocalDate.of(2024, 10, 1);
        LocalDate endDate = LocalDate.of(2024, 10, 10);

        when(orderRepository.findTotalSalesByDateRange(startDate, endDate)).thenReturn(Collections.emptyList());

        LocalDate maxSaleDay = orderService.getMaxSaleDay(startDate, endDate);
        assertEquals(null, maxSaleDay);
    }

}