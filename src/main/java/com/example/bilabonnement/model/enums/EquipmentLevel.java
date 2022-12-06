package com.example.bilabonnement.model.enums;

public enum EquipmentLevel {
    // actual enums
    BASE("basis"),
    MEDIUM("medium"),
    LARGE("stor");

    // Storing name
    private String string;

    // constructor to set the string
    EquipmentLevel(String name) {
        string = name;
    }

    public String danish() {
        return string;
    }
}
