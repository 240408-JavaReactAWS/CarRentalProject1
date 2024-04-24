package com.revature.CarRental.services;

import com.revature.CarRental.models.Order;
import com.revature.CarRental.models.User;
import com.revature.CarRental.repos.OrderDAO;
import com.revature.CarRental.repos.UserDAO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.FailedLoginException;
import java.util.Optional;

@Service
public class OrderService {

    private OrderDAO od;
    private UserDAO ud;

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

    /**
     * ORDER CANCELLATION by USER
     * @return DELETED ORDER if deleted throws exception otherwise
     */
    public Order deleteOrder(User credentials) throws FailedLoginException {
        Optional<User> user_w_username = ud.findByUsername(credentials.getUsername()); // ud = UserDAO
        Order deletedOrder;
        if (user_w_username.isPresent()) {
            User user = user_w_username.get();
            if (credentials.getPassword().equals(user.getPassword())) {
                deletedOrder=od.getByUserAndIsCompleted(user, false); // od = OrderDAO
                od.deleteByUserAndIsCompleted(user, false); // od = OrderDAO
                return deletedOrder;
            }
        }
        throw new FailedLoginException("Invalid credentials provided.");
    }



}
