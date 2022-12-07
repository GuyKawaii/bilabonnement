package com.example.bilabonnement.model.enums;

public enum State {
    // actual enums
    RETURNED("returneret"),
    AT_CUSTOMER("hos kunde"),
    READY("klar");

    // Storing name
    private String string;

    // constructor to set the string
    State(String name) {
        string = name;
    }

    public String danish() {
        return string;
    }
}
