package com.bappi.restaurantmanagement.service;

import com.bappi.restaurantmanagement.model.dto.OrderResponseDto;
import com.bappi.restaurantmanagement.model.dto.SaleResponseDto;
import com.bappi.restaurantmanagement.utils.ResponsePayload;

import java.time.LocalDate;

public interface OrderService {
    public ResponsePayload<OrderResponseDto> getTodayOrders();
    public SaleResponseDto getTodayTotalSaleAmount();
    public ResponsePayload<OrderResponseDto> getOrdersByCustomer(Long customerId);
    public SaleResponseDto getMaxSaleDay(LocalDate startDate, LocalDate endDate);
}
