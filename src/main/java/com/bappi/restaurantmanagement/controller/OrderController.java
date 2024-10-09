package com.bappi.restaurantmanagement.controller;


import com.bappi.restaurantmanagement.model.dto.OrderResponseDto;
import com.bappi.restaurantmanagement.model.entity.Customer;
import com.bappi.restaurantmanagement.model.entity.Order;
import com.bappi.restaurantmanagement.service.CustomerService;
import com.bappi.restaurantmanagement.service.OrderService;
import com.bappi.restaurantmanagement.utils.APIErrorCode;
import com.bappi.restaurantmanagement.utils.CustomException;
import com.bappi.restaurantmanagement.utils.ResponsePayload;
import com.bappi.restaurantmanagement.utils.ServiceExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static com.bappi.restaurantmanagement.config.ApiPath.*;
@Slf4j
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
    public HttpEntity<ResponsePayload<OrderResponseDto>> getTodayOrders() {
        ServiceExceptionHandler<ResponsePayload<OrderResponseDto>> dataHandler = service :: getTodayOrders;
        ResponsePayload<OrderResponseDto> dto = dataHandler.executeHandler();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(value = API_GET_ALL_SALE_TODAY)
    public HttpEntity<?> getTotalSaleAmount() {
        ServiceExceptionHandler<Double> dataHandler = service :: getTotalSaleAmount;
        Double data = dataHandler.executeHandler();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping(value=API_GET_ALL_ORDER_BY_CUSTOMER)
    public HttpEntity<ResponsePayload<OrderResponseDto>> getOrdersByCustomer(@RequestParam(value = "customerCode") String customerCode) {

        ResponsePayload<OrderResponseDto> dto = null;
        Customer customer = customerService.getCustomer(customerCode);
        if(Objects.isNull(customer)){
            log.error("Invalid Customer code {}",customerCode);
            return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
        }

        ServiceExceptionHandler<ResponsePayload<OrderResponseDto>> dataHandler = () -> service.getOrdersByCustomer(customer.getId());
        dto = dataHandler.executeHandler();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(value=API_GET_MAX_SALE_A_DAY)
    public HttpEntity<?> getMaxSaleDay(@RequestParam LocalDate start, @RequestParam LocalDate end) {
        ServiceExceptionHandler<LocalDate> dataHandler = () -> service.getMaxSaleDay(start, end);
        LocalDate data = dataHandler.executeHandler();
        return new ResponseEntity<>(data.toString(), HttpStatus.OK);
    }
}
