package com.revature.CarRental.models;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    // Data Fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    private String dateAndTime;

    // Foreign
    private int vehicleId;
    private int userId;

    private Boolean isApproved;
    private Boolean isCompleted;

    // Constructors

    public Order() {
    }

    public Order(String dateAndTime, int vehicleId, int userId) {
        this.dateAndTime = dateAndTime;
        this.vehicleId = vehicleId;
        this.userId = userId;
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

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }
}
