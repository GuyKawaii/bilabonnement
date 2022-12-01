package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.Optional;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OptionalRepositoryTest {

    OptionalRepository optionalRepository = new OptionalRepository();

    @Test
    void create() {
        // # arrange #
        Optional expected = new Optional(1, "name", 10);

        // delete previous
        optionalRepository.delete(1);

        // # act #
        optionalRepository.create(expected);

        Optional actual = optionalRepository.read(1);


        // # assert #
        assertEquals(actual.getOptionalID(), expected.getOptionalID());
        assertEquals(actual.getName(), expected.getName());
        assertEquals(actual.getPricePrMonth(), expected.getPricePrMonth());
    }

    @Test
    void readAll() {
        // # arrange # 
        List<Optional> expected = new ArrayList<>();
        expected.add(new Optional(1, "name 1", 10));
        expected.add(new Optional(2, "name 2", 20));

        // delete previous
        for (Optional optional : expected)
            optionalRepository.delete(optional.getOptionalID());
        // create new
        for (Optional optional : expected)
            optionalRepository.create(optional);

        // # act #
        List<Optional> actual = optionalRepository.readAll();

        // # assert #
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getOptionalID(), actual.get(i).getOptionalID());
            assertEquals(expected.get(i).getName(), actual.get(i).getName());
            assertEquals(expected.get(i).getPricePrMonth(), actual.get(i).getPricePrMonth());
        }
    }

    @Test
    void read() {
        // # arrange #
        Optional expected = new Optional(1, "name", 10);

        // delete previous
        optionalRepository.delete(1);

        optionalRepository.create(expected);

        // # act #
        Optional actual = optionalRepository.read(1);

        // # assert #
        assertEquals(actual.getOptionalID(), expected.getOptionalID());
        assertEquals(actual.getName(), expected.getName());
        assertEquals(actual.getPricePrMonth(), expected.getPricePrMonth());
    }

    @Test
    void update() {
        // # arrange #
        int optionalID = 1;

        // delete previous
        optionalRepository.delete(optionalID);

        Optional expected = new Optional(optionalID, "name", 10);
        Optional before = new Optional(optionalID, "oldName", 100);

        optionalRepository.create(before);

        // # act #
        optionalRepository.update(expected);

        Optional actual = optionalRepository.read(optionalID);

        // # assert #
        assertEquals(actual.getOptionalID(), expected.getOptionalID());
        assertEquals(actual.getName(), expected.getName());
        assertEquals(actual.getPricePrMonth(), expected.getPricePrMonth());
    }

    @Test
    void delete() {
        // # arrange #
        int optionalID = 1;

        // delete previous
        optionalRepository.delete(optionalID);

        Optional expected = new Optional(optionalID, "name", 10);

        optionalRepository.create(expected);

        // # act #
        Optional actual = optionalRepository.read(optionalID);

        optionalRepository.delete(optionalID);

        Optional actualNull = optionalRepository.read(optionalID);

        // # assert #
        assertEquals(actual.getOptionalID(), expected.getOptionalID());
        assertEquals(actual.getName(), expected.getName());
        assertEquals(actual.getPricePrMonth(), expected.getPricePrMonth());

        assertNull(actualNull);
    }
}