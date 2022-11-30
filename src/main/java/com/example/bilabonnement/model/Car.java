package com.example.bilabonnement.model;

import com.example.bilabonnement.model.enums.FuelType;
import com.example.bilabonnement.model.enums.State;

public class Car {
    private Integer vehicleID;
    private String chassisNumber;
    private double steelPrice;
    private String color;
    private String brand;
    private String model;
    private int co2emission;
    private String geartype;
    private int kmPerLiter;
    private FuelType fuelType;
    private int kmDriven;
    private int locationID;
    private State state;

    public Car(Integer vehicleID, String chassisNumber, double steelPrice, String color, String brand, String model, int co2emission, String geartype, int kmPerLiter, FuelType fuelType, int kmDriven, int locationID, State state) {
        this.vehicleID = vehicleID;
        this.chassisNumber = chassisNumber;
        this.steelPrice = steelPrice;
        this.color = color;
        this.brand = brand;
        this.model = model;
        this.co2emission = co2emission;
        this.geartype = geartype;
        this.kmPerLiter = kmPerLiter;
        this.fuelType = fuelType;
        this.kmDriven = kmDriven;
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

    public double getSteelPrice() {
        return steelPrice;
    }

    public void setSteelPrice(double steelPrice) {
        this.steelPrice = steelPrice;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public int getCo2emission() {
        return co2emission;
    }

    public void setCo2emission(int co2emission) {
        this.co2emission = co2emission;
    }

    public String getGeartype() {
        return geartype;
    }

    public void setGeartype(String geartype) {
        this.geartype = geartype;
    }

    public int getKmPerLiter() {
        return kmPerLiter;
    }

    public void setKmPerLiter(int kmPerLiter) {
        this.kmPerLiter = kmPerLiter;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public int getKmDriven() {
        return kmDriven;
    }

    public void setKmDriven(int kmDriven) {
        this.kmDriven = kmDriven;
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
}
