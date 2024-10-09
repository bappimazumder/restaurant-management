package com.bappi.restaurantmanagement.service;

import com.bappi.restaurantmanagement.model.dto.OrderResponseDto;
import com.bappi.restaurantmanagement.model.dto.SaleResponseDto;
import com.bappi.restaurantmanagement.model.entity.Order;
import com.bappi.restaurantmanagement.repository.OrderRepository;
import com.bappi.restaurantmanagement.utils.ResponsePayload;
import com.bappi.restaurantmanagement.utils.mapper.OrderMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repository;

    private final OrderMapper objectMapper;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.repository = orderRepository;
        this.objectMapper = Mappers.getMapper(OrderMapper.class);
    }

    public ResponsePayload<OrderResponseDto> getTodayOrders() {
        List<Order> orders = repository.findByOrderDate((LocalDate.now()));
        return ResponsePayload.<OrderResponseDto>builder()
                .dataList(objectMapper.map(orders)).build();
    }

    public SaleResponseDto getTodayTotalSaleAmount() {
        Double amount = repository.sumAmountBySaleDate(LocalDate.now());
        return new SaleResponseDto(amount,LocalDate.now().toString());
    }

    public ResponsePayload<OrderResponseDto> getOrdersByCustomer(Long customerId) {
        List<Order> orders = repository.findByCustomerId(customerId);
        return ResponsePayload.<OrderResponseDto>builder()
                .dataList(objectMapper.map(orders)).build();
    }

    public SaleResponseDto getMaxSaleDay(LocalDate startDate, LocalDate endDate) {
        SaleResponseDto responseDto = new SaleResponseDto();
        List<Object[]> salesData = repository.findTotalSalesByDateRange(startDate, endDate);

        LocalDate maxSaleDay = null;
        Double maxSaleAmount = 0.0;

        for (Object[] data : salesData) {
            LocalDate date = (LocalDate) data[0];
            Double totalAmount = (Double) data[1];

            if (totalAmount > maxSaleAmount) {
                maxSaleAmount = totalAmount;
                maxSaleDay = date;
            }
        }
        responseDto.setAmount(maxSaleAmount);

        if(maxSaleDay!= null){
            responseDto.setSaleDate(maxSaleDay.toString());
        }

        return responseDto;
    }

}
