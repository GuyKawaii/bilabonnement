package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.DamageEntry;
import com.example.bilabonnement.model.Employee;
import com.example.bilabonnement.model.enums.Role;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DamageEntryRepository implements IGenericRepository<DamageEntry> {
    Connection conn = DatabaseConnectionManager.getConnection();

    @Override
    public void create(DamageEntry damageEntry) {

        try {
            PreparedStatement psts = conn.prepareStatement("INSERT INTO bilabonnement.damageentry (damageTitle, damageDescription, damagePrice) VALUES (?,?,?)");
            psts.setString(1, damageEntry.getDamageTitle());
            psts.setString(2, damageEntry.getDamageDescription());
            psts.setInt(3, damageEntry.getDamagePrice());
            psts.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<DamageEntry> readAll() {
        List<DamageEntry> damageList = new ArrayList<>();

        try {
            PreparedStatement pst = conn.prepareStatement("select * from bilabonnement.damageentry");
            {
                ResultSet resultSet = pst.executeQuery();
                while (resultSet.next()) {
                    damageList.add(new DamageEntry(
                            resultSet.getInt("damageEntryID"),
                            resultSet.getString("damageTitle"),
                            resultSet.getString("damageDescription"),
                            resultSet.getInt("damagePrice"),
                            resultSet.getInt("damageReportID")));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return damageList;
    }

    @Override
    public DamageEntry read(int id) {
        DamageEntry damageEntry = null;

        try {
            PreparedStatement psts = conn.prepareStatement("SELECT * FROM bilabonnement.damageentry WHERE damageReportID = ?");
            psts.setInt(1, id);
            ResultSet resultSet = psts.executeQuery();

            // add parameters
            while (resultSet.next()) {
                damageEntry = new DamageEntry(
                        resultSet.getInt("damageEntryID"),
                        resultSet.getString("damageTitle"),
                        resultSet.getString("damageDescription"),
                        resultSet.getInt("damagePrice"),
                        resultSet.getInt("damageReportID"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return damageEntry;
    }

    @Override
    public void update(DamageEntry damageEntry) {
        try {
            PreparedStatement psts = conn.prepareStatement("UPDATE bilabonnement.damageentry SET damageTitle = ?, damageDescription = ?, damagePrice = ? WHERE damageEntryID = ?");
            psts.setInt(1, damageEntry.getId());
            ResultSet resultSet = psts.executeQuery();

            // add parameters
            while (resultSet.next()) {
                damageEntry = new DamageEntry(
                        resultSet.getString("damageTitle"),
                        resultSet.getString("damageDescription"),
                        resultSet.getInt("damagePrice"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

  }

    @Override
    public void delete(int id) {
        Employee employee = null;
        employeeID = employee.getId();
        try {
            PreparedStatement psts = conn.prepareStatement("DELETE FROM bilabonnement.employee WHERE employeeID=?");
            psts.setInt(1, employeeID);
            psts.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

  }
}
