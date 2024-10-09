package com.bappi.restaurantmanagement.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OrderResponseDto {

    private String code;

    private Double amount;

    private String orderDate;

    private String customerCode;

    private String customerName;
}
