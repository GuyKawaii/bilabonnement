package com.example.bilabonnement.repository;
/**
 * @author daniel(GuyKawaii)
 * @author Mikas(CodeClod)
 */
import com.example.bilabonnement.model.DamageEntry;
import com.example.bilabonnement.model.Employee;
import com.example.bilabonnement.model.enums.DB_CONNECTION;
import com.example.bilabonnement.model.enums.Role;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DamageEntryRepository implements IGenericRepository<DamageEntry> {
    Connection conn;

    public DamageEntryRepository(DB_CONNECTION db_connection) {
        conn = DatabaseConnectionManager.getConnection(db_connection);
    }

    /**
     * @author daniel(GuyKawaii)
     */
    @Override
    public void create(DamageEntry damageEntry) {
        try {
            // with or without predefined ID;
            PreparedStatement psts;
            if (damageEntry.getDamageEntryID() == null) {
                psts = conn.prepareStatement("INSERT INTO damageentry (damageTitle, damageDescription, damagePrice, damageReportID) VALUES (?,?,?,?)");
                psts.setString(1, damageEntry.getDamageTitle());
                psts.setString(2, damageEntry.getDamageDescription());
                psts.setDouble(3, damageEntry.getDamagePrice());
                psts.setInt(4, damageEntry.getDamageReportID());
            } else {
                psts = conn.prepareStatement("INSERT INTO damageentry (damageEntryID, damageTitle, damageDescription, damagePrice, damageReportID) VALUES (?,?,?,?,?)");
                psts.setInt(1, damageEntry.getDamageEntryID());
                psts.setString(2, damageEntry.getDamageTitle());
                psts.setString(3, damageEntry.getDamageDescription());
                psts.setDouble(4, damageEntry.getDamagePrice());
                psts.setInt(5, damageEntry.getDamageReportID());
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
    public List<DamageEntry> readAll() {
        List<DamageEntry> damageList = new ArrayList<>();

        try {
            PreparedStatement pst = conn.prepareStatement("select * from damageentry");
            ResultSet resultSet = pst.executeQuery();

            // list of entities
            while (resultSet.next()) {
                damageList.add(new DamageEntry(
                        resultSet.getInt("damageEntryID"),
                        resultSet.getString("damageTitle"),
                        resultSet.getString("damageDescription"),
                        resultSet.getDouble("damagePrice"),
                        resultSet.getInt("damageReportID")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return damageList;
    }

    /**
     * @author daniel(GuyKawaii)
     */
    @Override
    public DamageEntry read(int id) {
        DamageEntry damageEntry = null;

        try {
            PreparedStatement psts = conn.prepareStatement("SELECT * FROM damageentry WHERE damageReportID = ?");
            psts.setInt(1, id);
            ResultSet resultSet = psts.executeQuery();

            // entity parameters
            while (resultSet.next()) {
                damageEntry = new DamageEntry(
                        resultSet.getInt("damageEntryID"),
                        resultSet.getString("damageTitle"),
                        resultSet.getString("damageDescription"),
                        resultSet.getDouble("damagePrice"),
                        resultSet.getInt("damageReportID"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return damageEntry;
    }

    /**
     * @author daniel(GuyKawaii)
     */
    @Override
    public void update(DamageEntry damageEntry) {
        try {
            PreparedStatement psts = conn.prepareStatement("UPDATE damageentry SET damageTitle = ?, damageDescription = ?, damagePrice = ? WHERE damageEntryID = ?");
            psts.setString(1, damageEntry.getDamageTitle());
            psts.setString(2, damageEntry.getDamageDescription());
            psts.setDouble(3, damageEntry.getDamagePrice());
            psts.setInt(4, damageEntry.getDamageEntryID());
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
    public void delete(int id) {
        try {
            PreparedStatement psts = conn.prepareStatement("DELETE FROM damageentry WHERE damageEntryID=?");
            psts.setInt(1, id);
            psts.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @author daniel(GuyKawaii)
     * @author Ian(DatJustino)
     */
    public List<DamageEntry> entriesByReport(int ids) {
        List<DamageEntry> damageList = new ArrayList<>();

        try {
            PreparedStatement pst = conn.prepareStatement("select * from damageentry WHERE damageReportID = ?");
            pst.setInt(1, ids);
            ResultSet resultSet = pst.executeQuery();

            // list of entities
            while (resultSet.next()) {
                damageList.add(new DamageEntry(
                        resultSet.getInt("damageEntryID"),
                        resultSet.getString("damageTitle"),
                        resultSet.getString("damageDescription"),
                        resultSet.getDouble("damagePrice"),
                        resultSet.getInt("damageReportID")));
            }

        } catch (SQLException e) {
        }

        return damageList;
    }
}
