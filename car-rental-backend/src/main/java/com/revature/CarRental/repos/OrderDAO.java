package com.revature.CarRental.repos;

import com.revature.CarRental.models.Order;
import com.revature.CarRental.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDAO extends JpaRepository<Order, Integer> {

    /**
     * RETRIEVE ORDER OF A USER
     */
    Order getByUserAndIsCompleted(User user, boolean isCompleted);

    /**
     * DELETE CURRENT ORDER OF A USER
     */
    default void deleteByUserAndIsCompleted(User user, boolean isCompleted) {
        Order order = getByUserAndIsCompleted(user, isCompleted);
        delete(order);
    }
}
