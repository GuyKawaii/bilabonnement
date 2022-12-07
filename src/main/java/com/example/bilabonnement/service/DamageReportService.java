package com.example.bilabonnement.service;

import com.example.bilabonnement.model.DamageReport;
import com.example.bilabonnement.model.enums.DB_CONNECTION;
import com.example.bilabonnement.repository.DamageReportRepository;

import java.util.List;

public class DamageReportService {

    private DamageReportRepository damageRepo = new DamageReportRepository(DB_CONNECTION.RELEASE_DB);

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

    public List<DamageReport> readAllFromEmployee(int employeeID) {
        return damageRepo.readAllFromEmployee(employeeID);
    }

    // specific for service

}
