package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.Customer;
import com.example.bilabonnement.model.Optional;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerRepositoryTest {

    CustomerRepository customerRepository = new CustomerRepository();

    @Test
    void create() {
        // # arrange #
        int customerID = 1;

        Customer expected = new Customer(customerID, "firstName", "lastName", "email_1", "address", "city", 2900, "mobile", "cprNumber");

        // delete previous
        customerRepository.delete(customerID);

        // # act #
        customerRepository.create(expected);

        Customer actual = customerRepository.read(customerID);


        // # assert #
        assertEquals(actual.getCustomerID(), expected.getCustomerID());
        assertEquals(actual.getFirstName(), expected.getFirstName());
        assertEquals(actual.getLastName(), expected.getLastName());
        assertEquals(actual.getEmail(), expected.getEmail());
        assertEquals(actual.getAddress()    , expected.getAddress());
        assertEquals(actual.getCity(), expected.getCity());
        assertEquals(actual.getZipCode(), expected.getZipCode());

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