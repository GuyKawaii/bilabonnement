package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.LeaseContract;
import com.example.bilabonnement.service.CustomerService;
import com.example.bilabonnement.service.EmployeeService;
import com.example.bilabonnement.service.CarService;
import com.example.bilabonnement.service.LeaseContractService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;
import java.sql.Date;

import java.util.Calendar;
import java.util.Objects;

@Controller
public class DataRegistrationController {
    LeaseContractService leaseService = new LeaseContractService();
    CarService carService = new CarService();
    CustomerService customerService = new CustomerService();
    EmployeeService employeeService = new EmployeeService();


    @GetMapping("/data-registration")
    public String registrationPage(HttpSession session, Model model) {
        model.addAttribute("leaseContracts", leaseService.readAll());
        return "data-registration";
    }

    @PostMapping("/makeContract")
    public String makeContract(HttpSession session, WebRequest req, Model model) {
        double price = Double.parseDouble(req.getParameter("monthlyPrice"));
        int customerID = Integer.parseInt(req.getParameter("customerID"));//midlertidig variabel fordi den skal laves til int, ellers er det Integer?
        int vehicleID = Integer.parseInt(req.getParameter("vehicleID"));
        int employeeID = Integer.parseInt(req.getParameter("employeeID"));


        if (customerService.read(customerID) == null || carService.read(vehicleID) == null || employeeService.read(employeeID) == null) {
            return "redirect:/data-registration";
        } else {

            LeaseContract ls = new LeaseContract(
                Date.valueOf(Objects.requireNonNull(req.getParameter("startDate"))),
                Date.valueOf(req.getParameter("endDate")),
                price,
                customerID,
                vehicleID,
                employeeID
            );
            leaseService.create(ls);
            //model.addAttribute("leaseContracts", leaseService.readAll());
            carService.updateState(vehicleID);
            return "redirect:/data-registration";
        }
    }

    @GetMapping("/edit-leasecontract")
    public String updateLeaseContract(WebRequest req, Model model) { //@RequestParam int id
        int leaseID = Integer.parseInt(req.getParameter("leaseID"));
        System.out.println(leaseID);
        LeaseContract ls = leaseService.read(leaseID);
        model.addAttribute("contract", ls);
        return "edit-leasecontract";
    }

    @PostMapping("/edit")
    public String updateLease(WebRequest req, Model model) {

        System.out.println(req.getParameter("leaseID"));

        java.sql.Date startDate = Date.valueOf(req.getParameter("startDate"));
        java.sql.Date endDate = Date.valueOf(req.getParameter("endDate"));

        int id = Integer.parseInt(req.getParameter("leaseID"));
        double price = Double.parseDouble(req.getParameter("monthlyPrice"));
        int customerID = Integer.parseInt(req.getParameter("customerID"));//midlertidig variabel fordi den skal laves til int, ellers er det Integer?
        int vehicleID = Integer.parseInt(req.getParameter("vehicleID"));
        int employeeID = Integer.parseInt(req.getParameter("employeeID"));

        LeaseContract ls = new LeaseContract(id, startDate, endDate, price, customerID, vehicleID, employeeID);

        if (customerService.read(customerID) == null || carService.read(vehicleID) == null || employeeService.read(employeeID) == null) {
            return "redirect:/data-registration";
        } else {
            leaseService.update(ls);
            return "redirect:/data-registration";
        }
    }


    @PostMapping("/delete-leasecontract")
    public String deleteDamageReport(WebRequest req) {
        int leaseID = Integer.parseInt(req.getParameter("leaseID"));
        leaseService.delete(leaseID);
        return "redirect:/data-registration";
    }
}