
package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.Employee;
import com.example.bilabonnement.model.LeaseContract;
import com.example.bilabonnement.model.enums.DB_CONNECTION;
import com.example.bilabonnement.model.Optional;
import org.junit.jupiter.api.Test;

import java.rmi.dgc.Lease;
import java.sql.Date;
import java.util.Calendar;

import static com.example.bilabonnement.model.enums.Role.DAMAGE_REPORTER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LeaseContractRepositoryTest {

  LeaseContractRepository leaseRepo = new LeaseContractRepository(DB_CONNECTION.TEST_DB);
  java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());

  @Test
  void create(){
    // arrange
    int leaseID = 1;
    LeaseContract expected = new LeaseContract(leaseID, Date.valueOf("2019-01-1"), Date.valueOf("2020-04-01"), 666, 201, 501, 101);

    // delete previous
    leaseRepo.delete(leaseID);

    //act
    leaseRepo.create(expected);

    LeaseContract actual = leaseRepo.read(leaseID);

    //assert
    assertEquals(actual.getLeaseID(), expected.getLeaseID());
    assertEquals(actual.getStartDate(), expected.getStartDate());
    assertEquals(actual.getEndDate(), expected.getEndDate());
    assertEquals(actual.getMonthlyPrice(), expected.getMonthlyPrice());
    assertEquals(actual.getCustomerID(), expected.getCustomerID());
    assertEquals(actual.getVehicleID(), expected.getVehicleID());
    assertEquals(actual.getEmployeeID(), expected.getEmployeeID());
  }

  @Test
  void update() {
    // # arrange #
    int leaseID = 1;
    leaseRepo.delete(leaseID);
    // delete previous
    LeaseContract expected = new LeaseContract(Date.valueOf("2020-01-1"), Date.valueOf("2020-04-01"), 666, 201, 501, 101);

    // LeaseContract actual = new LeaseContract(leaseID, Date.valueOf("2019-01-1"), Date.valueOf("2020-04-01"), 666, 201, 501, 101);

    // # act #
    leaseRepo.update(expected);
    LeaseContract actual = leaseRepo.read(leaseID);
    System.out.println(actual);

    // # assert #
    assertEquals(expected.getLeaseID(), actual.getLeaseID());
    assertEquals(expected.getStartDate(), actual.getStartDate());
    assertEquals(expected.getMonthlyPrice(), actual.getMonthlyPrice());
  }

  @Test
  void delete(){
    int leaseID = 1;

    // delete previous
    leaseRepo.delete(leaseID);

    LeaseContract expected = new LeaseContract(leaseID, Date.valueOf("2019-01-1"), Date.valueOf("2020-04-01"), 666, 201, 501, 101);

    leaseRepo.create(expected);

    // # act #
    LeaseContract actual = leaseRepo.read(leaseID);

    leaseRepo.delete(leaseID);

    LeaseContract actualNull = leaseRepo.read(leaseID);

    // # assert #
    assertEquals(actual.getLeaseID(), expected.getLeaseID());
    assertEquals(actual.getStartDate(), expected.getStartDate());
    assertEquals(actual.getEndDate(), expected.getEndDate());
    assertEquals(actual.getMonthlyPrice(), expected.getMonthlyPrice());
    assertEquals(actual.getCustomerID(), expected.getCustomerID());
    assertEquals(actual.getVehicleID(), expected.getVehicleID());
    assertEquals(actual.getEmployeeID(), expected.getEmployeeID());

    assertNull(actualNull);
  }

}

