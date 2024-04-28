package com.revature.CarRental;

import com.revature.CarRental.controllers.OrderController;
import com.revature.CarRental.models.Order;
import com.revature.CarRental.models.User;
import com.revature.CarRental.services.OrderService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrderControllerTests {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    private HttpSession session;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        session = new MockHttpSession();
    }

    @Test
    public void updateOrderApprovalStatusHandler_AdminUser_UpdatesApprovalStatus() {
        User user = new User();
        user.setAdmin(true);
        session.setAttribute("user", user);
        Order order = new Order();
        when(orderService.updateOrderApprovalStatus(anyInt(), anyBoolean())).thenReturn(order);

        ResponseEntity<Order> response = orderController.updateOrderApprovalStatusHandler(1, true, session);

        assertEquals(HttpStatus.valueOf(200), response.getStatusCode());
        assertEquals(order, response.getBody());
    }

    @Test
    public void updateOrderApprovalStatusHandler_NonAdminUser_Unauthorized() {
        User user = new User();
        user.setAdmin(false);
        session.setAttribute("user", user);

        ResponseEntity<Order> response = orderController.updateOrderApprovalStatusHandler(1, true, session);

        assertEquals(HttpStatus.valueOf(401), response.getStatusCode());
    }

}