package com.example.bilabonnement.model;


import com.example.bilabonnement.model.enums.Role;

public class Employee {
    private String email;
    private String name;
    private int id;
    private String password;
    private Role role;

    public Employee(int id, String email, String name,  Role role) {
        this.email = email;
        this.name = name;
        this.id = id;
        this.role = role;
    }
    public Employee(int id, String email, String name, Role role, String password) {
        this.email = email;
        this.name = name;
        this.id = id;
        this.password = password;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    public Role getRole() {return role;}
}

