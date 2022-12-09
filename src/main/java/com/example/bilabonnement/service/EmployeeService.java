package com.example.bilabonnement.service;

import com.example.bilabonnement.model.Employee;
import com.example.bilabonnement.model.LeaseContract;
import com.example.bilabonnement.model.enums.DB_CONNECTION;
import com.example.bilabonnement.model.enums.Role;
import com.example.bilabonnement.model.enums.State;
import com.example.bilabonnement.repository.EmployeeRepository;
import com.example.bilabonnement.repository.IGenericRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.bilabonnement.model.enums.State.*;

public class EmployeeService {
    private EmployeeRepository employeeRepo = new EmployeeRepository(DB_CONNECTION.RELEASE_DB);

    public void create(Employee employee) {
        employeeRepo.create(employee);
    }

    public List<Employee> readAll() {
        return employeeRepo.readAll();
    }

    public Employee read(int id) {
        return employeeRepo.read(id);
    }

    public void update(Employee employee) {
        employeeRepo.update(employee);
    }

    public void delete(int id) {
        employeeRepo.delete(id);
    }

    // specific for service

    public Employee getEmployeeByEmail(String email) {
        return employeeRepo.readByEmail(email);
    }

    public Employee login(String email, String password) {

        // read from DB
        Employee employee = getEmployeeByEmail(email);

        // check presence and password
        if (employee != null && password.equals(employee.getPassword())) {
            employee.setPassword(null);
            return employee;
        } else
            return null;
    }


    public static boolean validEmployeeRole(Role role, Role[] roles) {
        for (Role r : roles)
            if (r == role) return true;

        return false;
    }

    public List<State> getEmployeeStateSelect(Role role) {
        switch (role) {
            case DATA_REGISTRATION -> {
                return new ArrayList<State>(Arrays.asList(RETURNED, AT_CUSTOMER));
            }
            case DAMAGE_REPORTER -> {
                return new ArrayList<State>(Arrays.asList(READY));
            }
            default -> {
                return null;
            }
        }
    }

    public List<Employee> readAllWithRole(Role role) {
        return employeeRepo.readAllWithRole(role);
    }
}
