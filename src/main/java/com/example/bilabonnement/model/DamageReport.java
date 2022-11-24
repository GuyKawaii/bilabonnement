package com.example.bilabonnement.model;

import java.time.LocalDateTime;

public class DamageReport {
    private int id;
    private LocalDateTime datetime;
    private int leaseID;
    private int vehicleNumber;

    public DamageReport(int id, LocalDateTime datetime, int leaseID, int vehicleNumber) {
        this.id = id;
        this.datetime = datetime;
        this.leaseID = leaseID;
        this.vehicleID = vehicleNumber;
    }

    public int id() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime datetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public int leaseID() {
        return leaseID;
    }

    public void setLeaseID(int leaseID) {
        this.leaseID = leaseID;
    }

    public int vehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(int vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }


}
