package com.example.bilabonnement.model;

import java.util.Date;

public class leaseContract {
    private int leaseID;
    private Date startDate;
    private Date endDate;
    private double monthlyPrice;

    public leaseContract(int leaseID, Date startDate, Date endDate, double monthlyPrice) {
        this.leaseID = leaseID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.monthlyPrice = monthlyPrice;
    }

    public int leaseID() {
        return leaseID;
    }

    public void setLeaseID(int leaseID) {
        this.leaseID = leaseID;
    }

    public Date startDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date endDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double monthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(double monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }


}
