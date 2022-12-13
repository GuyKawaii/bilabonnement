package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.Employee;
import com.example.bilabonnement.model.enums.DB_CONNECTION;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static com.example.bilabonnement.model.enums.Role.DAMAGE_REPORTER;
import static com.example.bilabonnement.model.enums.Role.DATA_REGISTRATION;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeRepositoryTest {

    EmployeeRepository employeeRepository = new EmployeeRepository(DB_CONNECTION.TEST_DB);

    @Test
    void create() {
        // # arrange #
        int employeeID = 1;

        // # delete previous #
        employeeRepository.delete(employeeID);

        Employee expected = new Employee(employeeID, "email", "name", "password123", DAMAGE_REPORTER);

        // # act #
        employeeRepository.create(expected);

        // return value
        Employee actual = employeeRepository.read(employeeID);

        // # assert #
        assertEquals(actual.getEmployeeID(), expected.getEmployeeID());
        assertEquals(actual.getEmail(), expected.getEmail());
        assertEquals(actual.getName(), expected.getName());
        assertEquals(actual.getPassword(), expected.getPassword());
        assertEquals(actual.getRole(), expected.getRole());
    }

    @Test
    void readAll() {
        // # arrange #

        // delete previous
        employeeRepository.delete(1);
        employeeRepository.delete(2);

        List<Employee> expectedList = new ArrayList<>();
        expectedList.add(new Employee(1, "email1", "name1", "password1", DAMAGE_REPORTER));
        expectedList.add(new Employee(2, "email2", "name2", "password2", DATA_REGISTRATION));
        employeeRepository.create(expectedList.get(0));
        employeeRepository.create(expectedList.get(1));

        // # act #
        List<Employee> actualList = employeeRepository.readAll();


        // # assert # (compare actual vs expected)
        assertEquals(actualList.get(0).getEmployeeID(), expectedList.get(0).getEmployeeID());
        assertEquals(actualList.get(0).getEmail(), expectedList.get(0).getEmail());
        assertEquals(actualList.get(0).getName(), expectedList.get(0).getName());
        assertEquals(actualList.get(0).getPassword(), expectedList.get(0).getPassword());
        assertEquals(actualList.get(0).getRole(), expectedList.get(0).getRole());

        assertEquals(actualList.get(1).getEmployeeID(), expectedList.get(1).getEmployeeID());
        assertEquals(actualList.get(1).getEmail(), expectedList.get(1).getEmail());
        assertEquals(actualList.get(1).getName(), expectedList.get(1).getName());
        assertEquals(actualList.get(1).getPassword(), expectedList.get(1).getPassword());
        assertEquals(actualList.get(1).getRole(), expectedList.get(1).getRole());
    }

    @Test
    void read() {
        // # arrange #

        // delete previous
        employeeRepository.delete(1);

        Employee expected = new Employee(1, "email1", "name1", "password1", DAMAGE_REPORTER);
        employeeRepository.create(expected);

        // # act #
        Employee actual = employeeRepository.read(1);

        // # assert #
        assertEquals(actual.getEmployeeID(), expected.getEmployeeID());
        assertEquals(actual.getEmail(), expected.getEmail());
        assertEquals(actual.getName(), expected.getName());
        assertEquals(actual.getPassword(), expected.getPassword());
        assertEquals(actual.getRole(), expected.getRole());

        // cleanup
        employeeRepository.delete(1);
    }

    @Test
    void update() {
        // # arrange #

        // delete previous
        employeeRepository.delete(1);

        Employee expected = new Employee(1, "email", "name", "password", DAMAGE_REPORTER);
        Employee before = new Employee(1, "oldEmail", "oldName", "oldPassword", DAMAGE_REPORTER);

        // setup before
        employeeRepository.create(before);

        // # act #
        employeeRepository.update(expected);
        Employee actual = employeeRepository.read(1);

        // # assert #
        assertEquals(actual.getEmployeeID(), expected.getEmployeeID());
        assertEquals(actual.getEmail(), expected.getEmail());
        assertEquals(actual.getName(), expected.getName());
        assertEquals(actual.getPassword(), expected.getPassword());
        assertEquals(actual.getRole(), expected.getRole());
    }

    @Test
    void delete() {
        // # arrange #
        int employeeID = 1;

        // delete previous
        employeeRepository.delete(employeeID);

        Employee expected = new Employee(1, "email1", "name1", "password1", DAMAGE_REPORTER);

        employeeRepository.create(expected);

        // # act #
        Employee actual = employeeRepository.read(employeeID);

        employeeRepository.delete(employeeID);

        Employee actualNull = employeeRepository.read(employeeID);

        // # assert #
        assertEquals(actual.getEmployeeID(), expected.getEmployeeID());
        assertEquals(actual.getEmail(), expected.getEmail());
        assertEquals(actual.getName(), expected.getName());
        assertEquals(actual.getPassword(), expected.getPassword());
        assertEquals(actual.getRole(), expected.getRole());
        assertNull(actualNull);
    }

}