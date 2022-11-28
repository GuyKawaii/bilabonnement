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
            // with or without predefined ID;
            PreparedStatement psts;
            if (damageEntry.getId() == null) {
                psts = conn.prepareStatement("INSERT INTO bilabonnement.damageentry (damageTitle, damageDescription, damagePrice, damageReportID) VALUES (?,?,?,?)");
                psts.setString(1, damageEntry.getDamageTitle());
                psts.setString(2, damageEntry.getDamageDescription());
                psts.setInt(3, damageEntry.getDamagePrice());
                psts.setInt(4, damageEntry.getDamageReportID());
            } else {
                psts = conn.prepareStatement("INSERT INTO bilabonnement.damageentry (damageEntryID, damageTitle, damageDescription, damagePrice, damageReportID) VALUES (?,?,?,?,?)");
                psts.setInt(1, damageEntry.getId());
                psts.setString(2, damageEntry.getDamageTitle());
                psts.setString(3, damageEntry.getDamageDescription());
                psts.setInt(4, damageEntry.getDamagePrice());
                psts.setInt(5, damageEntry.getDamageReportID());
            }
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
            ResultSet resultSet = pst.executeQuery();

            // list of entities
            while (resultSet.next()) {
                damageList.add(new DamageEntry(
                        resultSet.getInt("damageEntryID"),
                        resultSet.getString("damageTitle"),
                        resultSet.getString("damageDescription"),
                        resultSet.getInt("damagePrice"),
                        resultSet.getInt("damageReportID")));
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

            // entity parameters
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
            psts.setString(1, damageEntry.getDamageTitle());
            psts.setString(2, damageEntry.getDamageDescription());
            psts.setInt(3, damageEntry.getDamagePrice());
            psts.setInt(4, damageEntry.getId());
            ResultSet resultSet = psts.executeQuery();

            // specify parameters
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
        try {
            PreparedStatement psts = conn.prepareStatement("DELETE FROM bilabonnement.damageentry WHERE damageEntryID=?");
            psts.setInt(1, id);
            psts.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<DamageEntry> entriesByReport(int ids) {
        List<DamageEntry> damageList = new ArrayList<>();

        try {
            PreparedStatement pst = conn.prepareStatement("select * from bilabonnement.damageentry WHERE damageReportID = ?");
            pst.setInt(1, ids);
            ResultSet resultSet = pst.executeQuery();

            // list of entities
            while (resultSet.next()) {
                damageList.add(new DamageEntry(
                        resultSet.getInt("damageEntryID"),
                        resultSet.getString("damageTitle"),
                        resultSet.getString("damageDescription"),
                        resultSet.getInt("damagePrice"),
                        resultSet.getInt("damageReportID")));
            }

        } catch (SQLException e) {
        }

        return damageList;
    }
}
