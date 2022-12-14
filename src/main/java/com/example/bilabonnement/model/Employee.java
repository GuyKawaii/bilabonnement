package com.example.bilabonnement.model;


import com.example.bilabonnement.model.enums.Role;

public class Employee {
    private Integer employeeID;
    private String email;
    private String name;
    private String password;
    private Role role;

    // create with ID
    public Employee(int employeeID, String email, String name, String password,  Role role) {
        this.employeeID = employeeID;
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    // create without ID
    public Employee(String email, String name, Role role, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public Integer getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Integer employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

