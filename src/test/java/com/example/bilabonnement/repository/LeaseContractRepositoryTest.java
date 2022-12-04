/*
package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.LeaseContract;
import com.example.bilabonnement.model.optional;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LeaseContractRepositoryTest {

  LeaseContractRepository leaseRepo = new LeaseContractRepository();

  @Test
  void update() {
    // # arrange #
    int leaseID = 1;

    // delete previous
    LeaseContract expected = new LeaseContract(leaseID, "name", 10);
    leaseRepo.delete(leaseID);

    optional before = new optional(leaseID, "oldName", 100);

    leaseRepo.create(before);

    // # act #
    leaseRepo.update(expected);

    optional actual = leaseRepo.read(optionalID);

    // # assert #
    assertEquals(actual.getOptionalID(), expected.getOptionalID());
    assertEquals(actual.getName(), expected.getName());
    assertEquals(actual.getPricePrMonth(), expected.getPricePrMonth());
  }

}
*/
