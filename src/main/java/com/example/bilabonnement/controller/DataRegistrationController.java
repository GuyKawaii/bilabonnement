package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.LeaseContract;
import com.example.bilabonnement.service.FleetService;
import com.example.bilabonnement.service.LeaseContractService;
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
    LeaseContractService leaseService = new LeaseContractService();
    FleetService fleetService = new FleetService();

    @GetMapping("/data-registration")
    public String registrationPage(HttpSession session, Model model) {
        model.addAttribute("leaseContracts", leaseService.readAll());

        return "data-registration";
    }

    @PostMapping("/makeContract")
    public String makeContract(HttpSession session, WebRequest req, Model model) {
        int vehicleID = Integer.valueOf(req.getParameter("vehicleID"));
        int customerID = Integer.valueOf(req.getParameter("customerID"));//midlertidig variabel fordi den skal laves til int, ellers er det Integer?

        LeaseContract ls = new LeaseContract(
                Date.valueOf(Objects.requireNonNull(req.getParameter("startDate"))),
                Date.valueOf(req.getParameter("endDate")),
                Double.valueOf(req.getParameter("monthlyPrice")),
                customerID,
                vehicleID,
                101

        );
        leaseService.create(ls);
        model.addAttribute("leaseContracts", leaseService.readAll());
        fleetService.updateState(vehicleID);
        return "data-registration";
    }

    @PostMapping("/delete-leasecontract")
    public String deleteDamageReport(WebRequest req) {
        int leaseID = Integer.parseInt(req.getParameter("leaseID"));
        leaseService.delete(leaseID);
        return "redirect:/delete-leasecontract";
    }
}
