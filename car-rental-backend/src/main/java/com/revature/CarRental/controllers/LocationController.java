package com.revature.CarRental.controllers;

import com.revature.CarRental.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private LocationService ls;

    @Autowired
    public LocationController(LocationService ls) {
        this.ls = ls;
    }
}
