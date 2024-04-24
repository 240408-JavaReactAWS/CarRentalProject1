package com.revature.CarRental.controllers;

import com.revature.CarRental.models.Order;
import com.revature.CarRental.models.User;
import com.revature.CarRental.services.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.security.auth.login.FailedLoginException;

import static org.springframework.http.HttpStatus.*;


@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService os;

    @Autowired
    public OrderController(OrderService os) {
        this.os = os;
    }


    @PatchMapping("{id}")
    public ResponseEntity<Order> updateOrderApprovalStatusHandler(@PathVariable int id, @RequestBody Boolean approvalStatus) {
        Order order;
        try {
            order = os.updateOrderApprovalStatus(id, approvalStatus);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        }

        return new ResponseEntity<>(order, OK);
    }

    /**
     * ORDER CANCELLATION by USER
     * Endpoint: DELETE localhost:8080/locations/location.
     *
     * @ResponseBody JSON of the deleted Order.
     * @ResponseStatus NO_CONTENT (204) if successfully deleted, NOT_FOUND (404) otherwise .
     */
    @DeleteMapping("/myorder")
    public ResponseEntity<Order> cancelOrderHandler(@PathVariable User credentials) {
        Order order;
        try {
            order = os.deleteOrder(credentials); // os = OrderService
        } catch (EntityNotFoundException | FailedLoginException e) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(order, NO_CONTENT);
    }


    @GetMapping("/allorders")
    public ResponseEntity<List<Order>> getCurrentAndPastOrdersHandler() {
        return new ResponseEntity<>(os.getCurrentAndPastOrders(), OK);
    }
    @PostMapping
    public ResponseEntity<Order> createOrderHandler(@RequestBody VehicleUserDTO orderDTO) {
        Order order;
        try {
            order = os.createOrder(orderDTO.vehicleId, orderDTO.login);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        } catch (FailedLoginException e) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }
        return new ResponseEntity<>(order, OK);
    }


}

class VehicleUserDTO {
    int vehicleId;
    User login;

    public void setLogin(User login) {
        this.login = login;
    }

    public User getLogin() {
        return login;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getVehicleId() {
        return vehicleId;
    }
}
