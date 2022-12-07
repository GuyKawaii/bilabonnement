package com.example.bilabonnement.service;

import com.example.bilabonnement.model.Optional;
import com.example.bilabonnement.repository.IGenericRepository;
import com.example.bilabonnement.repository.OptionalRepository;
import static com.example.bilabonnement.model.enums.DB_CONNECTION.*;

import java.util.List;

public class OptionalService implements IGenericRepository<Optional> {

    OptionalRepository optionalRepository = new OptionalRepository(RELEASE_DB);

    public void create(Optional optional) {
        optionalRepository.create(optional);
    }

    public List<Optional> readAll() {
        return optionalRepository.readAll();
    }

    public Optional read(int optionalID) {
        return optionalRepository.read(optionalID);
    }

    public void update(Optional optional) {
        optionalRepository.update(optional);
    }

    public void delete(int optionalID) {
        optionalRepository.delete(optionalID);
    }
}
