package com.example.bilabonnement.model;

import com.example.bilabonnement.model.enums.FuelType;
import com.example.bilabonnement.model.enums.State;

public class Car {
    private int vehicleNumber;
    private String steelNumber;
    private String color;
    private String brand;
    private String model;
    private int co2emission;
    private String geartype;
    private int kmPerLiter;
    private FuelType fuelType;
    private int kmDriven;
    private State state;

    public Car(int vehicleNumber, String steelNumber, String color, String brand, String model, int co2emission, String geartype, int kmPerLiter, FuelType fuelType, int kmDriven, State state) {
        this.vehicleNumber = vehicleNumber;
        this.steelNumber = steelNumber;
        this.color = color;
        this.brand = brand;
        this.model = model;
        this.co2emission = co2emission;
        this.geartype = geartype;
        this.kmPerLiter = kmPerLiter;
        this.fuelType = fuelType;
        this.kmDriven = kmDriven;
        this.state = state;
    }

    public int vehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(int vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String steelNumber() {
        return steelNumber;
    }

    public void setSteelNumber(String steelNumber) {
        this.steelNumber = steelNumber;
    }

    public String color() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String brand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String model() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int co2emission() {
        return co2emission;
    }

    public void setCo2emission(int co2emission) {
        this.co2emission = co2emission;
    }

    public String geartype() {
        return geartype;
    }

    public void setGeartype(String geartype) {
        this.geartype = geartype;
    }

    public int kmPerLiter() {
        return kmPerLiter;
    }

    public void setKmPerLiter(int kmPerLiter) {
        this.kmPerLiter = kmPerLiter;
    }

    public FuelType fuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public int kmDriven() {
        return kmDriven;
    }

    public void setKmDriven(int kmDriven) {
        this.kmDriven = kmDriven;
    }

    public State state() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
