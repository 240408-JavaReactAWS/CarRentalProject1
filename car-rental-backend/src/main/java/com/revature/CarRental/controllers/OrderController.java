package com.revature.CarRental.controllers;

import com.revature.CarRental.models.Order;
import com.revature.CarRental.models.User;
import com.revature.CarRental.services.OrderService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.FailedLoginException;

import static org.springframework.http.HttpStatus.*;


@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    private OrderService os;

    @Autowired
    public OrderController(OrderService os) {
        this.os = os;
    }


    /**
     * ADMIN - ORDER APPROVAL
     */
    @PatchMapping("{id}")
    public ResponseEntity<Order> updateOrderApprovalStatusHandler(@PathVariable int id, @RequestBody Boolean approvalStatus, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getAdmin() ) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }
        Order order;
        try {
            order = os.updateOrderApprovalStatus(id, approvalStatus);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        }

        return new ResponseEntity<>(order, OK);
    }

    /**
     * ADMIN - ORDER COMPLETION
     */
    @PatchMapping("/complete/{id}")
    public ResponseEntity<Order> updateOrderCompleteStatusHandler(@PathVariable int id, @RequestBody Boolean completeStatus, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getAdmin() ) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }
        Order order;
        try {
            order = os.updateOrderCompleteStatus(id, completeStatus);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(order, OK);
    }

    /**
     * ADMIN - VIEW ALL ORDERS
     */
    @GetMapping("/allorders")
    public ResponseEntity<List<Order>> getCurrentAndPastOrdersHandler(HttpSession session) {
      User user = (User) session.getAttribute("user");
      if (user == null || !user.getAdmin() ) {
          return new ResponseEntity<>(UNAUTHORIZED);
      }
      return new ResponseEntity<>(os.getCurrentAndPastOrders(), OK);
    }

    /**
     * ADMIN - VIEW PENDING ORDERS
     */
    @GetMapping("/pendingorders")
    public ResponseEntity<List<Order>> getPendingOrdersHandler(HttpSession session) {
      User user = (User) session.getAttribute("user");
      if (user == null || !user.getAdmin() ) {
          return new ResponseEntity<>(UNAUTHORIZED);
      }
      return new ResponseEntity<>(os.getPendingOrders(), OK);
    }

    /**
     * ADMIN - VIEW COMPLETED ORDERS
     */
    @GetMapping("/completedorders")
    public ResponseEntity<List<Order>> getCompletedOrdersHandler(HttpSession session) {
      User user = (User) session.getAttribute("user");
      if (user == null || !user.getAdmin() ) {
          return new ResponseEntity<>(UNAUTHORIZED);
      }
      return new ResponseEntity<>(os.getCompletedOrders(), OK);
    }

    /**
     * USER - VIEW ALL ORDERS
     */
    @GetMapping("/myOrder")
    public ResponseEntity<List<Order>> getAllOrdersForUser(HttpSession session) {
      User user = (User) session.getAttribute("user");
      if (user == null) {
          return new ResponseEntity<>(UNAUTHORIZED);
      }
      return new ResponseEntity<>(os.getAllOrdersForUser(user.getUsername()), OK);
    }

    /**
     * USER - VIEW CURRENT ORDER
     */
    @GetMapping("/current")
    public ResponseEntity<Order> getCurrentOrderForUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }
        Order order;
        try {
            order = os.getCurrentOrderForUser( user.getUsername() );
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(order, OK);
    }

    /**
     * USER - ORDER CREATION
     */
    @PostMapping("/placeorder/{vehicleId}")
    public ResponseEntity<Order> createOrderHandler(@PathVariable int vehicleId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }
        Order order;
        try {
            order = os.createOrder(vehicleId, user);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        } catch (EntityExistsException e) {
            return new ResponseEntity<>(CONFLICT);
        }
        return new ResponseEntity<>(order, CREATED);
    }


    /**
     * USER - ORDER CANCELLATION
     */
    @DeleteMapping("/current")
    public ResponseEntity<Order> cancelOrderHandler(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }
        Order order;
        try {
            order = os.deleteOrder(user); // os = OrderService
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        } catch (EntityExistsException e) {
            return new ResponseEntity<>(CONFLICT);
        }
        return new ResponseEntity<>(order, NO_CONTENT);
    }

}