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
            if (customer.getCustomerID() == null) {
                psts = conn.prepareStatement(
                        "INSERT INTO bilabonnement.customer (firstName, lastName, email, address, city, zipCode, mobile, cprNumber) VALUES (?,?,?,?,?,?,?,?)");
                psts.setString(1, customer.getFirstName());
                psts.setString(2, customer.getLastName());
                psts.setString(3, customer.getEmail());
                psts.setString(4, customer.getAddress());
                psts.setString(5, customer.getCity());
                psts.setInt(6, customer.getZipCode());
                psts.setString(7, customer.getMobile());
                psts.setString(8,customer.getCprNumber());
            } else {
                psts = conn.prepareStatement(
                        "INSERT INTO bilabonnement.customer (customerID, firstName, lastName, email, address, city, zipCode, mobile, cprNumber) VALUES (?,?,?,?,?,?,?,?,?)");
                psts.setInt(1, customer.getCustomerID());
                psts.setString(2, customer.getFirstName());
                psts.setString(3, customer.getLastName());
                psts.setString(4, customer.getEmail());
                psts.setString(5, customer.getAddress());
                psts.setString(6, customer.getCity());
                psts.setInt(7, customer.getZipCode());
                psts.setString(8, customer.getMobile());
                psts.setString(9,customer.getCprNumber());
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
                        resultSet.getString("email"),
                        resultSet.getString("address"),
                        resultSet.getString("city"),
                        resultSet.getInt("zipCode"),
                        resultSet.getString("mobile"),
                        resultSet.getString("cprNumber")));
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
                        resultSet.getString("email"),
                        resultSet.getString("address"),
                        resultSet.getString("city"),
                        resultSet.getInt("zipCode"),
                        resultSet.getString("mobile"),
                        resultSet.getString("cprNumber"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return customer;
    }

    @Override
    public void update(Customer customer) {
        try {
            PreparedStatement psts = conn.prepareStatement(
                    "UPDATE bilabonnement.customer SET firstName = ?, lastName = ?, email = ?, address = ?, city = ?, zipCode = ?, mobile = ?, cprNumber = ?, WHERE customerID = ?");
            psts.setString(1, customer.getFirstName());
            psts.setString(2, customer.getLastName());
            psts.setString(3, customer.getEmail());
            psts.setString(4, customer.getAddress());
            psts.setString(5, customer.getCity());
            psts.setInt(6, customer.getZipCode());
            psts.setString(7, customer.getMobile());
            psts.setString(8,customer.getCprNumber());
            psts.setInt(9, customer.getCustomerID());
            psts.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int customerID) {

        // TODO is it possible to make a delete on a table-row that has a foreign key?
        try {
            PreparedStatement psts = conn.prepareStatement("DELETE FROM bilabonnement.customer WHERE customerID = ?");
            psts.setInt(1, customerID);
            psts.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
