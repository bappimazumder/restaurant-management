package com.bappi.restaurantmanagement.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

import static com.bappi.restaurantmanagement.config.OrderDBConstant.*;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = ORDER_TABLE)
public class Order{
    @Id
    @Column(name = ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = CODE,unique=true)
    private String code;

    @Column(name = AMOUNT)
    private Double amount;

    @Column(name = ORDER_DATE)
    private LocalDate orderDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = CUSTOMER_ID)
    private Customer customer;
}
