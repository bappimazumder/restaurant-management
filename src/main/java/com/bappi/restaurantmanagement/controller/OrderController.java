package com.bappi.restaurantmanagement.controller;


import com.bappi.restaurantmanagement.model.entity.Customer;
import com.bappi.restaurantmanagement.model.entity.Order;
import com.bappi.restaurantmanagement.service.CustomerService;
import com.bappi.restaurantmanagement.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static com.bappi.restaurantmanagement.config.ApiPath.*;

@RestController
@RequestMapping(API_ORDER)
public class OrderController {

    private final OrderService service;
    private final CustomerService customerService;

    @Autowired
    public OrderController(OrderService orderService, CustomerService customerService) {
        this.service = orderService;
        this.customerService = customerService;
    }

    @GetMapping(value=API_GET_ALL_ORDER_TODAY)
    public List<Order> getTodayOrders() {
        return service.getTodayOrders();
    }

    @GetMapping(value = API_GET_ALL_SALE_TODAY)
    public Double getTotalSaleAmount() {
        return service.getTotalSaleAmount();
    }

    @GetMapping(value=API_GET_ALL_ORDER_BY_CUSTOMER)
    public List<Order> getOrdersByCustomer(@RequestParam(value = "code") String code) {
        Customer customer = customerService.getCustomer(code);
        if (customer == null){

        }
        return service.getOrdersByCustomer(customer.getId());
    }

    @GetMapping(value=API_GET_MAX_SALE_A_DAY)
    public LocalDate getMaxSaleDay(@RequestParam LocalDate start, @RequestParam LocalDate end) {
        return service.getMaxSaleDay(start, end);
    }
}
