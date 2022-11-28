package com.example.bilabonnement.service;

import com.example.bilabonnement.model.Employee;
import com.example.bilabonnement.repository.EmployeeRepository;

import java.util.List;

public class EmployeeService {
    EmployeeRepository employeeRepo = new EmployeeRepository();

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

    public Employee getEmployee(String email) {
        return employeeRepo.readByEmail(email);
    }

}
