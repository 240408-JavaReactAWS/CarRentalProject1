package com.revature.CarRental;

import com.revature.CarRental.controllers.LocationController;
import com.revature.CarRental.models.Location;
import com.revature.CarRental.models.User;
import com.revature.CarRental.models.Vehicle;
import com.revature.CarRental.services.LocationService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LocationControllerTests {

    @InjectMocks
    private LocationController locationController;

    @Mock
    private LocationService locationService;

    private HttpSession session;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        session = new MockHttpSession();
    }

    @Test
    public void viewAllLocationsHandler_ReturnsAllLocations() {
        List<Location> locations = Collections.singletonList(new Location());
        when(locationService.getAllLocations()).thenReturn(locations);

        List<Location> response = locationController.viewAllLocationsHandler();

        assertEquals(locations, response);
    }

    @Test
    public void viewAllVehiclesAtLocationHandler_ReturnsAllVehiclesAtLocation() {
        List<Vehicle> vehicles = Collections.singletonList(new Vehicle());
        when(locationService.getAllVehiclesAtLocation(anyInt())).thenReturn(vehicles);

        List<Vehicle> response = locationController.viewAllVehiclesAtLocationHandler(1);

        assertEquals(vehicles, response);
    }

    @Test
    public void addLocationHandler_AdminUser_AddsLocation() {
        User user = new User();
        user.setAdmin(true);
        session.setAttribute("user", user);
        Location location = new Location();
        when(locationService.addLocation(any(Location.class))).thenReturn(location);

        ResponseEntity<Location> response = locationController.addLocationHandler(location, session);

        assertEquals(HttpStatus.valueOf(201), response.getStatusCode());
    }

    @Test
    public void addLocationHandler_NonAdminUser_Unauthorized() {
        User user = new User();
        user.setAdmin(false);
        session.setAttribute("user", user);
        Location location = new Location();

        ResponseEntity<Location> response = locationController.addLocationHandler(location, session);

        assertEquals(HttpStatus.valueOf(401), response.getStatusCode());
    }

    @Test
    public void removeLocationHandler_AdminUser_RemovesLocation() {
        User user = new User();
        user.setAdmin(true);
        session.setAttribute("user", user);

        ResponseEntity<Location> response = locationController.removeLocationHandler(1, session);

        assertEquals(HttpStatus.valueOf(204), response.getStatusCode());
    }

    @Test
    public void removeLocationHandler_NonAdminUser_Unauthorized() {
        User user = new User();
        user.setAdmin(false);
        session.setAttribute("user", user);

        ResponseEntity<Location> response = locationController.removeLocationHandler(1, session);

        assertEquals(HttpStatus.valueOf(401), response.getStatusCode());
    }

    @Test
    public void updateLocationHandler_AdminUser_UpdatesLocation() {
        User user = new User();
        user.setAdmin(true);
        session.setAttribute("user", user);
        Location location = new Location();

        ResponseEntity<Location> response = locationController.updateLocationHandler(1, location, session);

        assertEquals(HttpStatus.valueOf(200), response.getStatusCode());
    }

    @Test
    public void updateLocationHandler_NonAdminUser_Unauthorized() {
        User user = new User();
        user.setAdmin(false);
        session.setAttribute("user", user);
        Location location = new Location();

        ResponseEntity<Location> response = locationController.updateLocationHandler(1, location, session);

        assertEquals(HttpStatus.valueOf(401), response.getStatusCode());
    }
}