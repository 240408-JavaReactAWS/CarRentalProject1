package com.revature.CarRental.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    // Data Fields

    @Id
    @Column (name = "userId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;

    @OneToOne
    @JoinColumn(name = "current_car_id")
    private Vehicle currentCar;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Order> allOrders;

    private Boolean isAdmin = false;

    // Constructors

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, Vehicle currentCar, List<Order> allOrders, Boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.currentCar = currentCar;
        this.allOrders = allOrders;
        this.isAdmin = isAdmin;
    }

    public User(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.currentCar = null;
        this.allOrders = new ArrayList<Order>();
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

    @JsonProperty(value="isAdmin")
    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", currentCar=" + currentCar +
                ", allOrders=" + allOrders +
                ", isAdmin=" + isAdmin +
                '}';
    }
}