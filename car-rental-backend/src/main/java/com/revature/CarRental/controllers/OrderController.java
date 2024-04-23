package com.revature.CarRental.controllers;

import com.revature.CarRental.models.Order;
import com.revature.CarRental.services.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;


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


}
