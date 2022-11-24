package com.example.bilabonnement.model;

// One entry of a damage report
public class DamageEntry {
    private int id;
    private String damageTitle;
    private String damageDescription;
    private int damagePrice;

    private int damageReportID;

    public DamageEntry(String damageTitle, String damageDescription, int damagePrice) {
        this.damageTitle = damageTitle;
        this.damageDescription = damageDescription;
        this.damagePrice = damagePrice;
    }

    public DamageEntry(int id, String damageTitle, String damageDescription, int damagePrice, int damageReportID) {
        this.id = id;
        this.damageTitle = damageTitle;
        this.damageDescription = damageDescription;
        this.damagePrice = damagePrice;
        this.damageReportID = damageReportID;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getDamagePrice() {
        return damagePrice;
    }

    public void setDamagePrice(int damagePrice) {
        this.damagePrice = damagePrice;
    }

    public int getDamageReportID() {
        return damageReportID;
    }

    public void setDamageReportID(int damageReportID) {
        this.damageReportID = damageReportID;
    }
}
