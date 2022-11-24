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

public class LeaseContractRepository implements IGenericRepository<LeaseContract> {

    private Connection conn;

    @Override
    public void create(LeaseContract leaseContract) {
        try {
            PreparedStatement psts = conn.prepareStatement("INSERT INTO bilabonnement.leasecontract (leaseID, startDate, endDate, monthlyPrice, customerID, vehicleID) VALUES (?,?,?,?,?,?)");
            psts.setInt(1, leaseContract.getLeaseID());
            psts.setDate(2, leaseContract.getStartDate());
            psts.setDate(3, leaseContract.getEndDate());
            psts.setDouble(4, leaseContract.getMonthlyPrice());
            psts.setInt(5, leaseContract.getCustomerID());
            psts.setInt(6, leaseContract.getVehicleID());
            psts.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    // TODO
    @Override
    public List<LeaseContract> readAll() {
        List<LeaseContract> contractList = new ArrayList<>();

        try {
            PreparedStatement pst = conn.prepareStatement("select * from bilabonnement.leasecontract");
            ResultSet resultSet = pst.executeQuery();

            // read list of entities
            while (resultSet.next()) {
                contractList.add(new LeaseContract(
                        resultSet.getInt("leaseID"),
                        resultSet.getDate("startDate"),
                        resultSet.getDate("endDate"),
                        resultSet.getDouble("monthlyPrice"),
                        resultSet.getInt("customerID"),
                        resultSet.getInt("vehicleID")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contractList;
    }

    @Override
    public LeaseContract read(int id) {
        LeaseContract leaseContract = null;

        try {
            PreparedStatement psts = conn.prepareStatement("SELECT * FROM bilabonnement.leasecontract WHERE leaseID = ?");
            psts.setInt(1, id);
            ResultSet resultSet = psts.executeQuery();

            // read entity parameters
            while (resultSet.next()) {
                leaseContract = new LeaseContract(
                        resultSet.getInt("leaseID"),
                        resultSet.getDate("startDate"),
                        resultSet.getDate("endDate"),
                        resultSet.getDouble("monthlyPrice"),
                        resultSet.getInt("customerID"),
                        resultSet.getInt("vehicleID"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return leaseContract;
    }

    @Override
    public void update(LeaseContract leaseContract) {
        try {
            PreparedStatement psts = conn.prepareStatement("UPDATE bilabonnement.leasecontract SET startDate = ?, endDate = ?, monthlyPrice = ?, customerID = ?, vehicleID = ? WHERE leaseID = ?");
            psts.setDate(1, leaseContract.getStartDate());
            psts.setDate(2, leaseContract.getEndDate());
            psts.setDouble(3, leaseContract.getMonthlyPrice());
            psts.setInt(4, leaseContract.getCustomerID());
            psts.setInt(5, leaseContract.getVehicleID());
            psts.setInt(6, leaseContract.getLeaseID());
            psts.executeUpdate();

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
