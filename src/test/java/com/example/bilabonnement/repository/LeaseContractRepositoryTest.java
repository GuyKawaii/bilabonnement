
package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.*;
import com.example.bilabonnement.model.enums.DB_CONNECTION;
import com.example.bilabonnement.model.enums.Role;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

    /**
     * @author daniel(GuyKawaii)
     * @author Ian(DatJustino)
     */
    @Test
    void create() {
        // arrange
        int leaseID = 1;
        Optional optional = new Optional(1, "name", 30);
        Optional optional2 = new Optional(2, "name", 60);
        List<Optional> optionalList = new ArrayList<>();
        optionalList.add(optional);
        optionalList.add(optional2);


        // delete previous
        leaseRepo.delete(leaseID);
        optionalRepository.delete(optional.getOptionalID());
        optionalRepository.delete(optional2.getOptionalID());


        //act
        optionalRepository.create(optional);
        optionalRepository.create(optional2);
        LeaseContract expected = new LeaseContract(leaseID, Date.valueOf("2019-01-1"), Date.valueOf("2020-04-01"), 666, 1, 2, 1, optionalList);
        leaseRepo.create(expected);

        LeaseContract actual = leaseRepo.read(leaseID);

        //assert
        assertEquals(expected.getLeaseID(),actual.getLeaseID());
        assertEquals(expected.getStartDate(), actual.getStartDate());
        assertEquals(expected.getEndDate(), actual.getEndDate());
        assertEquals(expected.getMonthlyPrice(), actual.getMonthlyPrice());
        assertEquals(expected.getCustomerID(), actual.getCustomerID() );
        assertEquals(expected.getVehicleID(), actual.getVehicleID());
        assertEquals(expected.getEmployeeID(),actual.getEmployeeID());
        assertEquals(expected.getOptionalsAmount(), actual.getOptionalsAmount());
    }

    /**
     * @author daniel(GuyKawaii)
     * @author Ian(DatJustino)
     * @author Veronica(Rhod1um)
     */
    @Test
    void update() { // todo clean up
        // # arrange #
        int leaseID = 1;
        leaseRepo.delete(leaseID);
        Optional optional = new Optional(1, "name", 30);
        Optional optional2 = new Optional(2, "name", 60);


        List<Optional> optionalList = new ArrayList<>();
        optionalList.add(optional);
        optionalList.add(optional2);



        customerRepository.delete(1);
        employeeRepository.delete(1);
        carRepository.delete(1);
        optionalRepository.delete(optional.getOptionalID());
        optionalRepository.delete(optional2.getOptionalID());

        customerRepository.create(new Customer(1, "Test", "TEST", "test@mail.com", "testaddress", "testcity", 2500, "2234", "2234"));
        carRepository.create(new Car(1,"Test", 90, "brand", "model", MEDIUM, 100, 200, AT_CUSTOMER));
        employeeRepository.create(new Employee(1, "email", "name", "password", DATA_REGISTRATION));
        optionalRepository.create(optional);
        optionalRepository.create(optional2);
        // delete previous



        LeaseContract expected = new LeaseContract(leaseID, Date.valueOf("2020-01-1"), Date.valueOf("2020-04-01"), 666, 1, 1, 1, optionalList);

        LeaseContract before = new LeaseContract(leaseID, Date.valueOf("2021-01-1"), Date.valueOf("2021-04-01"), 777, 1, 1, 1, optionalList);
        // create
        leaseRepo.create(before);


        // # act #
        leaseRepo.update(expected);
        LeaseContract actual = leaseRepo.read(leaseID);
        System.out.println(actual);

        // # assert #
        assertEquals(expected.getLeaseID(), actual.getLeaseID());
        assertEquals(expected.getStartDate(), actual.getStartDate());
        assertEquals(expected.getMonthlyPrice(), actual.getMonthlyPrice());
    }
    /**
     * @author Ian(DatJustino)
     * @author Veronica(Rhod1um)
     */
    @Test
    void delete() {
        int leaseID = 1;
        // delete previous
        leaseRepo.delete(leaseID);

        Optional optional = new Optional(1, "name", 30);
        Optional optional2 = new Optional(2, "name", 60);


        List<Optional> optionalList = new ArrayList<>();
        optionalList.add(optional);
        optionalList.add(optional2);


        LeaseContract expected = new LeaseContract(leaseID, Date.valueOf("2019-01-1"), Date.valueOf("2020-04-01"), 666, 1, 2, 1, optionalList);

        // # Delete previous optionals
        optionalRepository.delete(optional.getOptionalID());
        optionalRepository.delete(optional2.getOptionalID());

        // # Create objects
        optionalRepository.create(optional);
        optionalRepository.create(optional2);
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
        assertEquals(actual.getOptionalsAmount(),expected.getOptionalsAmount());
        assertNull(actualNull);
    }


    /**
     * @author daniel(GuyKawaii)
     * @author Ian(DatJustino)
     */
    @Test
    void getCurrentIncome() {
        int baseID = 1;
        int leaseID_1 = 1;
        int leaseID_2 = 2;
        int leaseID_3 = 3;
        Optional optional1 = new Optional(1, "name", 1);
        Optional optional2 = new Optional(2, "name", 2);
        Optional optional3 = new Optional(3, "name", 3);


        List<Optional> optionalList = new ArrayList<>();
        List<Optional> optionalListEmpty = new ArrayList<>();
        List<Optional> optionalListforDate = new ArrayList<>();

        optionalList.add(optional1);
        optionalList.add(optional2);
        optionalListforDate.add(optional3);


        // delete previous
        leaseRepo.delete(leaseID_1);
        leaseRepo.delete(leaseID_2);
        leaseRepo.delete(leaseID_3);
        optionalRepository.delete(optional1.getOptionalID());
        optionalRepository.delete(optional2.getOptionalID());
        optionalRepository.delete(optional3.getOptionalID());

        // base
        carRepository.delete(baseID);
        customerRepository.delete(baseID);
        employeeRepository.delete(baseID);

        // test check variables
        Date checkdate = Date.valueOf("2000-01-02");
        double leaseWithoutOptionalsPrice = 3000;
        double leaseWithOptionalsPrice = 200;
        double leaseOutsideTimeWindowPrice = 400;

        // create instances
        Customer customer = new Customer(baseID, "firstName", "lastName", "email_99",
            "address", "city", 2900, "mobile", "cprNumber");
        Car car = new Car(baseID, "chassisNumber", 100, "brand", "model",
            MEDIUM, 100, 100, AT_CUSTOMER);
        Employee employee = new Employee(baseID, "email", "employee", "123", DATA_REGISTRATION);



        LeaseContract leaseWithOptionals = new LeaseContract(
            1,
            Date.valueOf("2000-01-01"),
            Date.valueOf("2000-02-01"),
            leaseWithOptionalsPrice,
            customer.getCustomerID(),
            car.getVehicleID(),
            employee.getEmployeeID(),
            optionalList);

        LeaseContract leaseWithoutOptionals = new LeaseContract(
            2,
            Date.valueOf("2000-01-01"),
            Date.valueOf("2000-02-01"),
            leaseWithoutOptionalsPrice,
            customer.getCustomerID(),
            car.getVehicleID(),
            employee.getEmployeeID(),
        optionalListEmpty);

        LeaseContract leaseOutsideTimeWindow = new LeaseContract(
            3,
            Date.valueOf("2000-01-01"),
            Date.valueOf("2000-02-01"),
            leaseOutsideTimeWindowPrice,
            customer.getCustomerID(),
            car.getVehicleID(),
            employee.getEmployeeID(),
            optionalListforDate);



        double expected = leaseOutsideTimeWindow.optionalsPrice() + leaseWithOptionals.optionalsPrice() + leaseWithoutOptionalsPrice + leaseWithOptionalsPrice + leaseOutsideTimeWindowPrice;

        // insert into db
        customerRepository.create(customer);
        employeeRepository.create(employee);
        carRepository.create(car);
        optionalRepository.create(optional1);
        optionalRepository.create(optional2);
        optionalRepository.create(optional3);


        leaseRepo.create(leaseWithOptionals);
        leaseRepo.create(leaseWithoutOptionals);
        leaseRepo.create(leaseOutsideTimeWindow);

        // # act #
        double actual = leaseRepo.getCurrentIncome(checkdate);


        System.out.println(leaseWithOptionals.getMonthlyPrice());
        System.out.println(leaseWithoutOptionals.getMonthlyPrice());
        System.out.println(leaseOutsideTimeWindow.getMonthlyPrice());

        System.out.println(optionalList.get(0).getPricePrMonth());
        System.out.println(optionalList.get(1).getPricePrMonth() + "\n");

        System.out.println(optionalListforDate.get(0).getPricePrMonth());

        System.out.println(leaseWithOptionals.optionalsPrice() + "\n");
        System.out.println(leaseOutsideTimeWindow.optionalsPrice());



        // # assert #
        assertEquals(expected, actual);

    }
}
