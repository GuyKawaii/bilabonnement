package com.example.bilabonnement.model;

import java.time.LocalDateTime;

public class DamageReport {
    private Integer id;
    private LocalDateTime datetime;
    private int leaseID;
    private int vehicleID;

    // create with ID
    public DamageReport(Integer id, LocalDateTime datetime, int leaseID, int vehicleNumber) {
        this.id = id;
        this.datetime = datetime;
        this.leaseID = leaseID;
        this.vehicleID = vehicleNumber;
    }

    // create without ID
    public DamageReport(LocalDateTime datetime, int leaseID, int vehicleNumber) {
        this.datetime = datetime;
        this.leaseID = leaseID;
        this.vehicleID = vehicleNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public int getLeaseID() {
        return leaseID;
    }

    public void setLeaseID(int leaseID) {
        this.leaseID = leaseID;
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }
}
