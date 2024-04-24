package com.revature.CarRental.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "orders")
public class Order {

    // Data Fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private String dateAndTime;

    // Foreign
    @ManyToOne
    private Vehicle vehicle;

    @ManyToOne
    @JsonBackReference
    private User user;

    private Boolean isApproved;
    private Boolean isCompleted;

    // Constructors

    public Order() {
    }

    public Order(Vehicle vehicle, User user) {
        this.vehicle = vehicle;
        this.user = user;
        this.isApproved = false;
        this.isCompleted = false;
    }

    public Order(String dateAndTime, Vehicle vehicle, User user) {
        this.dateAndTime = dateAndTime;
        this.vehicle = vehicle;
        this.user = user;
        this.isApproved = false;
        this.isCompleted = false;
    }

    // Setters & Getters

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonProperty(value="isApproved")
    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }

    @JsonProperty(value="isCompleted")
    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }
}
