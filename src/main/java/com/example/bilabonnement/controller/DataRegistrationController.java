package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.LeaseContract;
import com.example.bilabonnement.repository.LeaseContractRepository;
import com.example.bilabonnement.service.DamageReportService;
import com.example.bilabonnement.service.FleetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;
import java.sql.Date;

import java.util.Objects;

@Controller
public class DataRegistrationController {

    LeaseContractRepository leaseContractRepository = new LeaseContractRepository();
    DamageReportService damageReportService = new DamageReportService();
    FleetService fleetService = new FleetService();

    @GetMapping("/data-registration")
    public String registrationPage(HttpSession session, Model model) {
        return "data-registration";
    }

    @PostMapping("/makeContract")
    public String makeContract(HttpSession session, WebRequest req,int id, Model model) {

        Integer vehicleID = Integer.valueOf(req.getParameter("vehicleID"));

        LeaseContract ls = new LeaseContract(
                Date.valueOf(Objects.requireNonNull(req.getParameter("startDate"))),
                Date.valueOf(req.getParameter("endDate")),
                Double.valueOf(req.getParameter("monthlyPrice")),
                Integer.valueOf(req.getParameter("customerID")),
                vehicleID
        );
        // Throws it to the repo
        leaseContractRepository.create(ls);

        fleetService.updateState(fleetService.read(vehicleID), vehicleID);

        return "registration";
    }
}
