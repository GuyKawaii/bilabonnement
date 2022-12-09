package com.example.bilabonnement.service;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.model.enums.DB_CONNECTION;
import com.example.bilabonnement.model.enums.EquipmentLevel;
import com.example.bilabonnement.model.enums.Role;
import com.example.bilabonnement.model.enums.State;
import com.example.bilabonnement.repository.CarRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import static com.example.bilabonnement.model.enums.State.*;
import static com.example.bilabonnement.model.enums.State.AT_CUSTOMER;

public class CarService {
    private CarRepository carRepository = new CarRepository(DB_CONNECTION.RELEASE_DB);

    public void create(Car car) {
        carRepository.create(car);
    }

    public List<Car> readAll() {
        return carRepository.readAll();
    }


    public int getLeasedCarsAmount() {
        List<Car> fullCarList = carRepository.readAll();
        int numOfleasedCars = 0;

        for (Car c : fullCarList
        ) {
            if (c.getState().equals(AT_CUSTOMER)) {
                numOfleasedCars++;
            }
        }
        return numOfleasedCars;
    }

    public Car read(int id) {
        return carRepository.read(id);
    }

    public void update(Car car) {
        carRepository.update(car);
    }

    public void delete(int id) {
        carRepository.delete(id);
    }

    // specific for service

    public List<Car> readAllLeasedOnDate(Date date) {
        return carRepository.readAllLeasedOnDate(date);
    }

    public List<Car> readAllUnleasedOnDate(Date date) {
        return carRepository.readAllUnleasedOnDate(date);
    }

    public int getLeasedCarsAmountOnDate(Date date) {
        return carRepository.readAllUnleasedOnDate(date).size();
    }

    public List<State> getCarStates() {
        return new ArrayList<>(EnumSet.allOf(State.class));
    }

    public void updateState(int vehicleID, State state) {
        carRepository.updateState(vehicleID, state);
    }

    public List<EquipmentLevel> getEquipmentLevels() {
        return new ArrayList<>(EnumSet.allOf(EquipmentLevel.class));
    }

    public List<Car> readAllLeasedOnDateWithState(Date date, State state) {
        return carRepository.readAllLeasedOnDateWithState(date, state);
    }
}
