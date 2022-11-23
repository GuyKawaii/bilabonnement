package com.example.bilabonnement.model;



// TEST CLASS THAT IS THE ALL SEEING EYE
public class Admin {
  private String email;
  private String name;
  private int id;
  private String password;


    public Admin(String email, String name) {
      this.name = name;
      this.email = email;
    }
    public Admin(String email, String password, String name) {
      this.name = name;
      this.email = email;
      this.password = password;

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
  }

