package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.model.enums.DB_CONNECTION;
import com.example.bilabonnement.model.enums.EquipmentLevel;
import com.example.bilabonnement.model.enums.State;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CarRepositoryTest {
  CarRepository carRepo = new CarRepository(DB_CONNECTION.TEST_DB);



  @Test
  void createTest(){
    // ## arrange ##
    int carID = 2;
    Car expected = new Car(carID, "223123", 2, "brand",
        "123",
        EquipmentLevel.MEDIUM,
        2,
        2, State.READY);
    // delete
    carRepo.delete(carID);

    // ## act ##
    carRepo.create(expected);
    Car actual = carRepo.read(carID);

    // ## assert ##

    assertEquals(expected, actual);

  }



}
