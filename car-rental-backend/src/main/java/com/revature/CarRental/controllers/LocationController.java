package com.revature.CarRental.controllers;

import com.revature.CarRental.models.Location;
import com.revature.CarRental.models.Vehicle;
import com.revature.CarRental.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/add")
    public ResponseEntity<Location> addLocationHandler(@RequestBody Location location) {
        ls.addLocation(location);
        return new ResponseEntity<>(location, HttpStatus.CREATED);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Location> removeLocationHandler(@PathVariable int id) {
        try {
            ls.removeLocation(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Location> updateLocationHandler(@PathVariable int id, @RequestBody Location location) {
        try {
            ls.updateLocation(id, location);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(location, HttpStatus.OK);
    }


}
