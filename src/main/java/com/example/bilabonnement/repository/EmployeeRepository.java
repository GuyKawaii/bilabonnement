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

public class EmployeeRepository implements IGenericRepository<Employee> {
    Connection conn = DatabaseConnectionManager.getConnection();

    @Override
    public void create(Employee employee) {
        try {
            // with or without predefined ID;
            PreparedStatement psts;
            if (employee.getId() == null) {
                psts = conn.prepareStatement("INSERT INTO bilabonnement.employee (email, name, password, role) VALUES (?,?,?,?)");
                psts.setString(1, employee.getEmail());
                psts.setString(2, employee.getName());
                psts.setString(3, employee.getPassword());
                psts.setString(4, employee.getRole().name());
            } else {
                psts = conn.prepareStatement("INSERT INTO bilabonnement.employee (employeeID, email, name, password, role) VALUES (?,?,?,?,?)");
                psts.setString(1, employee.getEmail());
                psts.setString(2, employee.getEmail());
                psts.setString(3, employee.getName());
                psts.setString(4, employee.getPassword());
                psts.setString(5, employee.getRole().name());
            }
            psts.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Employee> readAll() {
        List<Employee> employeeList = new ArrayList<>();

        try {
            PreparedStatement pst = conn.prepareStatement("select * from bilabonnement.employee");
            ResultSet resultSet = pst.executeQuery();

            // list of entities
            while (resultSet.next()) {
                employeeList.add(new Employee(
                        resultSet.getInt("employeeID"),
                        resultSet.getString("email"),
                        resultSet.getString("name"),
                        Role.valueOf(resultSet.getString("role"))));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employeeList;
    }

    @Override
    public Employee read(int id) {
        Employee employee = null;

        try {
            PreparedStatement psts = conn.prepareStatement("SELECT * FROM bilabonnement.employee WHERE employeeID = ?");
            psts.setInt(1, id);
            ResultSet resultSet = psts.executeQuery();

            // entity parameters
            while (resultSet.next()) {
                employee = new Employee(
                        resultSet.getInt("employeeID"),
                        resultSet.getString("email"),
                        resultSet.getString("name"),
                        Role.valueOf(resultSet.getString("role")),
                        resultSet.getString("password"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return employee;
    }

    @Override
    public void update(Employee employee) {
        try {
            PreparedStatement psts = conn.prepareStatement("UPDATE bilabonnement.employee SET email = ?, name = ?, password = ?, role = ? WHERE employeeID = ?");
            psts.setString(1, employee.getEmail());
            psts.setString(2, employee.getName());
            psts.setString(3, employee.getPassword());
            psts.setString(4, employee.getRole().toString());
            psts.setInt(5, employee.getId());
            psts.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int employeeID) {
        try {
            PreparedStatement psts = conn.prepareStatement("DELETE FROM bilabonnement.employee WHERE employeeID=?");
            psts.setInt(1, employeeID);
            psts.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

