package com.example.bilabonnement.model;

import java.sql.Date;

public class LeaseContract {
    private int leaseID;
    private Date startDate;
    private Date endDate;
    private double monthlyPrice;
    private int customerID;
    private int vehicleID;

    public LeaseContract(int leaseID, Date startDate, Date endDate, double monthlyPrice, int customerID, int vehicleID) {
        this.leaseID = leaseID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.monthlyPrice = monthlyPrice;
        this.customerID = customerID;
        this.vehicleID = vehicleID;
    }

    public LeaseContract(Date startDate, Date endDate, double monthlyPrice, int customerID, int vehicleID) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.monthlyPrice = monthlyPrice;
        this.customerID = customerID;
        this.vehicleID = vehicleID;
    }

    public int getLeaseID() {
        return leaseID;
    }

    public void setLeaseID(int leaseID) {
        this.leaseID = leaseID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(double monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }
}
