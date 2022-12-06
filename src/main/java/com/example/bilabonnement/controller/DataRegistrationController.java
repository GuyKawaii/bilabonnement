package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.Customer;
import com.example.bilabonnement.model.LeaseContract;
import com.example.bilabonnement.model.enums.Role;
import com.example.bilabonnement.model.enums.State;
import com.example.bilabonnement.service.CustomerService;
import com.example.bilabonnement.service.EmployeeService;
import com.example.bilabonnement.service.CarService;
import com.example.bilabonnement.service.LeaseContractService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;
import java.sql.Date;

import java.time.LocalDate;
import java.util.Objects;

import static com.example.bilabonnement.model.enums.Role.ADMINISTRATION;
import static com.example.bilabonnement.model.enums.Role.DATA_REGISTRATION;

@Controller
public class DataRegistrationController {
    LeaseContractService leaseService = new LeaseContractService();
    CarService carService = new CarService();
    CustomerService customerService = new CustomerService();
    EmployeeService employeeService = new EmployeeService();


    @GetMapping("/data-registration")
    public String registrationPage(HttpSession session, Model model) {
        // validate employee access
        if (!EmployeeService.validEmployeeRole((Role) session.getAttribute("employeeRole"), new Role[]{DATA_REGISTRATION, ADMINISTRATION}))
            return "redirect:/role-redirect";
        // session navbar
        model.addAttribute("employeeRole", ((Role) session.getAttribute("employeeRole")).toString());
        model.addAttribute("employeeName", session.getAttribute("employeeName"));
        model.addAttribute("employeeID", session.getAttribute("employeeID"));


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
                Date.valueOf(req.getParameter("startDate")),
                Date.valueOf(req.getParameter("endDate")),
                price,
                customerID,
                vehicleID,
                employeeID
            );
            leaseService.create(ls);
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

    @GetMapping("/view-cars")
    public String viewCars(Model model, HttpSession session) {
        // validate employee access
        if (!EmployeeService.validEmployeeRole((Role) session.getAttribute("employeeRole"), new Role[]{DATA_REGISTRATION, ADMINISTRATION}))
            return "redirect:/role-redirect";
        // session navbar
        model.addAttribute("employeeRole", ((Role) session.getAttribute("employeeRole")).toString());
        model.addAttribute("employeeName", session.getAttribute("employeeName"));

        model.addAttribute("unleasedCars", carService.readAllUnleasedOnDate(Date.valueOf(LocalDate.now())));
        model.addAttribute("leasedCars", carService.readAllLeasedOnDate(Date.valueOf(LocalDate.now())));
        model.addAttribute("states", carService.getCarStates());


        return "data-registrator/view-cars";
    }

    @GetMapping("/edit-car")
    public String editCar(@RequestParam int vehicleID, Model model) {

        model.addAttribute("car", carService.read(vehicleID));
        model.addAttribute("states", carService.getCarStates());

        return "data-registrator/edit-car";
    }

    @PostMapping("/update-car-state")
    public String updateCarState(WebRequest req) {

        carService.updateState(
                Integer.parseInt(req.getParameter("vehicleID")),
                State.valueOf(req.getParameter("state")));

        return "redirect:/view-cars";
    }


    @PostMapping("/delete-leasecontract")
    public String deleteDamageReport(WebRequest req) {
        int leaseID = Integer.parseInt(req.getParameter("leaseID"));
        leaseService.delete(leaseID);
        return "redirect:/data-registration";
    }
}
