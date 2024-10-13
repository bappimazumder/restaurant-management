package com.bappi.restaurantmanagement.controller;


import com.bappi.restaurantmanagement.model.dto.OrderResponseDto;
import com.bappi.restaurantmanagement.model.dto.SaleResponseDto;
import com.bappi.restaurantmanagement.model.entity.Customer;
import com.bappi.restaurantmanagement.service.CustomerService;
import com.bappi.restaurantmanagement.service.Impl.OrderServiceImpl;
import com.bappi.restaurantmanagement.utils.ResponsePayload;
import com.bappi.restaurantmanagement.utils.ServiceExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Objects;

import static com.bappi.restaurantmanagement.config.ApiPath.*;
@Slf4j
@RestController
@RequestMapping(API_ORDER)
public class OrderController {

    private final OrderServiceImpl service;
    private final CustomerService customerService;

    @Autowired
    public OrderController(OrderServiceImpl orderService, CustomerService customerService) {
        this.service = orderService;
        this.customerService = customerService;
    }

    @GetMapping(value=API_GET_ALL_ORDER_TODAY)
    public HttpEntity<ResponsePayload<OrderResponseDto>> getTodayOrders() {
        log.info("Call Start getTodayOrders() method");
        ServiceExceptionHandler<ResponsePayload<OrderResponseDto>> dataHandler = service :: getTodayOrders;
        ResponsePayload<OrderResponseDto> dto = dataHandler.executeHandler();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(value = API_GET_ALL_SALE_TODAY)
    public HttpEntity<?> getTodayTotalSaleAmount(){
        log.info("Call Start getTodayTotalSaleAmount() method");
        ServiceExceptionHandler<SaleResponseDto> dataHandler = service :: getTodayTotalSaleAmount;
        SaleResponseDto dto = dataHandler.executeHandler();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(value=API_GET_ALL_ORDER_BY_CUSTOMER)
    public HttpEntity<ResponsePayload<OrderResponseDto>> getOrdersByCustomer(@RequestParam(value = "customerCode",required = true) String customerCode) {
        log.info("Call Start getOrdersByCustomer() method for customer {}",customerCode);
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
    public HttpEntity<?> getMaxSaleDay(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                                        @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        log.info("Call Start getMaxSaleDay() method for start {} and end {}",start,end);
        ServiceExceptionHandler<SaleResponseDto> dataHandler = () -> service.getMaxSaleDay(start, end);
        SaleResponseDto dto = dataHandler.executeHandler();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
