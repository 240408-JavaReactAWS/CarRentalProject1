package com.revature.CarRental.services;

import com.revature.CarRental.repos.OrderDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private OrderDAO od;

    @Autowired
    public OrderService(OrderDAO od) {
        this.od = od;
    }



}
