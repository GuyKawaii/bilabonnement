package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.LeaseContract;
import com.example.bilabonnement.repository.LeaseContractRepository;
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

    @GetMapping("/registration")
    public String registrationPage(HttpSession session) {

        return "registration";
    }

    @PostMapping("/makeContract")
    public String makeContract(HttpSession session, WebRequest req, Model model) {

        LeaseContract ls = new LeaseContract(
                Date.valueOf(Objects.requireNonNull(req.getParameter("startDate"))),
                Date.valueOf(req.getParameter("endDate")),
                Double.valueOf(req.getParameter("monthlyPrice")),
                Integer.valueOf(req.getParameter("customerID")),
                Integer.valueOf(req.getParameter("vehicleID"))
        );
        // Throws it to the repo
        leaseContractRepository.create(ls);


        return "registration";
    }
}
