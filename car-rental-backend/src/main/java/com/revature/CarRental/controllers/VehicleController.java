package com.revature.CarRental.controllers;


import com.revature.CarRental.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private VehicleService vs;

    @Autowired
    public VehicleController(VehicleService vs) {
        this.vs = vs;
    }



}
