package com.example.bilabonnement.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DamageReport {
    private Integer id;
    private Timestamp timestamp;
    private int leaseID;
    private int vehicleID;

    // create with ID
    public DamageReport(Integer id, Timestamp timestamp, int leaseID, int vehicleNumber) {
        this.id = id;
        this.timestamp = timestamp;
        this.leaseID = leaseID;
        this.vehicleID = vehicleNumber;
    }

    // create without ID
    public DamageReport(Timestamp timestamp, int leaseID, int vehicleNumber) {
        this.timestamp = timestamp;
        this.leaseID = leaseID;
        this.vehicleID = vehicleNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
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
