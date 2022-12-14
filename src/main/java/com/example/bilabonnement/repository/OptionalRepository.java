package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.enums.DB_CONNECTION;
import com.example.bilabonnement.model.Optional;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OptionalRepository implements IGenericRepository<Optional> {
    Connection conn;

    public OptionalRepository(DB_CONNECTION db_connection) {
        conn = DatabaseConnectionManager.getConnection(db_connection);
    }

    /**
     * @author daniel(GuyKawaii)
     */
    @Override
    public void create(Optional optional) {
        try {
            // with or without predefined ID;
            PreparedStatement psts;
            if (optional.getOptionalID() == null) {
                psts = conn.prepareStatement("INSERT INTO optional(name, pricePrMonth) VALUES (?,?)");
                psts.setString(1, optional.getName());
                psts.setDouble(2, optional.getPricePrMonth());

            } else {
                psts = conn.prepareStatement("INSERT INTO optional(optionalID, name, pricePrMonth) VALUES (?,?,?)");
                psts.setInt(1, optional.getOptionalID());
                psts.setString(2, optional.getName());
                psts.setDouble(3, optional.getPricePrMonth());

            }
            psts.executeUpdate();
            psts.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @author Veronica(Rhod1um)
     */
    @Override
    public List<Optional> readAll() {
        List<Optional> optionalList = new ArrayList<>();

        try {
            PreparedStatement pst = conn.prepareStatement("select * from optional");
            ResultSet resultSet = pst.executeQuery();

            // list of entities
            while (resultSet.next()) {
                optionalList.add(new Optional(resultSet.getInt("optionalID"), resultSet.getString("name"), resultSet.getDouble("pricePrMonth")));
            }
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return optionalList;
    }

    /**
     * @author Veronica(Rhod1um)
     */
    @Override
    public Optional read(int id) {
        Optional optional = null;

        try {
            PreparedStatement pst = conn.prepareStatement("select * from optional WHERE optionalID = ?");
            pst.setInt(1, id);
            ResultSet resultSet = pst.executeQuery();

            // list of entities
            while (resultSet.next()) {
                optional = new Optional(resultSet.getInt("optionalID"), resultSet.getString("name"), resultSet.getDouble("pricePrMonth"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return optional;
    }

    /**
     * @author Veronica(Rhod1um)
     */
    @Override
    public void update(Optional optional) {
        try {
            PreparedStatement psts = conn.prepareStatement("UPDATE optional SET name = ?, pricePrMonth = ? WHERE optionalID = ?");
            psts.setString(1, optional.getName());
            psts.setDouble(2, optional.getPricePrMonth());
            psts.setInt(3, optional.getOptionalID());
            psts.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement psts = conn.prepareStatement("DELETE FROM optional WHERE optionalID = ?");
            psts.setInt(1, id);
            psts.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // extra

    /**
     * @author daniel(GuyKawaii)
     */
    public List<Optional> readLeaseOptionals(int leaseID) {
        List<Optional> optionalList = new ArrayList<>();

        try {
            PreparedStatement pst = conn.prepareStatement("""
                    SELECT o.*
                    FROM optional o
                             JOIN leaseoptional l on o.optionalID = l.optionalID
                    WHERE l.leaseID = ?
                    """);
            pst.setInt(1, leaseID);
            ResultSet resultSet = pst.executeQuery();

            // list of entities
            while (resultSet.next()) {
                optionalList.add(new Optional(
                        resultSet.getInt("optionalID"),
                        resultSet.getString("name"),
                        resultSet.getDouble("pricePrMonth")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optionalList;
    }

    /**
     * @author daniel(GuyKawaii)
     */
    public List<Optional> readNonLeaseOptionals(int leaseID) {
        List<Optional> optionalList = new ArrayList<>();

        try {
            PreparedStatement pst = conn.prepareStatement("""
                    SELECT remaining.*
                    FROM optional remaining
                    WHERE remaining.optionalID NOT IN (SELECT leaseOptional.optionalID
                                                      FROM optional leaseOptional
                                                               JOIN leaseoptional l on leaseOptional.optionalID = l.optionalID
                                                      WHERE l.leaseID = ?)
                    """);
            pst.setInt(1, leaseID);
            ResultSet resultSet = pst.executeQuery();

            // list of entities
            while (resultSet.next()) {
                optionalList.add(new Optional(
                        resultSet.getInt("optionalID"),
                        resultSet.getString("name"),
                        resultSet.getDouble("pricePrMonth")));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optionalList;
    }

    /**
     * @author daniel(GuyKawaii)
     */
    public List<Double[]> readLeaseOptionalAmounts() {
        List<Double[]> optionalAmounts = new ArrayList<>();

        try {
            PreparedStatement pst = conn.prepareStatement("""
                    SELECT COUNT(lo.leaseID) as count, SUM(o.pricePrMonth) as sum
                    FROM leasecontract l
                             LEFT JOIN leaseoptional lo on l.leaseID = lo.leaseID
                             LEFT JOIN optional o on o.optionalID = lo.optionalID
                    GROUP BY l.leaseID
                    ORDER BY l.leaseID DESC
                    """);
            ResultSet resultSet = pst.executeQuery();

            // list of entities
            while (resultSet.next()) {
                optionalAmounts.add(new Double[]{
                        resultSet.getDouble("count"),
                        resultSet.getDouble("sum")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optionalAmounts;
    }
}
