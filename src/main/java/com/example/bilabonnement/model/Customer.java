package com.example.bilabonnement.model;

public class Customer {
    private int id;
    private int firstName;
    private int lastName;
    private int email;

    public Customer(int id, int firstName, int lastName, int email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public int id() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int firstName() {
        return firstName;
    }

    public void setFirstName(int firstName) {
        this.firstName = firstName;
    }

    public int lastName() {
        return lastName;
    }

    public void setLastName(int lastName) {
        this.lastName = lastName;
    }

    public int email() {
        return email;
    }

    public void setEmail(int email) {
        this.email = email;
    }
}
