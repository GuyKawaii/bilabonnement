package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.Customer;
import com.example.bilabonnement.model.LeaseContract;
import com.example.bilabonnement.service.CustomerService;
import com.example.bilabonnement.service.EmployeeService;
import com.example.bilabonnement.service.FleetService;
import com.example.bilabonnement.service.LeaseContractService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;
import java.rmi.dgc.Lease;
import java.sql.Date;

import java.util.Objects;

@Controller
public class DataRegistrationController {
  LeaseContractService leaseService = new LeaseContractService();
  FleetService fleetService = new FleetService();
  CustomerService customerService = new CustomerService();
  EmployeeService employeeService = new EmployeeService();


  @GetMapping("/data-registration")
  public String registrationPage(HttpSession session, Model model) {
    model.addAttribute("leaseContracts", leaseService.readAll());

    return "data-registration";
  }

  @PostMapping("/makeContract")
  public String makeContract(HttpSession session, WebRequest req, Model model) {


    double price = Double.valueOf(req.getParameter("monthlyPrice"));
    int customerID = Integer.valueOf(req.getParameter("customerID"));//midlertidig variabel fordi den skal laves til int, ellers er det Integer?
    int vehicleID = Integer.valueOf(req.getParameter("vehicleID"));
    int employeeID = Integer.valueOf(req.getParameter("employeeID"));


    if (customerService.read(customerID) == null || fleetService.read(vehicleID) == null || employeeService.read(employeeID) == null) {
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
      model.addAttribute("leaseContracts", leaseService.readAll());
      fleetService.updateState(vehicleID);
      return "data-registration";
    }
  }

  @GetMapping("/edit-leasecontract")
  public String updateLeaseContract(@RequestParam int id, Model model) {
    LeaseContract ls = leaseService.read(Integer.valueOf(id));
    model.addAttribute("contract", ls);
    return "/edit-leasecontract";
  }

  @PostMapping("/edit-leasecontract")
  public String updateLeaseContract(@ModelAttribute LeaseContract leaseContract, WebRequest req) {
    double price = Double.valueOf(req.getParameter("monthlyPrice"));
    int customerID = Integer.valueOf(req.getParameter("customerID"));//midlertidig variabel fordi den skal laves til int, ellers er det Integer?
    int vehicleID = Integer.valueOf(req.getParameter("vehicleID"));
    int employeeID = Integer.valueOf(req.getParameter("employeeID"));


    if (customerService.read(customerID) == null || fleetService.read(vehicleID) == null || employeeService.read(employeeID) == null) {
      return "redirect:/data-registration";
    } else {
      leaseService.update(leaseContract);
      return "data-registration";
    }
  }

  @PostMapping("/delete-leasecontract")
  public String deleteDamageReport(WebRequest req) {
    int leaseID = Integer.parseInt(req.getParameter("leaseID"));
    leaseService.delete(leaseID);
    return "redirect:/data-registration";
  }
}
