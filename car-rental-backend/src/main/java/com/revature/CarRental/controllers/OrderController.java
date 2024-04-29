package com.revature.CarRental.controllers;

import com.revature.CarRental.models.Order;
import com.revature.CarRental.models.OrderDTO;
import com.revature.CarRental.models.User;
import com.revature.CarRental.services.OrderService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        if (user == null || !user.getAdmin()) {
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
        if (user == null || !user.getAdmin()) {
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
    public ResponseEntity<List<OrderDTO>> getCurrentAndPastOrdersHandler(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getAdmin()) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }
        List<OrderDTO> orderDTOList = new ArrayList<>();
        List<Order> orderList = os.getCurrentAndPastOrders();
        for(Order order : orderList) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setOrder(order);
            orderDTO.setUserId(order.getUser().getUserId());
            orderDTOList.add(orderDTO);
        }
        return new ResponseEntity<>(orderDTOList, OK);
    }

    /**
     * ADMIN - VIEW PENDING ORDERS
     */
    @GetMapping("/pendingorders")
    public ResponseEntity<List<OrderDTO>> getPendingOrdersHandler(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getAdmin()) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }
        List<OrderDTO> orderDTOList = new ArrayList<>();
        List<Order> orderList = os.getPendingOrders();
        for(Order order : orderList) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setOrder(order);
            orderDTO.setUserId(order.getUser().getUserId());
            orderDTOList.add(orderDTO);
        }
        return new ResponseEntity<>(orderDTOList, OK);
    }

    /**
     * ADMIN - VIEW COMPLETED ORDERS
     */
    @GetMapping("/completedorders")
    public ResponseEntity<List<OrderDTO>> getCompletedOrdersHandler(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getAdmin()) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }
        List<OrderDTO> orderDTOList = new ArrayList<>();
        List<Order> orderList = os.getCompletedOrders();
        for(Order order : orderList) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setOrder(order);
            orderDTO.setUserId(order.getUser().getUserId());
            orderDTOList.add(orderDTO);
        }
        return new ResponseEntity<>(orderDTOList, OK);
    }

    /**
     * USER - VIEW ALL ORDERS
     */
    @GetMapping("/myorders")
    public ResponseEntity<List<OrderDTO>> getAllOrdersForUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }
        List<OrderDTO> orderDTOList = new ArrayList<>();
        List<Order> orderList = os.getAllOrdersForUser(user.getUsername());
        for(Order order : orderList) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setOrder(order);
            orderDTO.setUserId(order.getUser().getUserId());
            orderDTOList.add(orderDTO);
        }
        return new ResponseEntity<>(orderDTOList, OK);
    }

    /**
     * USER - VIEW CURRENT ORDER
     */
    @GetMapping("/current")
    public ResponseEntity<OrderDTO> getCurrentOrderForUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }
        OrderDTO orderDTO = new OrderDTO();
        try {
            orderDTO.setOrder(os.getCurrentOrderForUser(user.getUsername()));
            orderDTO.setUserId(orderDTO.getOrder().getUser().getUserId());
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(orderDTO, OK);
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