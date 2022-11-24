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
            PreparedStatement psts = conn.prepareStatement("INSERT INTO bilabonnement.customer (customerID, firstName, lastName, email) VALUES (?,?,?,?)");
            psts.setInt(1, customer.getId());
            psts.setString(2, customer.getFirstName());
            psts.setString(3, customer.getLastName());
            psts.setString(4, customer.getEmail());
            psts.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Customer read(int id) {
        Customer customer = null;

        try {
            PreparedStatement psts = conn.prepareStatement("SELECT * FROM bilabonnement.customer WHERE customerID = ?");
            psts.setInt(1, id);
            ResultSet resultSet = psts.executeQuery();

            // add parameters
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
    public List<Customer> readAll() {
        List<Customer> customerList = new ArrayList<>();

        try {
            PreparedStatement pst = conn.prepareStatement("select * from bilabonnement.employee");
            {
                ResultSet resultSet = pst.executeQuery();
                while (resultSet.next()) {
                    customerList.add(new Customer(
                            resultSet.getInt("customerID"),
                            resultSet.getString("firstName"),
                            resultSet.getString("lastName"),
                            resultSet.getString("email")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerList;
    }

  @Override
  public Object read(int id) {
    return null;
  }

  @Override
  public void update(Object p) {

  }

    @Override
    public void delete(int customerID) {
        Customer customer = null;

        try {
            PreparedStatement psts = conn.prepareStatement("DELETE FROM bilabonnement.customer WHERE customerID = ?");
            psts.setInt(1, customerID);
            psts.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

  }
}
