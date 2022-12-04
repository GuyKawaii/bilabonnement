package com.example.bilabonnement.model;

public class Customer {
    private Integer customerID;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String city;
    private int postalCode;
    private String mobile;
    private String cprNumber;

    public Customer(Integer customerID, String firstName, String lastName, String email, String address, String city, int postalCode, String mobile, String CPRNumber) {
        this.customerID = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.mobile = mobile;
        this.cprNumber = CPRNumber;
    }

    public Customer(String firstName, String lastName, String email, String address, String city, int postalCode, String mobile, String CPRNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.mobile = mobile;
        this.cprNumber = CPRNumber;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCprNumber() {
        return cprNumber;
    }

    public void setCprNumber(String cprNumber) {
        this.cprNumber = cprNumber;
    }
}
