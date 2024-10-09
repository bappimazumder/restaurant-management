package com.bappi.restaurantmanagement.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OrderResponseDto {

    private String code;

    private Double amount;

    private LocalDate orderDate;

    private CustomerResponseDto customer;
}
