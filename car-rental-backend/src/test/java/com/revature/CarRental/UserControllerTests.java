package com.revature.CarRental;

import java.util.List;
import com.revature.CarRental.models.User;
import com.revature.CarRental.controllers.UserController;
import com.revature.CarRental.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;

import javax.security.auth.login.FailedLoginException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTests {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private HttpSession session;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        session = new MockHttpSession();
    }

    @Test
    public void registerNewUserHandler_SuccessfulRegistration() throws Exception {
        User user = new User();
        when(userService.createUser(any(User.class))).thenReturn(user);

        ResponseEntity<User> response = userController.registerNewUserHandler(user, session);

        assertEquals(HttpStatus.valueOf(201), response.getStatusCode());
        assertEquals(user, session.getAttribute("user"));
    }

    @Test
    public void registerNewUserHandler_UserAlreadyExists() throws Exception {
        User user = new User();
        when(userService.createUser(any(User.class))).thenThrow(EntityExistsException.class);

        ResponseEntity<User> response = userController.registerNewUserHandler(user, session);

        assertEquals(HttpStatus.valueOf(400), response.getStatusCode());
    }

    @Test
    public void loginHandler_SuccessfulLogin() throws Exception {
        User user = new User();
        when(userService.login(any(User.class))).thenReturn(user);

        ResponseEntity<User> response = userController.loginHandler(user, session);

        assertEquals(HttpStatus.valueOf(200), response.getStatusCode());
        assertEquals(user, session.getAttribute("user"));
    }

    @Test
    public void loginHandler_FailedLogin() throws Exception {
        User user = new User();
        when(userService.login(any(User.class))).thenThrow(FailedLoginException.class);

        ResponseEntity<User> response = userController.loginHandler(user, session);

        assertEquals(HttpStatus.valueOf(401), response.getStatusCode());
    }

    @Test
    public void logoutHandler_SuccessfulLogout() {
        ResponseEntity<User> response = userController.logoutHandler(session);

        assertEquals(HttpStatus.valueOf(200), response.getStatusCode());
    }

    @Test
    public void getCurrentUserHandler_UserLoggedIn() {
        User user = new User();
        session.setAttribute("user", user);

        ResponseEntity<User> response = userController.getCurrentUserHandler(session);

        assertEquals(HttpStatus.valueOf(200), response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void getCurrentUserHandler_NoUserLoggedIn() {
        ResponseEntity<User> response = userController.getCurrentUserHandler(session);

        assertEquals(HttpStatus.valueOf(401), response.getStatusCode());
    }

    @Test
    public void getAllUsers_AdminUser() {
        User user = new User();
        user.setAdmin(true);
        session.setAttribute("user", user);

        ResponseEntity<List<User>> response = userController.getAllUsers(session);

        assertEquals(HttpStatus.valueOf(200), response.getStatusCode());
    }

    @Test
    public void getAllUsers_NonAdminUser() {
        User user = new User();
        user.setAdmin(false);
        session.setAttribute("user", user);

        ResponseEntity<List<User>> response = userController.getAllUsers(session);

        assertEquals(HttpStatus.valueOf(401), response.getStatusCode());
    }

    @Test
    public void getAllUsers_NoUserLoggedIn() {
        ResponseEntity<List<User>> response = userController.getAllUsers(session);

        assertEquals(HttpStatus.valueOf(401), response.getStatusCode());
    }
}