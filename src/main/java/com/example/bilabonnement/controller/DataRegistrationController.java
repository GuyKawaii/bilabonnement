package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.model.LeaseContract;
import com.example.bilabonnement.model.Optional;
import com.example.bilabonnement.model.enums.EquipmentLevel;
import com.example.bilabonnement.model.enums.Role;
import com.example.bilabonnement.model.enums.State;
import com.example.bilabonnement.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;
import java.sql.Date;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.bilabonnement.model.enums.Role.ADMINISTRATION;
import static com.example.bilabonnement.model.enums.Role.DATA_REGISTRATION;
import static com.example.bilabonnement.model.enums.State.*;

@Controller
public class DataRegistrationController {
    LeaseContractService leaseService = new LeaseContractService();
    CarService carService = new CarService();
    CustomerService customerService = new CustomerService();
    EmployeeService employeeService = new EmployeeService();
    OptionalService optionalService = new OptionalService();

    // people with access to these pages
    Role[] employeeAccess = new Role[]{DATA_REGISTRATION, ADMINISTRATION};


    @GetMapping("/data-registration")
    public String registrationPage(HttpSession session, Model model) {
        // validate employee access
        if (!EmployeeService.validEmployeeRole((Role) session.getAttribute("employeeRole"), employeeAccess))
            return "redirect:/role-redirect";
        // session navbar
        model.addAttribute("employeeRole", ((Role) session.getAttribute("employeeRole")).toString());
        model.addAttribute("employeeName", session.getAttribute("employeeName"));
        model.addAttribute("employeeID", session.getAttribute("employeeID"));


        model.addAttribute("optionals", optionalService.readAll());
        model.addAttribute("leaseContracts", leaseService.readAll());
        model.addAttribute("employees", employeeService.readAll());
        model.addAttribute("customers", customerService.readAll());
        model.addAttribute("cars", carService.readAll());
        model.addAttribute("leaseContracts", leaseService.readAll());

        return "data-registration";
    }

    @PostMapping("/makeContract")
    public String makeContract(HttpSession session, WebRequest req, Model model) {

        // create leaseContract
        int leaseID = leaseService.createAndReturnID(new LeaseContract(
        Date.valueOf(req.getParameter("startDate")),
                Date.valueOf(req.getParameter("endDate")),
                Double.parseDouble(req.getParameter("monthlyPrice")),
                Integer.parseInt(req.getParameter("customerID")),
                Integer.parseInt(req.getParameter("vehicleID")),
                Integer.parseInt(req.getParameter("employeeID"))
        ));
        // get dynamic all optionals
        List<Optional> leaseOptionals = new ArrayList<>();
        for (Optional optional : optionalService.readAll()) {
            // check which optionals were added
            if (req.getParameter(optional.getOptionalID().toString()) != null)
                leaseOptionals.add(optional);
        }
        leaseService.read(leaseID);
            // add optionals
        leaseService.updateOptionals(leaseOptionals, leaseID);

        // todo add check for leasing period maybe?
        carService.updateState(Integer.parseInt(req.getParameter("vehicleID")), AT_CUSTOMER);

            return "redirect:/data-registration";
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
        if (!EmployeeService.validEmployeeRole((Role) session.getAttribute("employeeRole"), employeeAccess))
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
    public String editCar(@RequestParam int vehicleID, Model model, HttpSession session) {
        // validate employee access
        if (!EmployeeService.validEmployeeRole((Role) session.getAttribute("employeeRole"), employeeAccess))
            return "redirect:/role-redirect";

        model.addAttribute("car", carService.read(vehicleID));
        model.addAttribute("states", carService.getCarStates());
        model.addAttribute("equipmentLevels", carService.getEquipmentLevels());
        model.addAttribute("locations", carService.getEquipmentLevels());

        return "data-registrator/edit-car";
    }

    @PostMapping("/update-car-state")
    public String updateCarState(WebRequest req) {

        carService.updateState(
                Integer.parseInt(req.getParameter("vehicleID")),
                valueOf(req.getParameter("state")));

        return "redirect:/view-cars";
    }

    @PostMapping("update-car")
    public String updateCar(WebRequest req) {

        System.out.println(req.getParameter("equipmentLevel"));


        carService.update(new Car(
                Integer.parseInt(req.getParameter("vehicleID")),
                req.getParameter("chassisNumber"),
                Double.parseDouble(req.getParameter("steelPrice")),
                req.getParameter("brand"),
                req.getParameter("model"),
                EquipmentLevel.valueOf(req.getParameter("equipmentLevel")),
                Double.parseDouble(req.getParameter("registrationFee")),
                Double.parseDouble(req.getParameter("co2emission")),
                valueOf(req.getParameter("state"))
        ));

        return "redirect:/view-cars";
    }


    @PostMapping("/delete-leasecontract")
    public String deleteDamageReport(WebRequest req) {
        int leaseID = Integer.parseInt(req.getParameter("leaseID"));
        leaseService.delete(leaseID);
        return "redirect:/data-registration";
    }
}
