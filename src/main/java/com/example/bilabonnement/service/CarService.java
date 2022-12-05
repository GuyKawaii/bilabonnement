package com.example.bilabonnement.service;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.repository.CarRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static com.example.bilabonnement.model.enums.State.IS_LEASED;

public class CarService {
    CarRepository carRepository = new CarRepository();

    public void create(Car car) {
        carRepository.create(car);
    }

    public List<Car> readAll(){
        return carRepository.readAll();
    }

    public List<Car>  getLeasedCarsList(){
        List<Car> fullCarList = carRepository.readAll();
        List<Car> leasedCarList = new ArrayList<>();

        for (Car c:fullCarList
             ) {
            if (c.getState().equals(IS_LEASED)) {
                leasedCarList.add(c);
            }
        }
        return leasedCarList;
    }
    public int getLeasedCarsAmount() {
        List<Car> fullCarList = carRepository.readAll();
        int numOfleasedCars = 0;

        for (Car c:fullCarList
        ) {
            if (c.getState().equals(IS_LEASED)) {
                numOfleasedCars++;
            }
        }
        return numOfleasedCars;
    }

    public Car read(int id){
        return carRepository.read(id);
    }

    public void update(Car car){
        carRepository.update(car);
    }
    public void updateState(int id){
        carRepository.updateState(id);
    }

    public void delete(int id){
        carRepository.delete(id);
    }

    // specific for service

    public List<Car> readAllLeasedOnDate(Date date) {
        return carRepository.readAllLeasedOnDate(date);
    }

    public List<Car> readAllUnleasedOnDate(Date date) {
        return carRepository.readAllUnleasedOnDate(date);
    }
}
