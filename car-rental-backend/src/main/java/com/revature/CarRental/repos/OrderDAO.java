package com.revature.CarRental.repos;

import com.revature.CarRental.models.Order;
import com.revature.CarRental.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDAO extends JpaRepository<Order, Integer> {

    /**
     * RETRIEVE an ORDER OF A USER
     */
    Order getByUserAndIsCompleted(User user, boolean isCompleted);
    Order getByUserAndIsApprovedAndIsAvailableAndIsCompleted(User user, boolean isApproved, boolean isAvailable, boolean isCompleted);

    /**
     * DELETE CURRENT ORDER OF A USER
     */
    default void deleteByUserAndIsCompleted(User user, boolean isCompleted) {
        Order order = getByUserAndIsCompleted(user, isCompleted);
        delete(order);
    }
    List<Order> findAllByUser(User user);

    List<Order> findAllByIsApprovedAndIsCompleted(boolean isApproved, boolean isCompleted);

    List<Order> findAllByIsCompleted(boolean isCompleted);
}
