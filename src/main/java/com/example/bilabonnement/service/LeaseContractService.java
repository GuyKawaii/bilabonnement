package com.example.bilabonnement.service;

import com.example.bilabonnement.model.LeaseContract;
import com.example.bilabonnement.model.Optional;
import com.example.bilabonnement.model.enums.DB_CONNECTION;
import com.example.bilabonnement.repository.LeaseContractRepository;
import org.springframework.web.context.request.WebRequest;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LeaseContractService {

    private LeaseContractRepository leaseContractRepo = new LeaseContractRepository(DB_CONNECTION.RELEASE_DB);

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

    public double getCurrentIncome() {
        return leaseContractRepo.getCurrentIncome(Date.valueOf(LocalDate.now()));
        // test-commit
    }

    public double activeLeaseContractsByDate(Date date) {
        return leaseContractRepo.activeLeaseContractCountByDate(date);
    }

    public double getCurrentIncomeByDate(Date date) {
        return leaseContractRepo.getCurrentIncome(date);
    }

    public void updateOptionals(List<Optional> optionals, int leaseID) {
        leaseContractRepo.updateOptionals(optionals, leaseID);
    }

    // extra
    public int createAndReturnID(LeaseContract leaseContract) {
        return leaseContractRepo.createAndReturnID(leaseContract);
    }

    public List<LeaseContract> readActiveLeaseContracts(int vehicleID, Date valueOf) {
        return leaseContractRepo.readActiveLeaseContractsByVehicleID(vehicleID, valueOf);
    }

    public List<LeaseContract> readUpcomingLeaseContractsByVehicleID(int vehicleID, Date valueOf) {
        return leaseContractRepo.readUpcomingLeaseContractsByVehicleID(vehicleID, valueOf);
    }


    public List<LeaseContract> readPassedLeaseContractsByVehicleID(int vehicleID, Date valueOf) {
        return leaseContractRepo.readPassedLeaseContractsByVehicleID(vehicleID, valueOf);
    }

    /**
     * @author daniel(GuyKawaii)
     */
    public List<Optional> getRequestLeaseOptionals(WebRequest req, List<Optional> allOptionals) {
        List<Optional> leaseOptionals = new ArrayList<>();

        for (Optional optional : allOptionals) {
            if (req.getParameter(optional.getOptionalID().toString()) != null)
                leaseOptionals.add(optional);
        }

        return leaseOptionals;
    }

    public boolean hasContractOverlapForPeriod(int vehicleID, Date startDate, Date endDate) {
        return leaseContractRepo.hasContractOverlapForPeriod( vehicleID,  startDate,  endDate);
    }

    /**
     * @author daniel(GuyKawaii)
     */
    public boolean invalidStartAndEndDAte(Date startDate, Date endDate) {
        return startDate.after(endDate) || endDate.before(startDate);
    }
}
