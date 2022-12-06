package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.Employee;
import com.example.bilabonnement.model.enums.DB_CONNECTION;
import com.example.bilabonnement.model.enums.Role;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository implements IGenericRepository<Employee> {
    Connection conn;

    public EmployeeRepository(DB_CONNECTION db_connection) {
        conn = DatabaseConnectionManager.getConnection(DB_CONNECTION.RELEASE_DB);
    }
    @Override
    public void create(Employee employee) {
        try {
            // with or without predefined ID;
            PreparedStatement psts;
            if (employee.getEmployeeID() == null) {
                psts = conn.prepareStatement("INSERT INTO employee (email, name, password, role) VALUES (?,?,?,?)");
                psts.setString(1, employee.getEmail());
                psts.setString(2, employee.getName());
                psts.setString(3, employee.getPassword());
                psts.setString(4, employee.getRole().name());
            } else {
                psts = conn.prepareStatement("INSERT INTO employee (employeeID, email, name, password, role) VALUES (?,?,?,?,?)");
                psts.setInt(1, employee.getEmployeeID());
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
            PreparedStatement pst = conn.prepareStatement("select * from employee");
            ResultSet resultSet = pst.executeQuery();

            // list of entities
            while (resultSet.next()) {
                employeeList.add(new Employee(
                        resultSet.getInt("employeeID"),
                        resultSet.getString("email"),
                        resultSet.getString("name"),
                        resultSet.getString("password"),
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
            PreparedStatement psts = conn.prepareStatement("SELECT * FROM employee WHERE employeeID = ?");
            psts.setInt(1, id);
            ResultSet resultSet = psts.executeQuery();

            // entity parameters
            while (resultSet.next()) {
                employee = new Employee(
                        resultSet.getInt("employeeID"),
                        resultSet.getString("email"),
                        resultSet.getString("name"),
                        resultSet.getString("password"),
                        Role.valueOf(resultSet.getString("role")));
            }
            return employee;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public void update(Employee employee) {
        try {
            PreparedStatement psts = conn.prepareStatement("UPDATE employee SET email = ?, name = ?, password = ?, role = ? WHERE employeeID = ?");
            psts.setString(1, employee.getEmail());
            psts.setString(2, employee.getName());
            psts.setString(3, employee.getPassword());
            psts.setString(4, employee.getRole().toString());
            psts.setInt(5, employee.getEmployeeID());
            psts.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int employeeID) {
        try {
            PreparedStatement psts = conn.prepareStatement("DELETE FROM employee WHERE employeeID=?");
            psts.setInt(1, employeeID);
            psts.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Employee readByEmail(String email) {
        Employee employee = null;

        try {
            PreparedStatement psts = conn.prepareStatement("SELECT * FROM employee WHERE email = ?;");
            psts.setString(1, email);
            ResultSet resultSet = psts.executeQuery();

            // entity parameters
            while (resultSet.next()) {
                employee = new Employee(
                        resultSet.getInt("employeeID"),
                        resultSet.getString("email"),
                        resultSet.getString("name"),
                        resultSet.getString("password"),
                        Role.valueOf(resultSet.getString("role")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return employee;
    }

}

