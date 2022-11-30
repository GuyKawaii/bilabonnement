package com.example.bilabonnement.model;

// One entry of a damage report
public class DamageEntry {
    private Integer damageEntryID;
    private String damageTitle;
    private String damageDescription;
    private double damagePrice;
    private int damageReportID;

    public DamageEntry(String damageTitle, String damageDescription, double damagePrice, int damageReportID) {
        this.damageTitle = damageTitle;
        this.damageDescription = damageDescription;
        this.damagePrice = damagePrice;
        this.damageReportID = damageReportID;
    }

    public DamageEntry(int damageEntryID, String damageTitle, String damageDescription, double damagePrice, int damageReportID) {
        this.damageEntryID = damageEntryID;
        this.damageTitle = damageTitle;
        this.damageDescription = damageDescription;
        this.damagePrice = damagePrice;
        this.damageReportID = damageReportID;
    }

    public Integer getDamageEntryID() {
        return damageEntryID;
    }

    public void setDamageEntryID(Integer damageEntryID) {
        this.damageEntryID = damageEntryID;
    }

    public String getDamageTitle() {
        return damageTitle;
    }

    public void setDamageTitle(String damageTitle) {
        this.damageTitle = damageTitle;
    }

    public String getDamageDescription() {
        return damageDescription;
    }

    public void setDamageDescription(String damageDescription) {
        this.damageDescription = damageDescription;
    }

    public double getDamagePrice() {
        return damagePrice;
    }

    public void setDamagePrice(double damagePrice) {
        this.damagePrice = damagePrice;
    }

    public int getDamageReportID() {
        return damageReportID;
    }

    public void setDamageReportID(int damageReportID) {
        this.damageReportID = damageReportID;
    }
}
