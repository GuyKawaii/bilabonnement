
package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.*;
import com.example.bilabonnement.model.enums.DB_CONNECTION;
import com.example.bilabonnement.model.enums.Role;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;

import static com.example.bilabonnement.model.enums.DB_CONNECTION.*;
import static com.example.bilabonnement.model.enums.EquipmentLevel.*;
import static com.example.bilabonnement.model.enums.Role.DATA_REGISTRATION;
import static com.example.bilabonnement.model.enums.State.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LeaseContractRepositoryTest {

    LeaseContractRepository leaseRepo = new LeaseContractRepository(TEST_DB);
    CarRepository carRepository = new CarRepository(TEST_DB);
    CustomerRepository customerRepository = new CustomerRepository(TEST_DB);
    OptionalRepository optionalRepository = new OptionalRepository(TEST_DB);
    EmployeeRepository employeeRepository = new EmployeeRepository(TEST_DB);
    java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
    Customer testCustomer = new Customer("Test", "TEST", "test@mail.com", "testaddress", "testcity", 2500, "2234", "2234");

    @Test
    void create() {
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
    void delete() {
        int leaseID = 1;
        // delete previous
        leaseRepo.delete(leaseID);

        LeaseContract expected = new LeaseContract(leaseID, Date.valueOf("2019-01-1"), Date.valueOf("2020-04-01"), 666, 1, 1, 1);

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

    @Test
    void getCurrentIncome() {
        int baseID = 1;
        int leaseID_1 = 1;
        int leaseID_2 = 2;
        int leaseID_3 = 3;

        // delete previous
        leaseRepo.delete(leaseID_1);
        leaseRepo.delete(leaseID_2);
        leaseRepo.delete(leaseID_3);

        // base
        carRepository.delete(baseID);
        customerRepository.delete(baseID);
        employeeRepository.delete(baseID);
        optionalRepository.delete(baseID);

        // test check variables
        Date checkdate = Date.valueOf("2000-01-02");
        double optionalPrice = 10;
        double leaseWithoutOptionalsPrice = 3000;
        double leaseWithOptionalsPrice = 200;
        double expected = optionalPrice + leaseWithoutOptionalsPrice + leaseWithOptionalsPrice;

        // create instances
        Customer customer = new Customer(baseID, "firstName", "lastName", "email_99",
                "address", "city", 2900, "mobile", "cprNumber");
        Car car = new Car(baseID, "chassisNumber", 100, "brand", "model",
                MEDIUM, 100, 100, AT_CUSTOMER);
        Employee employee = new Employee(baseID, "email", "employee", "123", DATA_REGISTRATION);
        Optional optional = new Optional(baseID, "optional", 10);

        LeaseContract leaseWithOptionals = new LeaseContract(
                1,
                Date.valueOf("2000-01-01"),
                Date.valueOf("2000-02-01"),
                leaseWithOptionalsPrice,
                customer.getCustomerID(),
                car.getVehicleID(),
                employee.getEmployeeID());

        LeaseContract leaseWithoutOptionals = new LeaseContract(
                2,
                Date.valueOf("2000-01-01"),
                Date.valueOf("2000-02-01"),
                leaseWithoutOptionalsPrice,
                customer.getCustomerID(),
                car.getVehicleID(),
                employee.getEmployeeID());

        LeaseContract leaseOutsideTimeWindow = new LeaseContract(
                3,
                Date.valueOf("2000-01-01"),
                Date.valueOf("2000-02-01"),
                400,
                customer.getCustomerID(),
                car.getVehicleID(),
                employee.getEmployeeID());

        // insert into db
        customerRepository.create(customer);
        employeeRepository.create(employee);
        carRepository.create(car);
        optionalRepository.create(optional);

        leaseRepo.create(leaseWithOptionals);
        leaseRepo.create(leaseWithoutOptionals);
        leaseRepo.create(leaseOutsideTimeWindow);

        // # act #
        double actual = leaseRepo.getCurrentIncome(checkdate);

        // # assert #
        assertEquals(expected, actual);

    }
}

