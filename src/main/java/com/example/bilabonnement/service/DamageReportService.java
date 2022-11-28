package com.example.bilabonnement.service;

import com.example.bilabonnement.model.DamageReport;
import com.example.bilabonnement.repository.DamageReportRepository;

import java.util.List;

public class DamageReportService {

    DamageReportRepository damageRepo = new DamageReportRepository();

    public void create(DamageReport damageReport){
        damageRepo.create(damageReport);
    }

    public List<DamageReport> readAll(){
        return damageRepo.readAll();
    }

    public DamageReport read(int id){
        return damageRepo.read(id);
    }

    public void update(DamageReport damageReport){
        damageRepo.update(damageReport);
    }

    public void delete(int id){
        damageRepo.delete(id);
    }

    // specific for service

}
