package com.example.bilabonnement.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DamageReport {
    private Integer damageReportID;
    private int vehicleID;
    private int employeeID;
    private Timestamp timestamp;

    // create with ID
    public DamageReport(Integer damageReportID, int employeeID , int vehicleID, Timestamp timestamp) {
        this.damageReportID = damageReportID;
        this.employeeID = employeeID;
        this.vehicleID = vehicleID;
        this.timestamp = timestamp;
    }

    // create without ID
    public DamageReport(int employeeID , int vehicleID, Timestamp timestamp) {
        this.employeeID = employeeID;
        this.vehicleID = vehicleID;
        this.timestamp = timestamp;
    }

    public Integer getDamageReportID() {
        return damageReportID;
    }

    public void setDamageReportID(Integer damageReportID) {
        this.damageReportID = damageReportID;
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
