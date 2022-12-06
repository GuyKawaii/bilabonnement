package com.example.bilabonnement.model;

import com.example.bilabonnement.model.enums.EquipmentLevel;
import com.example.bilabonnement.model.enums.State;

public class Car {
    private String chassisNumber;
    private String brand;
    private String model;
    private EquipmentLevel equipmentLevel;
    private double steelPrice;
    private double registrationFee;
    private double co2emission;
    private int locationID;
    private State state;

    // with vehicleID
    public Car(Integer vehicleID, String chassisNumber, double steelPrice, String brand, String model, EquipmentLevel equipmentLevel, double registrationFee, double co2emission, int locationID, State state) {
        this.vehicleID = vehicleID;
        this.chassisNumber = chassisNumber;
        this.brand = brand;
        this.model = model;
        this.equipmentLevel = equipmentLevel;
        this.steelPrice = steelPrice;
        this.registrationFee = registrationFee;
        this.co2emission = co2emission;
        this.locationID = locationID;
        this.state = state;
    }

    // without vehicleID
    public Car(String chassisNumber, double steelPrice,String brand, String model, EquipmentLevel equipmentLevel,  double registrationFee, double co2emission, int locationID, State state) {
        this.vehicleID = null;
        this.chassisNumber = chassisNumber;
        this.brand = brand;
        this.model = model;
        this.equipmentLevel = equipmentLevel;
        this.steelPrice = steelPrice;
        this.registrationFee = registrationFee;
        this.co2emission = co2emission;
        this.locationID = locationID;
        this.state = state;
    }

    public Integer getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(Integer vehicleID) {
        this.vehicleID = vehicleID;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public EquipmentLevel getEquipmentLevel() {
        return equipmentLevel;
    }

    public void setEquipmentLevel(EquipmentLevel equipmentLevel) {
        this.equipmentLevel = equipmentLevel;
    }

    public double getSteelPrice() {
        return steelPrice;
    }

    public void setSteelPrice(double steelPrice) {
        this.steelPrice = steelPrice;
    }

    public double getRegistrationFee() {
        return registrationFee;
    }

    public void setRegistrationFee(double registrationFee) {
        this.registrationFee = registrationFee;
    }

    public double getCo2emission() {
        return co2emission;
    }

    public void setCo2emission(double co2emission) {
        this.co2emission = co2emission;
    }

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    private Integer vehicleID;
}
