package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.Customer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerRepositoryTest {

    CustomerRepository customerRepository = new CustomerRepository();

    @Test
    void create() {
        // # arrange #
        int customerID = 1;

        Customer expected = new Customer(customerID, "firstName", "lastName", "createTestEmail", "address", "city", 2900, "mobile", "cprNumber");

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
        assertEquals(actual.getPostalCode(), expected.getPostalCode());
        assertEquals(actual.getMobile(), expected.getMobile());
        assertEquals(actual.getCprNumber(), expected.getCprNumber());
    }

    @Test
    void readAll() {
        // # arrange #
        List<Customer> expected = new ArrayList<>();
        expected.add(new Customer(1, "firstName1", "lastName1", "readAllEmail1", "address1", "city1", 9001, "mobile1", "cprNumber1"));
        expected.add(new Customer(2, "firstName2", "lastName2", "readAllEmail2", "address2", "city2", 2900, "mobile2", "cprNumber2"));

        // delete previous
        for (Customer customer : expected)
            customerRepository.delete(customer.getCustomerID());
        // create new
        for (Customer customer : expected)
            customerRepository.create(customer);

        // # act #
        List<Customer> actual = customerRepository.readAll();

        // # assert #
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(actual.get(i).getCustomerID(), expected.get(i).getCustomerID());
            assertEquals(actual.get(i).getFirstName(), expected.get(i).getFirstName());
            assertEquals(actual.get(i).getLastName(), expected.get(i).getLastName());
            assertEquals(actual.get(i).getEmail(), expected.get(i).getEmail());
            assertEquals(actual.get(i).getAddress()    , expected.get(i).getAddress());
            assertEquals(actual.get(i).getCity(), expected.get(i).getCity());
            assertEquals(actual.get(i).getPostalCode(), expected.get(i).getPostalCode());
            assertEquals(actual.get(i).getMobile(), expected.get(i).getMobile());
            assertEquals(actual.get(i).getCprNumber(), expected.get(i).getCprNumber());
        }
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