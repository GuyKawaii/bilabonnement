package com.example.bilabonnement.service;


import com.example.bilabonnement.model.Customer;
import com.example.bilabonnement.repository.*;

import java.util.List;

public class CustomerService {
    CustomerRepository customerRepo = new CustomerRepository();

    public void create(Customer customer) {
        customerRepo.create(customer);
    }

    public List<Customer> readAll() {
        return customerRepo.readAll();
    }

    public Customer read(int id) {
        return customerRepo.read(id);
    }

    public void update(Customer customer) {
        customerRepo.update(customer);
    }

    public void delete(int id) {
        customerRepo.delete(id);
    }

    // specific for repo

}

