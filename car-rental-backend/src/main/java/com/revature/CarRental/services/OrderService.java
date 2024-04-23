package com.revature.CarRental.services;

import com.revature.CarRental.models.Order;
import com.revature.CarRental.repos.OrderDAO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class OrderService {

    private OrderDAO od;

    @Autowired
    public OrderService(OrderDAO od) {
        this.od = od;
    }

    public Order updateOrderApprovalStatus(int id, Boolean approvalStatus) {
        Optional<Order> optionalOrder = od.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setApproved(approvalStatus);
            order.setCompleted(true);
            od.save(order);
            return order;
        }
        throw new EntityNotFoundException("No Order found with id: " + id);
    }


}
