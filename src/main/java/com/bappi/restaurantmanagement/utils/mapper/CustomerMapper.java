package com.bappi.restaurantmanagement.utils.mapper;

import com.bappi.restaurantmanagement.model.dto.CustomerResponseDto;
import com.bappi.restaurantmanagement.model.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CustomerMapper {
    CustomerResponseDto map(Customer obj);
    List<CustomerResponseDto> map(List<Customer> list);
}
