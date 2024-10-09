package com.bappi.restaurantmanagement.repository;

import com.bappi.restaurantmanagement.model.dto.SaleResponseDto;
import com.bappi.restaurantmanagement.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByOrderDate(LocalDate orderDate);
    List<Order> findByCustomerId(Long customerId);
    @Query("SELECT sum(o.amount) FROM Order o WHERE o.saleDate = :saleDate GROUP BY o.saleDate")
    Double sumAmountBySaleDate(@Param("saleDate") LocalDate saleDate);

    @Query("SELECT o.saleDate, SUM(o.amount) FROM Order o WHERE o.saleDate BETWEEN :startDate AND :endDate GROUP BY o.saleDate")
    List<Object[]> findTotalSalesByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
