package com.bappi.restaurantmanagement.repository;

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
    @Query("SELECT sum(o.amount) FROM Order o WHERE o.orderDate = :orderDate")
    Double sumAmountByOrderDate(@Param("orderDate") LocalDate orderDate);

    @Query("SELECT o.orderDate, SUM(o.amount) FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate GROUP BY o.orderDate")
    List<Object[]> findTotalSalesByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
