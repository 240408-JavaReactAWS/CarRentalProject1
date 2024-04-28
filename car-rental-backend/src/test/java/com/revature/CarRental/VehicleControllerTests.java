package com.revature.CarRental;

import com.revature.CarRental.controllers.VehicleController;
import com.revature.CarRental.models.Location;
import com.revature.CarRental.models.Order;
import com.revature.CarRental.models.User;
import com.revature.CarRental.models.Vehicle;
import com.revature.CarRental.services.LocationService;
import com.revature.CarRental.services.OrderService;
import com.revature.CarRental.services.UserService;
import com.revature.CarRental.services.VehicleService;
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

public class VehicleControllerTests {

    @InjectMocks
    private VehicleController vehicleController;

    @Mock
    private VehicleService vehicleService;

    @Mock
    private UserService userService;

    @Mock
    private OrderService orderService;

    @Mock
    private LocationService locationService;

    private HttpSession session;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        session = new MockHttpSession();
    }

    @Test
    public void viewAllVehiclesHandler_ReturnsAllVehicles() {
        List<Vehicle> vehicles = Collections.singletonList(new Vehicle());
        when(vehicleService.getAllVehicles()).thenReturn(vehicles);

        ResponseEntity<List<Vehicle>> response = vehicleController.viewAllVehiclesHandler();

        assertEquals(HttpStatus.valueOf(200), response.getStatusCode());
        assertEquals(vehicles, response.getBody());
    }

    @Test
    public void updateVehicleLocationHandler_AdminUser_UpdatesLocation() {
        User user = new User();
        user.setAdmin(true);
        session.setAttribute("user", user);
        Location location = new Location();
        Vehicle vehicle = new Vehicle();
        when(locationService.getLocationById(anyInt())).thenReturn(location);
        when(vehicleService.updateVehicleLocation(anyInt(), any(Location.class))).thenReturn(vehicle);

        ResponseEntity<Vehicle> response = vehicleController.updateVehicleLocationHandler(1, 1, session);

        assertEquals(HttpStatus.valueOf(200), response.getStatusCode());
        assertEquals(vehicle, response.getBody());
    }

    @Test
    public void updateVehicleLocationHandler_NonAdminUser_Unauthorized() {
        User user = new User();
        user.setAdmin(false);
        session.setAttribute("user", user);

        ResponseEntity<Vehicle> response = vehicleController.updateVehicleLocationHandler(1, 1, session);

        assertEquals(HttpStatus.valueOf(401), response.getStatusCode());
    }

    // Add similar tests for other methods in the VehicleController class
}