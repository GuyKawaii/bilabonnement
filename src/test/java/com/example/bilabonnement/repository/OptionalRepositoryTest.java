package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.optional;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OptionalRepositoryTest {

    OptionalRepository optionalRepository = new OptionalRepository();

    @Test
    void create() {
        // # arrange #
        optional expected = new optional(1, "name", 10);

        // delete previous
        optionalRepository.delete(1);

        // # act #
        optionalRepository.create(expected);

        optional actual = optionalRepository.read(1);


        // # assert #
        assertEquals(actual.getOptionalID(), expected.getOptionalID());
        assertEquals(actual.getName(), expected.getName());
        assertEquals(actual.getPricePrMonth(), expected.getPricePrMonth());
    }

    @Test
    void readAll() {
        // # arrange # 
        List<optional> expected = new ArrayList<>();
        expected.add(new optional(1, "name 1", 10));
        expected.add(new optional(2, "name 2", 20));

        // delete previous
        for (com.example.bilabonnement.model.optional optional : expected)
            optionalRepository.delete(optional.getOptionalID());
        // create new
        for (com.example.bilabonnement.model.optional optional : expected)
            optionalRepository.create(optional);

        // # act #
        List<optional> actual = optionalRepository.readAll();

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
        optional expected = new optional(1, "name", 10);

        // delete previous
        optionalRepository.delete(1);

        optionalRepository.create(expected);

        // # act #
        optional actual = optionalRepository.read(1);

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

        optional expected = new optional(optionalID, "name", 10);
        optional before = new optional(optionalID, "oldName", 100);

        optionalRepository.create(before);

        // # act #
        optionalRepository.update(expected);

        optional actual = optionalRepository.read(optionalID);

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

        optional expected = new optional(optionalID, "name", 10);

        optionalRepository.create(expected);

        // # act #
        optional actual = optionalRepository.read(optionalID);

        optionalRepository.delete(optionalID);

        optional actualNull = optionalRepository.read(optionalID);

        // # assert #
        assertEquals(actual.getOptionalID(), expected.getOptionalID());
        assertEquals(actual.getName(), expected.getName());
        assertEquals(actual.getPricePrMonth(), expected.getPricePrMonth());

        assertNull(actualNull);
    }
}