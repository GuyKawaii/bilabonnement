package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.Customer;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository implements IGenericRepository<Customer> {
    Connection conn = DatabaseConnectionManager.getConnection();

    @Override
    public void create(Customer customer) {
        try {
            // with or without predefined ID;
            PreparedStatement psts;
            if (customer.getId() == null) {
                psts = conn.prepareStatement("INSERT INTO bilabonnement.customer (firstName, lastName, email) VALUES (?,?,?)");
                psts.setString(1, customer.getFirstName());
                psts.setString(2, customer.getLastName());
                psts.setString(3, customer.getEmail());
            } else {
                psts = conn.prepareStatement("INSERT INTO bilabonnement.customer (customerID, firstName, lastName, email) VALUES (?,?,?,?)");
                psts.setInt(1, customer.getId());
                psts.setString(2, customer.getFirstName());
                psts.setString(3, customer.getLastName());
                psts.setString(4, customer.getEmail());
            }
            psts.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Customer> readAll() {
        List<Customer> customerList = new ArrayList<>();

        try {
            PreparedStatement pst = conn.prepareStatement("select * from bilabonnement.employee");
            ResultSet resultSet = pst.executeQuery();

            // list of entities
            while (resultSet.next()) {
                customerList.add(new Customer(
                        resultSet.getInt("customerID"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("email")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerList;
    }

    @Override
    public Customer read(int id) {
        Customer customer = null;

        try {
            PreparedStatement psts = conn.prepareStatement("SELECT * FROM bilabonnement.customer WHERE customerID = ?");
            psts.setInt(1, id);
            ResultSet resultSet = psts.executeQuery();

            // entity parameters
            while (resultSet.next()) {
                customer = new Customer(
                        resultSet.getInt("customerID"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("email")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return customer;
    }

    @Override
    public void update(Customer customer) {
        try {
            PreparedStatement psts = conn.prepareStatement("UPDATE bilabonnement.customer SET firstName = ?, lastName = ?, email = ? WHERE customerID = ?");
            psts.setString(1, customer.getFirstName());
            psts.setString(2, customer.getLastName());
            psts.setString(3, customer.getEmail());
            psts.setInt(4, customer.getId());
            psts.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int customerID) {
        try {
            PreparedStatement psts = conn.prepareStatement("DELETE FROM bilabonnement.customer WHERE customerID = ?");
            psts.setInt(1, customerID);
            psts.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
