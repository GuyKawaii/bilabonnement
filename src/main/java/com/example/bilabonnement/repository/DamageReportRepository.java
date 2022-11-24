package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.DamageReport;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DamageReportRepository implements IGenericRepository<DamageReport>{
  Connection conn = DatabaseConnectionManager.getConnection();

  @Override
  public void create(DamageReport damageReport) {

      try {
        // with or without predefined listID;
        if (damageReport.getId() == null) {
          PreparedStatement psts = conn.prepareStatement("INSERT INTO bilabonnement.damagereport (damageReportID, leaseID, vehicleID) VALUES (?,?,?)");
          psts.setString(1, damageReport.getDatetime());
          psts.setString(2, damageReport.getLeaseID());
          psts.setString(3, damageReport.getVehicleID());
          psts.executeUpdate();

        } else {
          PreparedStatement psts = conn.prepareStatement("INSERT INTO bilabonnement.damagereport (damageReportID, damageTitle, leaseID, ) VALUES (?,?,?,?)");
          psts.setString(1, damageReport.getDatetime());
          psts.setString(2, damageReport.getLeaseID());
          psts.setString(3, damageReport.getVehicleID());
          psts.executeUpdate();
        }




    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<DamageReport> readAll() {
    List<DamageReport> damageReports = new ArrayList<>();

    try {
      PreparedStatement pst = conn.prepareStatement("select * from bilabonnement.employee");
      {
        ResultSet resultSet = pst.executeQuery();
        while (resultSet.next()) {
          damageReports.add(new DamageReport(
                  resultSet.getInt("id"),
                  resultSet.getDate("datetime"),
                  resultSet.getInt("leaseID"),
                  resultSet.getInt("vehicleID")));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return damageReports;
  }

  @Override
  public DamageReport read(int id) {
    DamageReport damageReport = null;

    try {
      PreparedStatement psts = conn.prepareStatement("SELECT * FROM bilabonnement.damagereport WHERE id = ?");
      psts.setInt(1, id);
      ResultSet resultSet = psts.executeQuery();

      while (resultSet.next()) {
        damageReport = new DamageReport(
                resultSet.getInt("id"),
                resultSet.getString("datetime"),
                resultSet.getString("leaseID"),
                resultSet.getString("vehicleID"));
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return damageReport;
  }

  @Override
  public void update(DamageReport damageReport) {
    try {
      PreparedStatement psts = conn.prepareStatement("UPDATE bilabonnement.damagereport SET datetime = ?, leaseID = ?, vehicleID = ? WHERE damageReportID = ?");
      psts.setInt(1, damageReport.getId());
      ResultSet resultSet = psts.executeQuery();

      while (resultSet.next()) {
        damageReport = new DamageReport(
                resultSet.getInt("damageReportID"),
                resultSet.getLocalDateTime("datetime"),
                resultSet.getInt("leaseID"),
                resultSet.getInt("vehicleID"));
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void delete(int id) {
    try {
      PreparedStatement psts = conn.prepareStatement("DELETE FROM bilabonnement.damagereport WHERE damageReportID=?");
      psts.setInt(1, id);
      psts.executeUpdate();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }
}
