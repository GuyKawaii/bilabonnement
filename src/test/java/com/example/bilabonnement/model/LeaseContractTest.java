package com.example.bilabonnement.model;

import com.example.bilabonnement.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class LeaseContractTest {

    @Test
    void testGetMonths() {
        // arrange
        LeaseContract dayAbove = new LeaseContract(1, Date.valueOf("2020-01-1"), Date.valueOf("2020-04-01"), 1, 1, 1, 1);
        LeaseContract threeMonths = new LeaseContract(1, Date.valueOf("2020-01-1"), Date.valueOf("2020-03-31"), 1, 1, 1, 1);
        LeaseContract dayBelow = new LeaseContract(1, Date.valueOf("2020-01-1"), Date.valueOf("2020-03-30"), 1, 1, 1, 1);

        int expected = 3;

        // act
        int actualWithDayAbove = dayAbove.getMonths();
        int actual = threeMonths.getMonths();
        int actualWithDayBelow = dayBelow.getMonths();

        // assert
        assertEquals(expected, actualWithDayAbove);
        assertEquals(expected, actual);
        assertEquals(expected - 1, actualWithDayBelow);
    }
}