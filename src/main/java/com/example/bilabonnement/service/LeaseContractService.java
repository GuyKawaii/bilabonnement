package com.example.bilabonnement.service;

import com.example.bilabonnement.model.LeaseContract;
import com.example.bilabonnement.repository.LeaseContractRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class LeaseContractService {

    LeaseContractRepository leaseContractRepo = new LeaseContractRepository();

    public void create(LeaseContract leaseContract) {
            leaseContractRepo.create(leaseContract);
    }

    public List<LeaseContract> readAll() {
        return leaseContractRepo.readAll();
    }

    public LeaseContract read(int id) {
        return leaseContractRepo.read(id);
    }

    public void update(LeaseContract leaseContract) {
        leaseContractRepo.update(leaseContract);
    }

    public void delete(int id) {
        leaseContractRepo.delete(id);
    }

    public  double getCurrentIncome() {
        return leaseContractRepo.getCurrentIncome(Date.valueOf(LocalDate.now()));
    }

    public  double getCurrentIncomeByDate(Date date) {
        return leaseContractRepo.getCurrentIncome(date);
    }

}
