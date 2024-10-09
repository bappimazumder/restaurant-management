package com.bappi.restaurantmanagement.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class CustomerResponseDto {
    private String code;
    private String fullName;
}
