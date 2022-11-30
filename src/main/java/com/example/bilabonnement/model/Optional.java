package com.example.bilabonnement.model;

public class Optional {
    private Integer optionalID;
    private String name;
    private double pricePrMonth;

    public Optional(Integer optionalID, String name, double pricePrMonth) {
        this.optionalID = optionalID;
        this.name = name;
        this.pricePrMonth = pricePrMonth;
    }

    public Integer getOptionalID() {
        return optionalID;
    }

    public void setOptionalID(Integer optionalID) {
        this.optionalID = optionalID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPricePrMonth() {
        return pricePrMonth;
    }

    public void setPricePrMonth(double pricePrMonth) {
        this.pricePrMonth = pricePrMonth;
    }
}
