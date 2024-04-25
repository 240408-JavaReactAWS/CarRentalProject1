package com.revature.CarRental.services;

import com.revature.CarRental.models.Order;
import com.revature.CarRental.models.User;
import com.revature.CarRental.repos.OrderDAO;
import com.revature.CarRental.repos.UserDAO;
import com.revature.CarRental.models.Vehicle;
import com.revature.CarRental.repos.VehicleDAO;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.security.auth.login.FailedLoginException;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderDAO od;
    private final VehicleDAO vd;
    private final UserDAO ud;

    @Autowired
    public OrderService(OrderDAO od, VehicleDAO vd, UserDAO ud) {
        this.od = od;
        this.vd = vd;
        this.ud = ud;
    }

    public Order updateOrderApprovalStatus(int id, Boolean approvalStatus) {
        Optional<Order> optionalOrder = od.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setApproved(approvalStatus);
            if (!approvalStatus) {
                order.setCompleted(true);
            }
            od.save(order);
            return order;
        }
        throw new EntityNotFoundException("No Order found with id: " + id);
    }

    public Order updateOrderCompleteStatus(int id, Boolean completeStatus) {
        Optional<Order> optionalOrder = od.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setCompleted(completeStatus);
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
                try {
                    deletedOrder = this.getCurrentOrderForUser(user.getUsername());
                    if(deletedOrder.getApproved()) {
                        throw new EntityExistsException("Cannot delete an approved order.");
                    }
                } catch(EntityNotFoundException e) {
                    throw new EntityNotFoundException("No current order found for user: " + user.getUsername());
                }
                deletedOrder=od.getByUserAndIsCompleted(user, false); // od = OrderDAO
                od.deleteByUserAndIsCompleted(user, false); // od = OrderDAO
                return deletedOrder;
            }
        }
        throw new FailedLoginException("Invalid credentials provided.");
    }


    public List<Order> getCurrentAndPastOrders() {
        return od.findAll();
    }

    public List<Order> getAllOrdersForUser(String username) {
        Optional<User> optionalUser = ud.findByUsername(username);
        if(optionalUser.isPresent()) {
            return od.findAllByUser(optionalUser.get());
        }
        throw new EntityNotFoundException("No User found with username: " + username);
    }

    public Order getCurrentOrderForUser(String username) {
        Optional<User> optionalUser = ud.findByUsername(username);
        if(optionalUser.isPresent()) {
            List<Order> orders = od.findAllByUser(optionalUser.get());
            for(Order order : orders) {
                if(!order.getCompleted()) {
                    return order;
                }
            }
            throw new EntityNotFoundException(username + "has no current order");
        }
        throw new EntityNotFoundException("No User found with username: " + username);
    }

    public Order createOrder(int vehicleId, User login) throws FailedLoginException {
        Optional<Vehicle> optionalVehicle = vd.findById(vehicleId);
        Optional<User> optionalUser = ud.findByUsername(login.getUsername());
        if(optionalVehicle.isPresent() &&
                optionalUser.isPresent() && login.getPassword().equals(optionalUser.get().getPassword())) {
            List<Order> orders = od.findAllByUser(optionalUser.get());
            for(Order order : orders) {
                if(!order.getCompleted()) {
                    throw new EntityExistsException("User already has a pending order.");
                }
            }
            Order order = new Order(optionalVehicle.get(), optionalUser.get());
            return od.save(order);
        } else if(optionalVehicle.isPresent() &&
                optionalUser.isPresent() && !login.getPassword().equals(optionalUser.get().getPassword())) {
            throw new FailedLoginException("Incorrect Username or Password");
        } else if(optionalUser.isEmpty()) {
            throw new EntityNotFoundException("No User found with username: " + login.getUsername());
        }
        throw new EntityNotFoundException("No Vehicle found with id: " + vehicleId);
    }

    public void updateOrderCompletionStatus(int id, Boolean completionStatus) {
        Optional<Order> optionalOrder = od.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setCompleted(completionStatus);
            od.save(order);
        } else {
            throw new EntityNotFoundException("No Order found with id: " + id);
        }
    }


}
