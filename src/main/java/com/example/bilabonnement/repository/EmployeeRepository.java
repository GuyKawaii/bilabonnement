package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.Employee;
import com.example.bilabonnement.model.enums.Role;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository implements IGenericRepository{
  @Override
  public void create(Object p) {

  }

  @Override
  public List readAll() {
    return null;
  }

  @Override
  public Object read(int id) {
    return null;
  }

  @Override
  public void update(Object p) {

  }

  @Override
  public void delete(int id) {

  }
}
