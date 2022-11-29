package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.model.Employee;
import com.example.bilabonnement.model.enums.FuelType;
import com.example.bilabonnement.model.enums.Role;
import com.example.bilabonnement.model.enums.State;
import com.example.bilabonnement.utility.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FleetRepository implements IGenericRepository<Car> {
    Connection conn = DatabaseConnectionManager.getConnection();

    @Override
    public void create(Car car) {
        try {
            PreparedStatement psts;
            if (car.getVehicleID() == null) {
                psts = conn.prepareStatement("INSERT INTO bilabonnement.fleet (chassisNumber, color, brand, model, co2emission, geartype, kmPerLiter, fuelType, kmDriven, state) VALUES (?,?,?,?,?,?,?,?,?,?)");
                psts.setString(1, car.getChassisNumber());
                psts.setString(2, car.getColor());
                psts.setString(3, car.getBrand());
                psts.setString(4, car.getModel());
                psts.setInt(5, car.getCo2emission());
                psts.setString(6, car.getGeartype());
                psts.setInt(7, car.getKmPerLiter());
                psts.setString(8, car.getFuelType().toString());
                psts.setInt(9, car.getKmDriven());
                psts.setString(10, car.getState().toString());
            } else {
                psts = conn.prepareStatement("INSERT INTO bilabonnement.fleet (vehicleID, chassisNumber, color, brand, model, co2emission, geartype, kmPerLiter, fuelType, kmDriven, state) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
                psts.setInt(1, car.getVehicleID());
                psts.setString(2, car.getChassisNumber());
                psts.setString(3, car.getColor());
                psts.setString(4, car.getBrand());
                psts.setString(5, car.getModel());
                psts.setInt(6, car.getCo2emission());
                psts.setString(7, car.getGeartype());
                psts.setInt(8, car.getKmPerLiter());
                psts.setString(9, car.getFuelType().toString());
                psts.setInt(10, car.getKmDriven());
                psts.setString(11, car.getState().toString());
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
            PreparedStatement pst = conn.prepareStatement("select * from bilabonnement.fleet");
            ResultSet resultSet = pst.executeQuery();

            // list of entities
            while (resultSet.next()) {
                carList.add(new Car(
                        resultSet.getInt("vehicleID"),
                        resultSet.getString("chassisNumber"),
                        resultSet.getString("color"),
                        resultSet.getString("brand"),
                        resultSet.getString("model"),
                        resultSet.getInt("co2emission"),
                        resultSet.getString("geartype"),
                        resultSet.getInt("kmPerLiter"),
                        FuelType.valueOf(resultSet.getString("fuelType")),
                        resultSet.getInt("kmDriven"),
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
            PreparedStatement psts = conn.prepareStatement("SELECT * FROM bilabonnement.fleet WHERE vehicleID = ?");
            psts.setInt(1, id);
            ResultSet resultSet = psts.executeQuery();


            // entity parameters
            while (resultSet.next()) {
                car = new Car(
                        resultSet.getInt("vehicleID"),
                        resultSet.getString("chassisNumber"),
                        resultSet.getString("color"),
                        resultSet.getString("brand"),
                        resultSet.getString("model"),
                        resultSet.getInt("co2emission"),
                        resultSet.getString("geartype"),
                        resultSet.getInt("kmPerLiter"),
                        FuelType.valueOf(resultSet.getString("fuelType")),
                        resultSet.getInt("kmDriven"),
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
            PreparedStatement psts = conn.prepareStatement("UPDATE bilabonnement.fleet SET chassisNumber = ?, color = ?, brand = ?, model = ?, co2emission = ?, geartype = ?, kmPerLiter = ?, fuelType = ?, kmDriven = ?, state = ? WHERE vehicleID = ?");
            psts.setString(1, car.getChassisNumber());
            psts.setString(2, car.getColor());
            psts.setString(3, car.getBrand());
            psts.setString(4, car.getModel());
            psts.setInt(5, car.getCo2emission());
            psts.setString(6, car.getGeartype());
            psts.setInt(7, car.getKmPerLiter());
            psts.setString(8, car.getFuelType().toString());
            psts.setInt(9, car.getKmDriven());
            psts.setString(10, car.getState().toString());
            psts.setInt(11, car.getVehicleID());
            ResultSet resultSet = psts.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateState(int id){
        try {
            PreparedStatement psts = conn.prepareStatement("UPDATE bilabonnement.fleet SET state = ? WHERE vehicleID = ?");
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
            PreparedStatement psts = conn.prepareStatement("DELETE FROM bilabonnement.fleet WHERE vehicleID = ?");
            psts.setInt(1, id);
            psts.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
