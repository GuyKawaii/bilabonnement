package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.DamageReport;
import com.example.bilabonnement.model.enums.DB_CONNECTION;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DamageReportRepository implements IGenericRepository<DamageReport> {
    Connection conn;

    public DamageReportRepository(DB_CONNECTION db_connection) {
        conn = DatabaseConnectionManager.getConnection(db_connection);
    }

    /**
     * @author daniel(GuyKawaii)
     * @author Ian(DatJustino)
     */
    @Override
    public void create(DamageReport damageReport) {
        try {
            // with or without predefined ID;
            PreparedStatement psts;
            if (damageReport.getDamageReportID() == null) {
                psts = conn.prepareStatement("INSERT INTO damagereport (vehicleID,employeeID, timestamp) VALUES (?,?,?)");
                psts.setInt(1, damageReport.getVehicleID());
                psts.setInt(2, damageReport.getEmployeeID());
                psts.setTimestamp(3, damageReport.getTimestamp());

            } else {
                psts = conn.prepareStatement("INSERT INTO damagereport (damageReportID,vehicleID,employeeID, timestamp) VALUES (?,?,?,?)");
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

    /**
     * @author daniel(GuyKawaii)
     * @author Ian(DatJustino)
     */
    @Override
    public List<DamageReport> readAll() {
        List<DamageReport> damageReports = new ArrayList<>();

        try {
            PreparedStatement pst = conn.prepareStatement("select * from damagereport");
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

    /**
     * @author daniel(GuyKawaii)
     * @author Ian(DatJustino)
     */
    @Override
    public DamageReport read(int id) {
        DamageReport damageReport = null;

        try {
            PreparedStatement psts = conn.prepareStatement("SELECT * FROM damagereport WHERE damageReportID = ?");
            psts.setInt(1, id);
            ResultSet resultSet = psts.executeQuery();

            // entity parameters
            while (resultSet.next()) {
                damageReport = new DamageReport(
                        resultSet.getInt("damageReportID"),
                        resultSet.getInt("employeeID"),
                        resultSet.getInt("vehicleID"),
                        resultSet.getTimestamp("timestamp"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return damageReport;
    }

    /**
     * @author Ian(DatJustino)
     */
    @Override
    public void update(DamageReport damageReport) {
        try {
            PreparedStatement psts = conn.prepareStatement("UPDATE damagereport SET vehicleID = ?, employeeID = ?, timestamp = ? WHERE damageReportID = ?");
            psts.setInt(1, damageReport.getVehicleID());
            psts.setInt(2, damageReport.getEmployeeID());
            psts.setTimestamp(3, damageReport.getTimestamp());
            psts.setInt(4, damageReport.getDamageReportID());

            psts.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @author Ian(DatJustino)
     */
    @Override
    public void delete(int id) {
        try {
            PreparedStatement psts = conn.prepareStatement("DELETE FROM damagereport WHERE damageReportID = ?");
            psts.setInt(1, id);
            psts.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<DamageReport> readAllFromEmployee(int employeeID) {
        // Get all damageReports by (logged in) EmployeeID
        List<DamageReport> damageReports = new ArrayList<>();

        try {
            PreparedStatement psts = conn.prepareStatement("SELECT * FROM damagereport WHERE employeeID = ?");
            psts.setInt(1, employeeID);
            ResultSet resultSet = psts.executeQuery();

            // list of entities
            while (resultSet.next()) {
                damageReports.add(new DamageReport(
                        resultSet.getInt("damageReportID"),
                        resultSet.getInt("employeeID"),
                        resultSet.getInt("vehicleID"),
                        resultSet.getTimestamp("timestamp")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return damageReports;
    }
}

