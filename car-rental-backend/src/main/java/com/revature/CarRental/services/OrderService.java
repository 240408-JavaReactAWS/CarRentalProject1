package com.revature.CarRental.services;

import com.revature.CarRental.models.Order;
import com.revature.CarRental.models.User;
import com.revature.CarRental.models.Vehicle;
import com.revature.CarRental.repos.OrderDAO;
import com.revature.CarRental.repos.UserDAO;
import com.revature.CarRental.repos.VehicleDAO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.security.auth.login.FailedLoginException;
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
            order.setCompleted(true);
            od.save(order);
            return order;
        }
        throw new EntityNotFoundException("No Order found with id: " + id);
    }


    public Order createOrder(int vehicleId, User login) throws FailedLoginException {
        Optional<Vehicle> optionalVehicle = vd.findById(vehicleId);
        Optional<User> optionalUser = ud.findByUsername(login.getUsername());
        if(optionalVehicle.isPresent() &&
                optionalUser.isPresent() && login.getPassword().equals(optionalUser.get().getPassword())) {
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
}
