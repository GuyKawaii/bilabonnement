package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.DamageReport;
import com.example.bilabonnement.utility.DatabaseConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DamageReportRepository implements IGenericRepository<DamageReport> {
    Connection conn = DatabaseConnectionManager.getConnection();

    @Override
    public void create(DamageReport damageReport) {
        try {
            // with or without predefined ID;
            PreparedStatement psts;
            if (damageReport.getDamageReportID() == null) {
                psts = conn.prepareStatement("INSERT INTO bilabonnement.damagereport (vehicleID,employeeID, timestamp) VALUES (?,?,?)");
                psts.setInt(1, damageReport.getVehicleID());
                psts.setInt(2, damageReport.getEmployeeID());
                psts.setTimestamp(3, damageReport.getTimestamp());

            } else {
                psts = conn.prepareStatement("INSERT INTO bilabonnement.damagereport (damageReportID,vehicleID,employeeID, timestamp) VALUES (?,?,?,?)");
                psts.setInt(1, damageReport.getDamageReportID());
                psts.setInt(2, damageReport.getVehicleID());
                psts.setInt(3, damageReport.getEmployeeID());
                psts.setTimestamp(4, damageReport.getTimestamp()); // todo some confusion about this and datetime format in sql and java
            }
            psts.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<DamageReport> readAll() {
        List<DamageReport> damageReports = new ArrayList<>();

        try {
            PreparedStatement pst = conn.prepareStatement("select * from bilabonnement.damagereport");
            ResultSet resultSet = pst.executeQuery();

            // list of entities
            while (resultSet.next()) {
                damageReports.add(new DamageReport(
                        resultSet.getInt("damageReportID"),
                        resultSet.getInt("vehicleID"),
                        resultSet.getInt("employeeID"),
                        resultSet.getTimestamp("timestamp")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return damageReports;
    }

    @Override
    public DamageReport read(int id) {
        DamageReport damageReport = null;

        try {
            PreparedStatement psts = conn.prepareStatement("SELECT * FROM bilabonnement.damagereport WHERE damageReportID = ?");
            psts.setInt(1, id);
            ResultSet resultSet = psts.executeQuery();

            // entity parameters
            while (resultSet.next()) {
                damageReport = new DamageReport(
                        resultSet.getInt("damageReportID"),
                        resultSet.getInt("vehicleID"),
                        resultSet.getInt("employeeID"),
                        resultSet.getTimestamp("timestamp"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return damageReport;
    }

    @Override
    public void update(DamageReport damageReport) {
        try {
            PreparedStatement psts = conn.prepareStatement("UPDATE bilabonnement.damagereport SET vehicleID = ?, damageReportID = ?, employeeID = ?, timestamp = ? WHERE damageReportID = ?");
            psts.setInt(1, damageReport.getVehicleID());
            psts.setInt(2, damageReport.getDamageReportID());
            psts.setInt(4, damageReport.getEmployeeID());
            psts.setTimestamp(3, damageReport.getTimestamp());

            psts.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement psts = conn.prepareStatement("DELETE FROM bilabonnement.damagereport WHERE damageReportID = ?");
            psts.setInt(1, id);
            psts.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
