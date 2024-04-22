package com.revature.CarRental.models;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {

    // Data Fields

    @Id
    @Column (name = "userId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    private String username;
    private String password;

    private Vehicle currentCar;

    private List<Order> allOrders;

    private Boolean isAdmin;

    // Constructors

    public User() {
    }

    public User(String username, String password, Vehicle currentCar, List<Order> allOrders, Boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.currentCar = currentCar;
        this.allOrders = allOrders;
        this.isAdmin = isAdmin;
    }

    // Getters & Setters

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Vehicle getCurrentCar() {
        return currentCar;
    }

    public void setCurrentCar(Vehicle currentCar) {
        this.currentCar = currentCar;
    }

    public List<Order> getAllOrders() {
        return allOrders;
    }

    public void setAllOrders(List<Order> allOrders) {
        this.allOrders = allOrders;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
