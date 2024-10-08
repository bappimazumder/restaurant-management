package com.bappi.restaurantmanagement.service;

import com.bappi.restaurantmanagement.model.entity.Order;
import com.bappi.restaurantmanagement.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.repository = orderRepository;
    }

    public List<Order> getTodayOrders() {
        return repository.findByOrderDate((LocalDate.now()));
    }

    public Double getTotalSaleAmount() {
        return repository.sumAmountByOrderDate(LocalDate.now());
    }

    public List<Order> getOrdersByCustomer(Long customerId) {
        return repository.findByCustomerId(customerId);
    }

    public LocalDate getMaxSaleDay(LocalDate startDate, LocalDate endDate) {

        List<Object[]> salesData = repository.findTotalSalesByDateRange(startDate, endDate);

        LocalDate maxSaleDay = null;
        double maxSaleAmount = 0;

        for (Object[] data : salesData) {
            LocalDate date = (LocalDate) data[0];
            Double totalAmount = (Double) data[1];

            if (totalAmount > maxSaleAmount) {
                maxSaleAmount = totalAmount;
                maxSaleDay = date;
            }
        }

        return maxSaleDay;
    }

}
