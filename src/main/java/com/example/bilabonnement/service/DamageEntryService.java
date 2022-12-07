package com.example.bilabonnement.service;

import com.example.bilabonnement.model.DamageEntry;
import com.example.bilabonnement.model.enums.DB_CONNECTION;
import com.example.bilabonnement.repository.DamageEntryRepository;

import java.util.List;

public class DamageEntryService {
    private DamageEntryRepository damageEntryRepo = new DamageEntryRepository(DB_CONNECTION.RELEASE_DB);

    public void create(DamageEntry damageEntry) {
        damageEntryRepo.create(damageEntry);
    }

    public List<DamageEntry> readAll(){
    return damageEntryRepo.readAll();
    }

    public DamageEntry read(int id){
        return damageEntryRepo.read(id);
    }

    public void update(DamageEntry damageEntry){
        damageEntryRepo.update(damageEntry);
    }

    public void delete(int id){
        damageEntryRepo.delete(id);
    }

    public List<DamageEntry> entriesByReport(int id) {
        return damageEntryRepo.entriesByReport(id);
    }

    // specific for service

}
