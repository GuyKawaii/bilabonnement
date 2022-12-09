package com.example.bilabonnement.model.enums;

// used for website access
public enum Role {
    // Storing name
    ADMINISTRATION("Administrator"),
    DAMAGE_REPORTER("Skaderapportør"),
    DATA_REGISTRATION("Data registerer"),
    BUSINESS_DEVELOPER("Forretningsudvikler");

    // Storing name
    private String string;

    // constructor to set the string
    Role(String name) {
        string = name;
    }

    public String danish() {
        return string;
    }
}
