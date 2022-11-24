package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.Employee;
import com.example.bilabonnement.model.enums.Role;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Equality;

import static com.example.bilabonnement.model.enums.Role.ADMINISTRATION;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeRepositoryTest {

    EmployeeRepository employeeRepository = new EmployeeRepository();

    @Test
    void create() {
        // test variables
        int id = 1;
        String email = "email";
        String name = "name";
        Role role = ADMINISTRATION;
        Employee employee = new Employee(id, email, name, role);

        // test case
        employeeRepository.create(employee);

        // return value
        employeeRepository.read(id);

        // assessment
//        assertEquals();





    }

    @Test
    void readAll() {
    }

    @Test
    void read() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}