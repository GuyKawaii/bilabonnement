package com.example.bilabonnement.model.enums;

public enum State {
    // actual enums
    READY("klar"),
    IS_LEASED("leased"),
    UNDER_REPAIR("reperation"),
    DAMAGED("skadet");

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
