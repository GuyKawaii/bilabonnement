package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.optional;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OptionalRepository implements IGenericRepository<optional> {
    Connection conn = DatabaseConnectionManager.getConnection();

    @Override
    public void create(optional optional) {
        try {
            // with or without predefined ID;
            PreparedStatement psts;
            if (optional.getOptionalID() == null) {
                psts = conn.prepareStatement("INSERT INTO bilabonnement.optional (name, pricePrMonth) VALUES (?,?)");
                psts.setString(1, optional.getName());
                psts.setDouble(2, optional.getPricePrMonth());

            } else {
                psts = conn.prepareStatement("INSERT INTO bilabonnement.optional (optionalID, name, pricePrMonth) VALUES (?,?,?)");
                psts.setInt(1, optional.getOptionalID());
                psts.setString(2, optional.getName());
                psts.setDouble(3, optional.getPricePrMonth());

            }
            psts.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<optional> readAll() {
        List<optional> optionalList = new ArrayList<>();

        try {
            PreparedStatement pst = conn.prepareStatement("select * from bilabonnement.optional");
            ResultSet resultSet = pst.executeQuery();

            // list of entities
            while (resultSet.next()) {
                optionalList.add(new optional(
                        resultSet.getInt("optionalID"),
                        resultSet.getString("name"),
                        resultSet.getDouble("pricePrMonth")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return optionalList;
    }

    @Override
    public optional read(int id) {
        optional optional = null;

        try {
            PreparedStatement pst = conn.prepareStatement("select * from bilabonnement.optional WHERE optionalID = ?");
            pst.setInt(1, id);
            ResultSet resultSet = pst.executeQuery();

            // list of entities
            while (resultSet.next()) {
                optional = new optional(
                        resultSet.getInt("optionalID"),
                        resultSet.getString("name"),
                        resultSet.getDouble("pricePrMonth"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return optional;
    }

    @Override
    public void update(optional optional) {
        try {
            PreparedStatement psts = conn.prepareStatement("UPDATE bilabonnement.optional SET name = ?, pricePrMonth = ? WHERE optionalID = ?");
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
            PreparedStatement psts = conn.prepareStatement("DELETE FROM bilabonnement.optional WHERE optionalID = ?");
            psts.setInt(1, id);
            psts.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
