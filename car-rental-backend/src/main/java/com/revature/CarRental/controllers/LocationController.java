package com.revature.CarRental.controllers;

import com.revature.CarRental.models.Vehicle;
import com.revature.CarRental.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private LocationService ls;

    @Autowired
    public LocationController(LocationService ls) {
        this.ls = ls;
    }

     /**
     * VEHICLE RETRIEVING - ALL VEHICLES AT A LOCATION
     * Endpoint: GET localhost:8080/locations/{State}/{City}/v.
     *
     * @ResponseBody JSON of a list containing all vehicles at a location retrieved from the database or Empty list if there are no vehicles.
     * @ResponseStatus default, 200 (OK).
     */
    @GetMapping("/{state}/{city}/v")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Vehicle> viewAllVehiclesAtLocationHandler(@PathVariable String city, @PathVariable String state) {
        return ls.getAllVehiclesAtLocation(city, state); // ls = LocationService
    }

}
