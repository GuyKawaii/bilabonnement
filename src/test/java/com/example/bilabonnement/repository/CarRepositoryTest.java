package com.example.bilabonnement.repository;
/**
 * @author Ian(DatJustino)
 */
import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.model.Employee;
import com.example.bilabonnement.model.enums.DB_CONNECTION;
import com.example.bilabonnement.model.enums.EquipmentLevel;
import com.example.bilabonnement.model.enums.State;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.example.bilabonnement.model.enums.Role.DAMAGE_REPORTER;
import static org.junit.jupiter.api.Assertions.*;

public class CarRepositoryTest {
  CarRepository carRepo = new CarRepository(DB_CONNECTION.TEST_DB);


  @Test
  void createTest() {
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

    assertEquals(expected.getVehicleID(), actual.getVehicleID());
    assertEquals(expected.getChassisNumber(), actual.getChassisNumber());
    assertEquals(expected.getSteelPrice(), actual.getSteelPrice());
    assertEquals(expected.getBrand(), actual.getBrand());
    assertEquals(expected.getModel(), actual.getModel());
    assertEquals(expected.getEquipmentLevel(), actual.getEquipmentLevel());
    assertEquals(expected.getRegistrationFee(), actual.getRegistrationFee());
    assertEquals(expected.getCo2emission(), actual.getCo2emission());
    assertEquals(expected.getState(), actual.getState());

  }

  @Test
  void readallTest() {
    List<Car> expected = new ArrayList<>();
    int carID1 = 1;
    int carID2 = 2;

    Car car1 = new Car(carID1, "223123", 2, "brand",
        "123",
        EquipmentLevel.MEDIUM,
        2,
        2, State.READY);
    Car car2 = new Car(carID2, "444444", 2, "brand",
        "123",
        EquipmentLevel.MEDIUM,
        2,
        2, State.READY);

    expected.add(car1);
    expected.add(car2);

    for (Car car : expected)
      carRepo.delete(car.getVehicleID());
    carRepo.delete(501);
    carRepo.delete(502);
    carRepo.delete(503);
    carRepo.delete(504);
    carRepo.delete(505);
    carRepo.delete(506);
    carRepo.delete(507);


    for (Car car : expected)
      carRepo.create(car);


    List<Car> actual = carRepo.readAll();

    // # assert #
    for (int i = 0; i < actual.size(); i++) {
      assertEquals(expected.get(i).getVehicleID(), actual.get(i).getVehicleID());
      assertEquals(expected.get(i).getChassisNumber(), actual.get(i).getChassisNumber());
      assertEquals(expected.get(i).getSteelPrice(), actual.get(i).getSteelPrice());
      assertEquals(expected.get(i).getBrand(), actual.get(i).getBrand());
      assertEquals(expected.get(i).getModel(), actual.get(i).getModel());
      assertEquals(expected.get(i).getEquipmentLevel(), actual.get(i).getEquipmentLevel());
      assertEquals(expected.get(i).getRegistrationFee(), actual.get(i).getRegistrationFee());
      assertEquals(expected.get(i).getCo2emission(), actual.get(i).getCo2emission());
      assertEquals(expected.get(i).getState(), actual.get(i).getState());
    }
  }
  @Test
  void readTest(){
    // # arrange
    int carID1 = 1;

    // # Delete
    carRepo.delete(carID1);

    Car expected = new Car(carID1, "223123", 2, "brand",
        "123",
        EquipmentLevel.MEDIUM,
        2,
        2, State.READY);

    carRepo.create(expected);

    // # act
    Car actual = carRepo.read(expected.getVehicleID());

    // # Assert

    assertEquals(expected.getVehicleID(), actual.getVehicleID());
    assertEquals(expected.getChassisNumber(), actual.getChassisNumber());
    assertEquals(expected.getSteelPrice(), actual.getSteelPrice());
    assertEquals(expected.getBrand(), actual.getBrand());
    assertEquals(expected.getModel(), actual.getModel());
    assertEquals(expected.getEquipmentLevel(), actual.getEquipmentLevel());
    assertEquals(expected.getRegistrationFee(), actual.getRegistrationFee());
    assertEquals(expected.getCo2emission(), actual.getCo2emission());
    assertEquals(expected.getState(), actual.getState());
  }


  @Test
  void updateTest() {
    // ## arrange ##
    int carID = 2;
    Car expected = new Car(carID, "223123", 2, "brand",
        "123",
        EquipmentLevel.MEDIUM,
        2,
        2, State.READY);
    Car before = new Car(carID, "9999999", 4, "brand",
        "321",
        EquipmentLevel.MEDIUM,
        2,
        2, State.READY);


    // delete
    carRepo.delete(carID);

    // ## act ##
    carRepo.create(before);
    carRepo.update(expected);
    Car actual = carRepo.read(carID);

    // ## assert ##

    assertEquals(expected.getVehicleID(), actual.getVehicleID());
    assertEquals(expected.getChassisNumber(), actual.getChassisNumber());
    assertEquals(expected.getSteelPrice(), actual.getSteelPrice());
    assertEquals(expected.getBrand(), actual.getBrand());
    assertEquals(expected.getModel(), actual.getModel());
    assertEquals(expected.getEquipmentLevel(), actual.getEquipmentLevel());
    assertEquals(expected.getRegistrationFee(), actual.getRegistrationFee());
    assertEquals(expected.getCo2emission(), actual.getCo2emission());
    assertEquals(expected.getState(), actual.getState());

  }

  @Test
  void deleteTest(){
    // # arrange
    int carID1 = 1;

    // # Delete
    carRepo.delete(carID1);

    Car expected = new Car(carID1, "223123", 2, "brand",
        "123",
        EquipmentLevel.MEDIUM,
        2,
        2, State.READY);
    carRepo.create(expected);

    Car actual = carRepo.read(expected.getVehicleID());

    // # act
    carRepo.delete(expected.getVehicleID());
    Car actualNull = carRepo.read(expected.getVehicleID());


    // # Assert
    assertEquals(expected.getVehicleID(), actual.getVehicleID());
    assertEquals(expected.getChassisNumber(), actual.getChassisNumber());
    assertEquals(expected.getSteelPrice(), actual.getSteelPrice());
    assertEquals(expected.getBrand(), actual.getBrand());
    assertEquals(expected.getModel(), actual.getModel());
    assertEquals(expected.getEquipmentLevel(), actual.getEquipmentLevel());
    assertEquals(expected.getRegistrationFee(), actual.getRegistrationFee());
    assertEquals(expected.getCo2emission(), actual.getCo2emission());
    assertEquals(expected.getState(), actual.getState());
    assertNull(actualNull);



  }
  }

