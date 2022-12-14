package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.Customer;
import com.example.bilabonnement.model.enums.DB_CONNECTION;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository implements IGenericRepository<Customer> {
    Connection conn;

    public CustomerRepository(DB_CONNECTION db_connection) {
        conn = DatabaseConnectionManager.getConnection(db_connection);
    }
    /**
     * @author daniel(GuyKawaii)
     * @author Mikas(CodeClod)
     */
    @Override
    public void create(Customer customer) {
        try {
            // with or without predefined ID;
            PreparedStatement psts;
            if (customer.getCustomerID() == null) {
                psts = conn.prepareStatement(
                        "INSERT INTO customer (firstName, lastName, email, address, city, postalCode, mobile, cprNumber) VALUES (?,?,?,?,?,?,?,?)");
                psts.setString(1, customer.getFirstName());
                psts.setString(2, customer.getLastName());
                psts.setString(3, customer.getEmail());
                psts.setString(4, customer.getAddress());
                psts.setString(5, customer.getCity());
                psts.setInt(6, customer.getPostalCode());
                psts.setString(7, customer.getMobile());
                psts.setString(8, customer.getCprNumber());
            } else {
                psts = conn.prepareStatement(
                        "INSERT INTO customer (customerID, firstName, lastName, email, address, city, postalCode, mobile, cprNumber) VALUES (?,?,?,?,?,?,?,?,?)");
                psts.setInt(1, customer.getCustomerID());
                psts.setString(2, customer.getFirstName());
                psts.setString(3, customer.getLastName());
                psts.setString(4, customer.getEmail());
                psts.setString(5, customer.getAddress());
                psts.setString(6, customer.getCity());
                psts.setInt(7, customer.getPostalCode());
                psts.setString(8, customer.getMobile());
                psts.setString(9, customer.getCprNumber());
            }
            psts.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    /**
     * @author daniel(GuyKawaii)
     * @author Mikas(CodeClod)
     */
    @Override
    public List<Customer> readAll() {
        List<Customer> customerList = new ArrayList<>();

        try {
            PreparedStatement pst = conn.prepareStatement("select * from customer");
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
                        resultSet.getInt("postalCode"),
                        resultSet.getString("mobile"),
                        resultSet.getString("cprNumber")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerList;
    }
    /**
     * @author daniel(GuyKawaii)
     * @author Mikas(CodeClod)
     */
    @Override
    public Customer read(int id) {
        Customer customer = null;

        try {
            PreparedStatement psts = conn.prepareStatement("SELECT * FROM customer WHERE customerID = ?");
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
                        resultSet.getInt("postalCode"),
                        resultSet.getString("mobile"),
                        resultSet.getString("cprNumber"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return customer;
    }
    /**
     * @author daniel(GuyKawaii)
     * @author Mikas(CodeClod)
     */
    @Override
    public void update(Customer customer) {
        try {
            PreparedStatement psts = conn.prepareStatement(
                    "UPDATE customer SET firstName = ?, lastName = ?, email = ?, address = ?, city = ?, postalCode = ?, mobile = ?, cprNumber = ? WHERE customerID = ?");
            psts.setString(1, customer.getFirstName());
            psts.setString(2, customer.getLastName());
            psts.setString(3, customer.getEmail());
            psts.setString(4, customer.getAddress());
            psts.setString(5, customer.getCity());
            psts.setInt(6, customer.getPostalCode());
            psts.setString(7, customer.getMobile());
            psts.setString(8, customer.getCprNumber());
            psts.setInt(9, customer.getCustomerID());
            psts.executeUpdate();
            psts.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * @author daniel(GuyKawaii)
     * @author Mikas(CodeClod)
     */
    @Override
    public void delete(int customerID) {
        try {
            PreparedStatement psts = conn.prepareStatement("DELETE FROM customer WHERE customerID = ?");
            psts.setInt(1, customerID);
            psts.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
