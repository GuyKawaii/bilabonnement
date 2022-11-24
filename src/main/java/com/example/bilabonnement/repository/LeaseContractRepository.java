package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.Employee;
import com.example.bilabonnement.model.LeaseContract;
import com.example.bilabonnement.model.enums.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class LeaseContractRepository implements IGenericRepository<LeaseContract>{

    private Connection conn;


    @Override
    public void create(LeaseContract p) {
        try {
        PreparedStatement psts = conn.prepareStatement("INSERT INTO bilabonnement.employee (email, name, password, role) VALUES (?,?,?,?)");
        psts.setString(1, employee.getEmail());
        psts.setString(2, employee.getName());
        psts.setString(3, employee.getPassword());
        psts.setString(4, employee.getRole().name());
        psts.executeUpdate();

    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

    }

    // TODO
    @Override
    public LeaseContract read(int id) {
        LeaseContract leaseContract = null;

        try {
            PreparedStatement psts = conn.prepareStatement("SELECT * FROM bilabonnement.employee WHERE employeeID = ?");
            psts.setInt(1, id);
            ResultSet resultSet = psts.executeQuery();

            // add parameters
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

        return leaseContract;
    }

    @Override
    public List<LeaseContract> readAll() {
        List<LeaseContract> contractList = new ArrayList<>();

        try {
            PreparedStatement pst = conn.prepareStatement("select * from bilabonnement.leasecontract");
            {
                ResultSet resultSet = pst.executeQuery();
                while (resultSet.next()) {
                    contractList.add(new Employee(
                            resultSet.getInt("employeeID"),
                            resultSet.getString("email"),
                            resultSet.getString("name"),
                            Role.valueOf(resultSet.getString("role"))));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contractList;
    }



    @Override
    public void update(LeaseContract p) {

        LeaseContract leaseContract;

        try {
            PreparedStatement psts = conn.prepareStatement("UPDATE bilabonnement.leasecontract SET startDate = ?, endDate = ?, monthlyPrice = ?, customerID = ?, vehicleID = ? WHERE leaseID = ?");
            psts.setDate(1, p.getStartDate());
            psts.setDate(2, p.getEndDate());
            psts.setDouble(3, p.getMonthlyPrice());
            psts.setInt(4, p.getCustomerID());
            psts.setInt(5, p.getVehicleID());
            psts.setInt(6, p.getLeaseID());
            ResultSet resultSet = psts.executeQuery();
            // add parameters (???)
            while (resultSet.next()) {
                 leaseContract = new LeaseContract(resultSet.getInt("leaseID"),
                        resultSet.getDate("startDate"),
                        resultSet.getDate("endDate"),
                        resultSet.getDouble("monthlyPrice"),
                        resultSet.getInt("customerID"),
                        resultSet.getInt("vehicleID")
                 );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public void delete(int id) {


        try {
            PreparedStatement pst = conn.prepareStatement("DELETE FROM bilabonnement.leasecontract WHERE leaseID = ? ");
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
