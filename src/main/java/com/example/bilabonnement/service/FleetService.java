package com.example.bilabonnement.service;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.repository.FleetRepository;

import java.util.ArrayList;
import java.util.List;

import static com.example.bilabonnement.model.enums.State.IS_LEASED;

public class FleetService {
    FleetRepository fleetRepo = new FleetRepository();

    public void create(Car car) {
        fleetRepo.create(car);
    }

    public List<Car> readAll(){
        return fleetRepo.readAll();
    }

    public List<Car>  getLeasedCarsList(){
        List<Car> fullCarList = fleetRepo.readAll();
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
        List<Car> fullCarList = fleetRepo.readAll();
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
        return fleetRepo.read(id);
    }

    public void update(Car car){
        fleetRepo.update(car);
    }
    public void updateState(int id){
        fleetRepo.updateState(id);
    }

    public void delete(int id){
        fleetRepo.delete(id);
    }

    // specific for service
}
