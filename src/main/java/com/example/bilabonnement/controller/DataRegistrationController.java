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
    double price = Double.valueOf(req.getParameter("monthlyPrice"));
    int customerID = Integer.valueOf(req.getParameter("customerID"));//midlertidig variabel fordi den skal laves til int, ellers er det Integer?
    int vehicleID = Integer.valueOf(req.getParameter("vehicleID"));
    int employeeID = Integer.valueOf(req.getParameter("employeeID"));


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
      model.addAttribute("leaseContracts", leaseService.readAll());
      carService.updateState(vehicleID);
      return "data-registration";
    }
  }

  @GetMapping("/edit-leasecontract")
  public String updateLeaseContract(@RequestParam int id, Model model) {
    LeaseContract ls = leaseService.read(id);
    model.addAttribute("contract", ls);
    return "edit-leasecontract";
  }

  @GetMapping("/edit")
  public String updateLeaseContract(WebRequest req, Model model) {
    Date startDate = (Date) model.getAttribute("startDate");
    Date endDate = (Date) model.getAttribute("endDate");

   /* Date startDate = Date.valueOf(req.getParameter("startDate"));
    Date endDate = Date.valueOf(req.getParameter("endDate"));
   */
    //Double price = model.getAttribute("monthlyPrice");
//    double price = model.;  //(req.getParameter("monthlyPrice")); todo changed to comment to edit file
    int customerID = 201; //Integer.parseInt(req.getParameter("customerID"));//midlertidig variabel fordi den skal laves til int, ellers er det Integer?
    int vehicleID = 501; //Integer.parseInt(req.getParameter("vehicleID"));
    int employeeID = 101; // Integer.parseInt(req.getParameter("employeeID"));

    if (customerService.read(customerID) == null || carService.read(vehicleID) == null || employeeService.read(employeeID) == null) {
      return "redirect:/data-registration";
    } else {
//      LeaseContract ls = new LeaseContract(startDate, endDate, price, customerID, vehicleID, employeeID); todo changed to comment to edit file

//      leaseService.update(ls); todo changed to comment to edit file
      return "data-registration";
    }
  }
    /*

    ------------USING WEB REQUEST.---------------
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

*/




  @PostMapping("/delete-leasecontract")
  public String deleteDamageReport(WebRequest req) {
    int leaseID = Integer.parseInt(req.getParameter("leaseID"));
    leaseService.delete(leaseID);
    return "redirect:/data-registration";
  }
}
