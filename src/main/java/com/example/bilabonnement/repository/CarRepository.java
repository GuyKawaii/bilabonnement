package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.model.enums.*;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarRepository implements IGenericRepository<Car> {
    Connection conn;

    public CarRepository(DB_CONNECTION db_connection) {
        conn = DatabaseConnectionManager.getConnection(db_connection);
    }

    @Override
    public void create(Car car) {
        try {
            PreparedStatement psts;
            if (car.getVehicleID() == null) {
                psts = conn.prepareStatement("INSERT INTO car (chassisNumber, steelPrice, brand, model, equipmentLevel, registrationFee, co2emission, state) VALUES (?,?,?,?,?,?,?,?)");
                psts.setString(1, car.getChassisNumber());
                psts.setDouble(2, car.getSteelPrice());
                psts.setString(3, car.getBrand());
                psts.setString(4, car.getModel());
                psts.setString(5, car.getEquipmentLevel().toString());
                psts.setDouble(6, car.getRegistrationFee());
                psts.setDouble(7, car.getCo2emission());
                psts.setString(8, car.getState().toString());
            } else {
                psts = conn.prepareStatement("INSERT INTO car (vehicleID, chassisNumber, steelPrice, brand, model, equipmentLevel, registrationFee, co2emission, state) VALUES (?,?,?,?,?,?,?,?,?)");
                psts.setInt(1, car.getVehicleID());
                psts.setString(2, car.getChassisNumber());
                psts.setDouble(3, car.getSteelPrice());
                psts.setString(4, car.getBrand());
                psts.setString(5, car.getModel());
                psts.setString(6, car.getEquipmentLevel().toString());
                psts.setDouble(7, car.getRegistrationFee());
                psts.setDouble(8, car.getCo2emission());
                psts.setString(9, car.getState().toString());
            }
            psts.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Car> readAll() {
        List<Car> carList = new ArrayList<>();

        try {
            PreparedStatement pst = conn.prepareStatement("select * from car");
            ResultSet resultSet = pst.executeQuery();

            // list of entities
            while (resultSet.next()) {
                carList.add(new Car(
                        resultSet.getInt("vehicleID"),
                        resultSet.getString("chassisNumber"),
                        resultSet.getDouble("steelPrice"),
                        resultSet.getString("brand"),
                        resultSet.getString("model"),
                        EquipmentLevel.valueOf(resultSet.getString("equipmentLevel")),
                        resultSet.getDouble("registrationFee"),
                        resultSet.getDouble("co2emission"),
                        State.valueOf(resultSet.getString("state"))));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return carList;
    }

    @Override
    public Car read(int id) {
        Car car = null;

        try {
            PreparedStatement psts = conn.prepareStatement("SELECT * FROM car WHERE vehicleID = ?");
            psts.setInt(1, id);
            ResultSet resultSet = psts.executeQuery();


            // entity parameters
            while (resultSet.next()) {
                car = new Car(
                        resultSet.getInt("vehicleID"),
                        resultSet.getString("chassisNumber"),
                        resultSet.getDouble("steelPrice"),
                        resultSet.getString("brand"),
                        resultSet.getString("model"),
                        EquipmentLevel.valueOf(resultSet.getString("equipmentLevel")),
                        resultSet.getDouble("registrationFee"),
                        resultSet.getDouble("co2emission"),
                        State.valueOf(resultSet.getString("state")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return car;
    }

    @Override
    public void update(Car car) {
        try {
            PreparedStatement psts = conn.prepareStatement("UPDATE car SET chassisNumber = ? , steelPrice = ? , brand = ? , model = ? , equipmentLevel = ? , registrationFee = ? , co2emission = ?, state = ? WHERE vehicleID = ?");
            psts.setString(1, car.getChassisNumber());
            psts.setDouble(2, car.getSteelPrice());
            psts.setString(3, car.getBrand());
            psts.setString(4, car.getModel());
            psts.setString(5, car.getEquipmentLevel().toString());
            psts.setDouble(6, car.getRegistrationFee());
            psts.setDouble(7, car.getCo2emission());
            psts.setString(8, car.getState().toString());
            psts.setInt(9, car.getVehicleID());
            psts.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement psts = conn.prepareStatement("DELETE FROM car WHERE vehicleID = ?");
            psts.setInt(1, id);
            psts.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // extra for this repository

    public List<Car> readAllLeasedOnDate(Date date) {
        List<Car> carList = new ArrayList<>();

        try {
            PreparedStatement pst = conn.prepareStatement("""
                    SELECT c.*
                    FROM car c
                             JOIN leasecontract l on c.vehicleID = l.vehicleID
                    WHERE startDate <= ?
                      AND              ? <= endDate
                    GROUP BY c.vehicleID
                    ORDER BY c.vehicleID
                    """);
            pst.setDate(1, date);
            pst.setDate(2, date);
            ResultSet resultSet = pst.executeQuery();

            // list of entities
            while (resultSet.next()) {
                carList.add(new Car(
                        resultSet.getInt("vehicleID"),
                        resultSet.getString("chassisNumber"),
                        resultSet.getDouble("steelPrice"),
                        resultSet.getString("brand"),
                        resultSet.getString("model"),
                        EquipmentLevel.valueOf(resultSet.getString("equipmentLevel")),
                        resultSet.getDouble("registrationFee"),
                        resultSet.getDouble("co2emission"),
                        State.valueOf(resultSet.getString("state"))));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return carList;
    }

    public List<Car> readAllUnleasedOnDate(Date date) {
        List<Car> carList = new ArrayList<>();

        try {
            PreparedStatement pst = conn.prepareStatement("""
                    SELECT unleased.*
                    FROM car unleased
                    WHERE unleased.vehicleID NOT IN (SELECT leased.vehicleID
                                                     FROM car leased
                                                              join leasecontract l on leased.vehicleID = l.vehicleID
                                                     WHERE startDate <= ?
                                                       AND              ? <= endDate)
                    """);
            pst.setDate(1, date);
            pst.setDate(2, date);
            ResultSet resultSet = pst.executeQuery();

            // list of entities
            while (resultSet.next()) {
                carList.add(new Car(
                        resultSet.getInt("vehicleID"),
                        resultSet.getString("chassisNumber"),
                        resultSet.getDouble("steelPrice"),
                        resultSet.getString("brand"),
                        resultSet.getString("model"),
                        EquipmentLevel.valueOf(resultSet.getString("equipmentLevel")),
                        resultSet.getDouble("registrationFee"),
                        resultSet.getDouble("co2emission"),
                        State.valueOf(resultSet.getString("state"))));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return carList;
    }

    public List<Car> readAllUnleasedOnDateForRole(Date date, ArrayList<Role> roles) {
        List<Car> carList = new ArrayList<>();

        try {
            PreparedStatement pst = conn.prepareStatement("""
                    SELECT unleased.*
                    FROM car unleased
                    WHERE (unleased.state = 'RETURNED')
                      AND unleased.vehicleID NOT IN (SELECT leased.vehicleID
                                                     FROM car leased
                                                              join leasecontract l on leased.vehicleID = l.vehicleID
                                                     WHERE startDate <= ?
                                                       AND              ? <= endDate)
                    """);
            pst.setDate(1, date);
            pst.setDate(2, date);
            ResultSet resultSet = pst.executeQuery();

            // list of entities
            while (resultSet.next()) {
                carList.add(new Car(
                        resultSet.getInt("vehicleID"),
                        resultSet.getString("chassisNumber"),
                        resultSet.getDouble("steelPrice"),
                        resultSet.getString("brand"),
                        resultSet.getString("model"),
                        EquipmentLevel.valueOf(resultSet.getString("equipmentLevel")),
                        resultSet.getDouble("registrationFee"),
                        resultSet.getDouble("co2emission"),
                        State.valueOf(resultSet.getString("state"))));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return carList;
    }

    public void updateState(int vehicleID, State state) {
        try {
            PreparedStatement psts = conn.prepareStatement("UPDATE car SET state = ? WHERE vehicleID = ?");
            psts.setString(1, state.toString());
            psts.setInt(2, vehicleID);
            //ResultSet resultSet =
            psts.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
