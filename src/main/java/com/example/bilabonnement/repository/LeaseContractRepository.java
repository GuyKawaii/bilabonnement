package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.LeaseContract;
import com.example.bilabonnement.model.Optional;
import com.example.bilabonnement.model.enums.DB_CONNECTION;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class LeaseContractRepository implements IGenericRepository<LeaseContract> {

    Connection conn;

    public LeaseContractRepository(DB_CONNECTION db_connection) {
        conn = DatabaseConnectionManager.getConnection(db_connection);
    }

    @Override
    public void create(LeaseContract leaseContract) {
        int leaseID;

        // with or without predefined ID;
        try {
            PreparedStatement psts;
            if (leaseContract.getLeaseID() == null) {
                psts = conn.prepareStatement(
                        "INSERT INTO leasecontract (startDate, endDate, monthlyPrice, customerID, vehicleID, employeeID) VALUES (?,?,?,?,?,?)");
                psts.setDate(1, leaseContract.getStartDate());
                psts.setDate(2, leaseContract.getEndDate());
                psts.setDouble(3, leaseContract.getMonthlyPrice());
                psts.setInt(4, leaseContract.getCustomerID());
                psts.setInt(5, leaseContract.getVehicleID());
                psts.setInt(6, leaseContract.getEmployeeID());
                psts.executeUpdate();

                // leaseID
                PreparedStatement psts2 = conn.prepareStatement("SELECT MAX(leaseID) FROM leasecontract");
                ResultSet resultSet = psts2.executeQuery();
                resultSet.next();
                leaseID = resultSet.getInt(1);

            } else {
                psts = conn.prepareStatement(
                        "INSERT INTO leasecontract (leaseID, startDate, endDate, monthlyPrice, customerID, vehicleID, employeeID) VALUES (?,?,?,?,?,?,?)");
                psts.setInt(1, leaseContract.getLeaseID());
                psts.setDate(2, leaseContract.getStartDate());
                psts.setDate(3, leaseContract.getEndDate());
                psts.setDouble(4, leaseContract.getMonthlyPrice());
                psts.setInt(5, leaseContract.getCustomerID());
                psts.setInt(6, leaseContract.getVehicleID());
                psts.setInt(7, leaseContract.getEmployeeID());
                psts.executeUpdate();

                // leaseID
                leaseID = leaseContract.getLeaseID();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // update leaseOptionals table
        updateOptionals(leaseContract.getLeaseOptionals(), leaseID);
    }

    public int createAndReturnID(LeaseContract leaseContract) {
        // call base method
        create(leaseContract);

        // preset id
        if (leaseContract.getLeaseID() != null)
            return leaseContract.getLeaseID();

        // no preset id
        try {
            PreparedStatement psts = conn.prepareStatement("SELECT MAX(leaseID) FROM leasecontract");
            ResultSet resultSet = psts.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // TODO
    @Override
    public List<LeaseContract> readAll() {
        List<LeaseContract> contractList = new ArrayList<>();

        try {
            PreparedStatement pst = conn.prepareStatement("select * from leasecontract ORDER BY leaseID DESC");
            ResultSet resultSet = pst.executeQuery();

            // list of entities
            while (resultSet.next()) {
                contractList.add(new LeaseContract(
                        resultSet.getInt("leaseID"),
                        resultSet.getDate("startDate"),
                        resultSet.getDate("endDate"),
                        resultSet.getDouble("monthlyPrice"),
                        resultSet.getInt("customerID"),
                        resultSet.getInt("vehicleID"),
                        resultSet.getInt("employeeID"),
                        readLeaseOptionals(resultSet.getInt("leaseID"))
                ));
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
            PreparedStatement psts = conn.prepareStatement("SELECT * FROM leasecontract WHERE leaseID = ?");
            psts.setInt(1, id);
            ResultSet resultSet = psts.executeQuery();

            // entity parameters
            while (resultSet.next()) {
                leaseContract = new LeaseContract(
                        resultSet.getInt("leaseID"),
                        resultSet.getDate("startDate"),
                        resultSet.getDate("endDate"),
                        resultSet.getDouble("monthlyPrice"),
                        resultSet.getInt("customerID"),
                        resultSet.getInt("vehicleID"),
                        resultSet.getInt("employeeID"),
                        readLeaseOptionals(resultSet.getInt("leaseID"))
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return leaseContract;
    }

    @Override
    public void update(LeaseContract leaseContract) {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        java.sql.Date date2 = new java.sql.Date(Calendar.getInstance().getTime().getTime());

        try {
            PreparedStatement psts = conn.prepareStatement("UPDATE leasecontract SET startDate = ?, endDate = ?, monthlyPrice = ?, customerID = ?, vehicleID = ?, employeeID = ? WHERE leaseID = ?");
            psts.setDate(1, leaseContract.getStartDate());
            psts.setDate(2, leaseContract.getEndDate());
            psts.setDouble(3, leaseContract.getMonthlyPrice());
            psts.setInt(4, leaseContract.getCustomerID());
            psts.setInt(5, leaseContract.getVehicleID());
            psts.setInt(6, leaseContract.getEmployeeID());
            psts.setInt(7, leaseContract.getLeaseID());
            psts.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void delete(int id) {
        try {
            PreparedStatement pst = conn.prepareStatement("DELETE FROM leasecontract WHERE leaseID = ?");
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public double getCurrentIncome(Date date) {
        double income = 0;
        try {
            PreparedStatement pst = conn.prepareStatement("""
                    SELECT SUM(currentIncomeTable) as currentIncomeSum
                    FROM (SELECT monthlyPrice + IFNULL(SUM(pricePrMonth), 0) AS currentIncomeTable
                          FROM fullLeaseInfo
                          WHERE (startDate <= ?)
                            AND (? <= endDate)
                          GROUP BY leaseID) as currentIncomeTable
                    """);

            pst.setDate(1, date);
            pst.setDate(2, date);
            ResultSet resultSet = pst.executeQuery();

            while (resultSet.next()) {
                income += resultSet.getDouble("currentIncomeSum");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return income;
    }

    public double getCurrentIncome2(Date date) {
        double income = 0;
        try {
            PreparedStatement pst = conn.prepareStatement("SELECT l.monthlyPrice + SUM(o.pricePrMonth) as total\n" +
                    "FROM leaseContract l\n" +
                    "         LEFT JOIN leaseoptional lo on lo.leaseID = l.leaseID\n" +
                    "         JOIN optional o on lo.optionalID = o.optionalID\n" +
                    "WHERE l.startDate < ?\n" +
                    "  AND ? < l.endDate\n" +
                    "GROUP BY l.monthlyPrice;");

            pst.setDate(1, date);
            pst.setDate(2, date);
            ResultSet resultSet = pst.executeQuery();

            while (resultSet.next()) {
                income += resultSet.getDouble("total");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return income;
    }

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


    public void updateOptionals(List<Optional> optionals, int leaseID) {
        // remove previous optionals
        try {
            PreparedStatement pst = conn.prepareStatement("DELETE FROM leaseoptional WHERE leaseID = ?");
            pst.setInt(1, leaseID);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // add new
        for (Optional optional : optionals) {
            try {
                PreparedStatement pst = conn.prepareStatement("INSERT INTO leaseoptional (optionalID, leaseID) VALUES (?,?)");
                pst.setInt(1, optional.getOptionalID());
                pst.setInt(2, leaseID);
                pst.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<LeaseContract> readActiveLeaseContractsByVehicleID(int vehicleID, Date date) {
        List<LeaseContract> contractList = new ArrayList<>();

        try {
            PreparedStatement pst = conn.prepareStatement("""
                    SELECT l.*
                    FROM leasecontract l
                             JOIN car c on c.vehicleID = l.vehicleID
                    WHERE l.vehicleID = ?
                      AND startDate <= ?
                      AND              ? <= endDate
                    ORDER BY startDate DESC
                    """);
            pst.setInt(1, vehicleID);
            pst.setDate(2, date);
            pst.setDate(3, date);
            ResultSet resultSet = pst.executeQuery();

            // list of entities
            while (resultSet.next()) {
                contractList.add(new LeaseContract(
                        resultSet.getInt("leaseID"),
                        resultSet.getDate("startDate"),
                        resultSet.getDate("endDate"),
                        resultSet.getDouble("monthlyPrice"),
                        resultSet.getInt("customerID"),
                        resultSet.getInt("vehicleID"),
                        resultSet.getInt("employeeID"),
                        readLeaseOptionals(resultSet.getInt("leaseID"))
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contractList;
    }

    public List<LeaseContract> readPassedLeaseContractsByVehicleID(int vehicleID, Date date) {
        List<LeaseContract> contractList = new ArrayList<>();

        try {
            PreparedStatement pst = conn.prepareStatement("""
                    SELECT l.*
                    FROM leasecontract l
                             JOIN car c on c.vehicleID = l.vehicleID
                    WHERE l.vehicleID = ?
                      AND l.endDate < ?
                    ORDER BY startDate DESC
                    """);
            pst.setInt(1, vehicleID);
            pst.setDate(2, date);
            ResultSet resultSet = pst.executeQuery();

            // list of entities
            while (resultSet.next()) {
                contractList.add(new LeaseContract(
                        resultSet.getInt("leaseID"),
                        resultSet.getDate("startDate"),
                        resultSet.getDate("endDate"),
                        resultSet.getDouble("monthlyPrice"),
                        resultSet.getInt("customerID"),
                        resultSet.getInt("vehicleID"),
                        resultSet.getInt("employeeID"),
                        readLeaseOptionals(resultSet.getInt("leaseID"))
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contractList;
    }

    public List<LeaseContract> readUpcomingLeaseContractsByVehicleID(int vehicleID, Date date) {
        List<LeaseContract> contractList = new ArrayList<>();

        try {
            PreparedStatement pst = conn.prepareStatement("""
                    SELECT l.*
                    FROM leasecontract l
                             JOIN car c on c.vehicleID = l.vehicleID
                    WHERE l.vehicleID = ?
                      AND ? < l.startDate
                    ORDER BY startDate DESC
                    """);
            pst.setInt(1, vehicleID);
            pst.setDate(2, date);
            ResultSet resultSet = pst.executeQuery();

            // list of entities
            while (resultSet.next()) {
                contractList.add(new LeaseContract(
                        resultSet.getInt("leaseID"),
                        resultSet.getDate("startDate"),
                        resultSet.getDate("endDate"),
                        resultSet.getDouble("monthlyPrice"),
                        resultSet.getInt("customerID"),
                        resultSet.getInt("vehicleID"),
                        resultSet.getInt("employeeID"),
                        readLeaseOptionals(resultSet.getInt("leaseID"))
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contractList;
    }

    public double activeLeaseContractCountByDate(Date date) {
        double count = 0;
        try {
            PreparedStatement pst = conn.prepareStatement("SELECT COUNT(*) AS activeContractCount\n" +
                    "FROM fullLeaseInfo\n" +
                    "WHERE (startDate <= ?) AND\n" +
                    "    (? <= endDate);");

            pst.setDate(1, date);
            pst.setDate(2, date);
            ResultSet resultSet = pst.executeQuery();

            while (resultSet.next()) {
                count = resultSet.getDouble("activeContractCount");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    public boolean hasContractOverlapForPeriod(int vehicleID, Date startDate, Date endDate) {
        List<LeaseContract> contractList = new ArrayList<>();

        try {
            // get contracts that have an active overlap with the given period
            PreparedStatement pst = conn.prepareStatement("""
                    SELECT leasecontract.*
                    FROM leasecontract
                    WHERE vehicleID = ?
                      AND (
                            (startDate <= ? AND ? <= endDate
                          OR startDate <= ? AND ? <= endDate ))
                    """);

            pst.setInt(1, vehicleID);
            pst.setDate(2, startDate);
            pst.setDate(3, startDate);
            pst.setDate(4, endDate);
            pst.setDate(5, endDate);
            ResultSet resultSet = pst.executeQuery();

            // list of entities
            while (resultSet.next()) {
                contractList.add(new LeaseContract(
                        resultSet.getInt("leaseID"),
                        resultSet.getDate("startDate"),
                        resultSet.getDate("endDate"),
                        resultSet.getDouble("monthlyPrice"),
                        resultSet.getInt("customerID"),
                        resultSet.getInt("vehicleID"),
                        resultSet.getInt("employeeID"),
                        readLeaseOptionals(resultSet.getInt("vehicleID"))
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return (0 < contractList.size());
    }

}

