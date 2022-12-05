package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.model.enums.FuelType;
import com.example.bilabonnement.model.enums.State;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarRepository implements IGenericRepository<Car> {
    Connection conn = DatabaseConnectionManager.getConnection();

    @Override
    public void create(Car car) {
        try {
            PreparedStatement psts;
            if (car.getVehicleID() == null) {
                psts = conn.prepareStatement("INSERT INTO bilabonnement.car (chassisNumber, steelPrice, color, brand, model, co2emission, geartype, kmPerLiter, fuelType, kmDriven, locationID, state) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
                psts.setString(1, car.getChassisNumber());
                psts.setDouble(2, car.getSteelPrice());
                psts.setString(3, car.getColor());
                psts.setString(4, car.getBrand());
                psts.setString(5, car.getModel());
                psts.setInt(6, car.getCo2emission());
                psts.setString(7, car.getGeartype());
                psts.setInt(8, car.getKmPerLiter());
                psts.setString(9, car.getFuelType().toString());
                psts.setInt(10, car.getKmDriven());
                psts.setInt(11, car.getLocationID());
                psts.setString(12, car.getState().toString());
            } else {
                psts = conn.prepareStatement("INSERT INTO bilabonnement.car (vehicleID, chassisNumber, steelPrice, color, brand, model, co2emission, geartype, kmPerLiter, fuelType, kmDriven, locationID, state) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
                psts.setInt(1, car.getVehicleID());
                psts.setString(2, car.getChassisNumber());
                psts.setDouble(3, car.getSteelPrice());
                psts.setString(4, car.getColor());
                psts.setString(5, car.getBrand());
                psts.setString(6, car.getModel());
                psts.setInt(7, car.getCo2emission());
                psts.setString(8, car.getGeartype());
                psts.setInt(9, car.getKmPerLiter());
                psts.setString(10, car.getFuelType().toString());
                psts.setInt(11, car.getKmDriven());
                psts.setInt(12, car.getLocationID());
                psts.setString(13, car.getState().toString());
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
            PreparedStatement pst = conn.prepareStatement("select * from bilabonnement.car");
            ResultSet resultSet = pst.executeQuery();

            // list of entities
            while (resultSet.next()) {
                carList.add(new Car(
                        resultSet.getInt("vehicleID"),
                        resultSet.getString("chassisNumber"),
                        resultSet.getDouble("steelPrice"),
                        resultSet.getString("color"),
                        resultSet.getString("brand"),
                        resultSet.getString("model"),
                        resultSet.getInt("co2emission"),
                        resultSet.getString("geartype"),
                        resultSet.getInt("kmPerLiter"),
                        FuelType.valueOf(resultSet.getString("fuelType")),
                        resultSet.getInt("kmDriven"),
                        resultSet.getInt("locationID"),
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
            PreparedStatement psts = conn.prepareStatement("SELECT * FROM bilabonnement.car WHERE vehicleID = ?");
            psts.setInt(1, id);
            ResultSet resultSet = psts.executeQuery();


            // entity parameters
            while (resultSet.next()) {
                car = new Car(
                        resultSet.getInt("vehicleID"),
                        resultSet.getString("chassisNumber"),
                        resultSet.getDouble("steelPrice"),
                        resultSet.getString("color"),
                        resultSet.getString("brand"),
                        resultSet.getString("model"),
                        resultSet.getInt("co2emission"),
                        resultSet.getString("geartype"),
                        resultSet.getInt("kmPerLiter"),
                        FuelType.valueOf(resultSet.getString("fuelType")),
                        resultSet.getInt("kmDriven"),
                        resultSet.getInt("locationID"),
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
            PreparedStatement psts = conn.prepareStatement("UPDATE bilabonnement.car SET chassisNumber = ?, steelPrice = ?, color = ?, brand = ?, model = ?, co2emission = ?, geartype = ?, kmPerLiter = ?, fuelType = ?, kmDriven = ?, state = ? WHERE vehicleID = ?");
            psts.setString(1, car.getChassisNumber());
            psts.setDouble(2, car.getSteelPrice());
            psts.setString(3, car.getColor());
            psts.setString(4, car.getBrand());
            psts.setString(5, car.getModel());
            psts.setInt(6, car.getCo2emission());
            psts.setString(7, car.getGeartype());
            psts.setInt(8, car.getKmPerLiter());
            psts.setString(9, car.getFuelType().toString());
            psts.setInt(10, car.getKmDriven());
            psts.setString(11, car.getState().toString());
            psts.setInt(12, car.getVehicleID());
            ResultSet resultSet = psts.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateState(int id) {
        try {
            PreparedStatement psts = conn.prepareStatement("UPDATE bilabonnement.car SET state = ? WHERE vehicleID = ?");
            psts.setString(1, State.IS_LEASED.toString());
            psts.setInt(2, id);
            //ResultSet resultSet =
            psts.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement psts = conn.prepareStatement("DELETE FROM bilabonnement.car WHERE vehicleID = ?");
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
            PreparedStatement pst = conn.prepareStatement(
                    "SELECT car.*\n" +
                            "FROM car\n" +
                            "         JOIN leasecontract l on car.vehicleID = l.vehicleID\n" +
                            "WHERE startDate <= ?\n" +
                            "  AND ? <= endDate");
            pst.setDate(1, date);
            pst.setDate(2, date);
            ResultSet resultSet = pst.executeQuery();

            // list of entities
            while (resultSet.next()) {
                carList.add(new Car(
                        resultSet.getInt("vehicleID"),
                        resultSet.getString("chassisNumber"),
                        resultSet.getDouble("steelPrice"),
                        resultSet.getString("color"),
                        resultSet.getString("brand"),
                        resultSet.getString("model"),
                        resultSet.getInt("co2emission"),
                        resultSet.getString("geartype"),
                        resultSet.getInt("kmPerLiter"),
                        FuelType.valueOf(resultSet.getString("fuelType")),
                        resultSet.getInt("kmDriven"),
                        resultSet.getInt("locationID"),
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
            PreparedStatement pst = conn.prepareStatement(
                    "SELECT unleased.*\n" +
                            "FROM car unleased\n" +
                            "WHERE unleased.vehicleID NOT IN (SELECT leased.vehicleID\n" +
                            "                                 FROM car leased\n" +
                            "                                          join leasecontract l on leased.vehicleID = l.vehicleID\n" +
                            "                                 WHERE startDate <= ?\n" +
                            "                                   AND ? <= endDate)");
            pst.setDate(1, date);
            pst.setDate(2, date);
            ResultSet resultSet = pst.executeQuery();

            // list of entities
            while (resultSet.next()) {
                carList.add(new Car(
                        resultSet.getInt("vehicleID"),
                        resultSet.getString("chassisNumber"),
                        resultSet.getDouble("steelPrice"),
                        resultSet.getString("color"),
                        resultSet.getString("brand"),
                        resultSet.getString("model"),
                        resultSet.getInt("co2emission"),
                        resultSet.getString("geartype"),
                        resultSet.getInt("kmPerLiter"),
                        FuelType.valueOf(resultSet.getString("fuelType")),
                        resultSet.getInt("kmDriven"),
                        resultSet.getInt("locationID"),
                        State.valueOf(resultSet.getString("state"))));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return carList;
    }
}
