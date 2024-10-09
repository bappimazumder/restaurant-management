package com.bappi.restaurantmanagement.utils.mapper;

import com.bappi.restaurantmanagement.model.dto.CustomerResponseDto;
import com.bappi.restaurantmanagement.model.dto.OrderResponseDto;
import com.bappi.restaurantmanagement.model.entity.Customer;
import com.bappi.restaurantmanagement.model.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface OrderMapper {
    @Mapping(source = "customer.code", target = "customerCode")
    @Mapping(source = "customer.fullName", target = "customerName")
    OrderResponseDto map(Order obj);
    List<OrderResponseDto> map(List<Order> list);

}
