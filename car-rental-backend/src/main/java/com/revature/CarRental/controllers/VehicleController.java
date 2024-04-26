package com.revature.CarRental.controllers;


import com.revature.CarRental.models.Location;
import com.revature.CarRental.models.Order;
import com.revature.CarRental.models.User;
import com.revature.CarRental.models.Vehicle;
import com.revature.CarRental.repos.OrderDAO;
import com.revature.CarRental.services.LocationService;
import com.revature.CarRental.services.OrderService;
import com.revature.CarRental.services.UserService;
import com.revature.CarRental.services.VehicleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.FailedLoginException;

import java.util.List;

import static org.springframework.http.HttpStatus.*;


@RestController
@RequestMapping("/vehicles")
@CrossOrigin(origins = "http://localhost:3000")
public class VehicleController {

    private VehicleService vs;
    private LocationService ls;
    private UserService us;
    private OrderDAO od;
    private OrderService os;

    @Autowired
    public VehicleController(VehicleService vs, UserService us, OrderDAO od, OrderService os) {
        this.vs = vs;
        this.us = us;
        this.od = od;
        this.os = os;
    }

    @GetMapping
    public ResponseEntity<List<Vehicle>> viewAllVehiclesHandler() {
        return new ResponseEntity<>(vs.getAllVehicles(), OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Vehicle> updateVehicleLocationHandler(@PathVariable int id, @RequestBody int newLocationId) {
        Vehicle vehicle;
        Location newLocation;
        try {
            newLocation = ls.getLocationById(newLocationId);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        }

        try {
            vehicle = vs.updateVehicleLocation(id, newLocation);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(vehicle, OK);
    }

    //The id field here is for location id.
    @PostMapping("/{id}/add")
    public ResponseEntity<Vehicle> addVehicleHandler(@PathVariable int id, @RequestBody Vehicle vehicle) {
        try { // Check if location exists
            vs.addVehicle(id, vehicle);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(vehicle, CREATED);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Vehicle> removeVehicleHandler(@PathVariable int id) {
        try {
            vs.removeVehicle(id);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(NO_CONTENT);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Vehicle> updateVehicleHandler(@PathVariable int id, @RequestBody Vehicle vehicle) {
        try {
            vs.updateVehicle(id, vehicle);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(vehicle, OK);
    }

    /**
     * VEHICLE PICKUP by USER
     * Endpoint: GET localhost:8080/vehicles/pickup.
     *
     * @ResponseBody JSON of the vehicle being picked up by the user.
     * @ResponseStatus default, 200 (OK).
     */
    @PatchMapping("/pickup")
    public ResponseEntity<Vehicle> pickupVehicleHandler(@RequestBody User credentials) throws FailedLoginException {
        User user;
        try {
            user = us.login(credentials);
        } catch (FailedLoginException e) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }
        Order order;
        try {
            order = os.getCurrentOrderForUser(user.getUsername());
        } catch( EntityNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND); // User has no pending order
        }
        if(!order.getApproved()) {
            return new ResponseEntity<>(BAD_REQUEST); // Order is not approved
        }
        Vehicle vehicle;
        try {
            vehicle = us.getCurrentCar(user.getUserId());
        } catch( EntityNotFoundException e) {
            vehicle = null;
        }
        if(vehicle != null) { // Check if user already has a vehicle
            return new ResponseEntity<>(CONFLICT); // User already has a vehicle
        }
        vs.updateVehicleAvailability(order.getVehicle().getId(), false); // Mark vehicle as unavailable for others
        us.updateCurrentCar(user.getUserId(), order.getVehicle());  // Assign vehicle to user
        return new ResponseEntity<>(order.getVehicle(), OK);
    }

    /**
     * VEHICLE RETURN by USER
     * Endpoint: GET localhost:8080/vehicles/return
     *
     * @ResponseBody JSON of the vehicle being returned by the user.
     * @ResponseStatus default, 200 (OK).
     */
    @PatchMapping("/return")
    public ResponseEntity<Vehicle> returnVehicleHandler(@RequestBody User credentials) throws FailedLoginException {
        User user;
        try {
            user = us.login(credentials);
        } catch (FailedLoginException e) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }
        Order order;
        try {
            order = os.getCurrentOrderForUser(user.getUsername());
        } catch( EntityNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND); // User has no pending order
        }
        Vehicle vehicle;
        try {
            vehicle = us.getCurrentCar(user.getUserId());
        } catch( EntityNotFoundException e) {
            vehicle = null;
        }
        if(vehicle == null ) { // Check if user has a vehicle
            return new ResponseEntity<>(BAD_REQUEST); // User has no vehicle
        }
        us.updateCurrentCar(user.getUserId(), null); // Remove vehicle from user
        os.updateOrderCompletionStatus(order.getOrderId(), true); // Mark order as completed
        vs.updateVehicleAvailability(vehicle.getId(), true); // Mark vehicle as available for others
        return new ResponseEntity<>(vehicle, OK);
    }




}
