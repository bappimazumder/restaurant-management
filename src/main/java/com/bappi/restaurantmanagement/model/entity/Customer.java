package com.bappi.restaurantmanagement.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static com.bappi.restaurantmanagement.config.CustomerDBConstant.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = CUSTOMER_TABLE)
public class Customer {

    @Id
    @Column(name = ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = CODE,unique=true)
    private String code;

    @Column(name = FULL_NAME)
    private String fullName;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;
}
