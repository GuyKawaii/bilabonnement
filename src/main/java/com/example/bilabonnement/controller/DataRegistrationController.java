package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.model.LeaseContract;
import com.example.bilabonnement.model.Optional;
import com.example.bilabonnement.model.enums.EquipmentLevel;
import com.example.bilabonnement.model.enums.Role;
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
    // people with access to these pages
    Role[] employeeAccess = new Role[]{DATA_REGISTRATION, ADMINISTRATION};


    @GetMapping("/create-lease-contract")
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
        model.addAttribute("date", LocalDate.now());
        model.addAttribute("leaseOptionalAmounts", optionalService.readLeaseOptionalAmounts());

        return "data-registrator/create-lease-contract";
    }

    @PostMapping("/make_contract")
    public String makeContract(HttpSession session, WebRequest req, Model model) {
        Date startDate = Date.valueOf(req.getParameter("startDate"));
        Date endDate = Date.valueOf(req.getParameter("endDate"));

        // todo return to create-damage-report if date dates are inverse or period overlaps with other contracts
        // todo add env variable so people know

        // add a check method in service layer that returns a boolean on startdate/enddate validity.
        // and then proceeds to redirect if that's the case`?
        // ...nvm

        if (startDate.after(endDate) || startDate.before(endDate)) {
            return "redirect:/create-lease-contract";
        }

        // create leaseContract
        int leaseID = leaseService.createAndReturnID(new LeaseContract(
                startDate,
                endDate,
                Double.parseDouble(req.getParameter("monthlyPrice").replace(',', '.')),
                Integer.parseInt(req.getParameter("customerID")),
                Integer.parseInt(req.getParameter("vehicleID")),
                Integer.parseInt(req.getParameter("employeeID"))
        ));

        // get leaseOptionals
        List<Optional> leaseOptionals = leaseService.getRequestLeaseOptionals(req, optionalService.readAll());
        // update leaseOptionals
        leaseService.updateOptionals(leaseOptionals, leaseID);

        // change state if active contract
        if (startDate.after(Date.valueOf(LocalDate.now())) & endDate.before(Date.valueOf(LocalDate.now()))) {
            carService.updateState(Integer.parseInt(req.getParameter("vehicleID")), AT_CUSTOMER);
        }

        return "redirect:/create-lease-contract";
    }

    @GetMapping("/edit-lease-contract")
    public String updateLeaseContract(WebRequest req, Model model) { //@RequestParam int id
        int leaseID = Integer.parseInt(req.getParameter("leaseID"));

        model.addAttribute("contract", leaseService.read(leaseID));
        model.addAttribute("leaseNonOptionals", optionalService.readNonLeaseOptionals(leaseID));
        model.addAttribute("leaseOptionals", optionalService.readLeaseOptionals(leaseID));
        model.addAttribute("cars", carService.readAll());
        model.addAttribute("vehicleID", carService.readAll());
        model.addAttribute("customers", customerService.readAll());

        return "edit-lease-contract";
    }

    @PostMapping("/update-lease-contract")
    public String updateLease(WebRequest req, Model model) {
        int leaseID = Integer.parseInt(req.getParameter("leaseID"));

        // update contract
        leaseService.update(new LeaseContract(
                leaseID,
                Date.valueOf(req.getParameter("startDate")),
                Date.valueOf(req.getParameter("endDate")),
                Double.parseDouble(req.getParameter("monthlyPrice")),
                Integer.parseInt(req.getParameter("customerID")),
                Integer.parseInt(req.getParameter("vehicleID")),
                Integer.parseInt(req.getParameter("employeeID")))
        );

        // get optionals selected
        List<Optional> leaseOptionals = leaseService.getRequestLeaseOptionals(req, optionalService.readAll());
        // update references
        leaseService.updateOptionals(leaseOptionals, leaseID);

        return "redirect:/create-lease-contract";
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
        model.addAttribute("states", employeeService.getEmployeeStateSelect(DATA_REGISTRATION));


        return "data-registrator/view-cars";
    }

    @GetMapping("/edit-car")
    public String editCar(@RequestParam int vehicleID, Model model, HttpSession session) {
        // validate employee access
        if (!EmployeeService.validEmployeeRole((Role) session.getAttribute("employeeRole"), employeeAccess))
            return "redirect:/role-redirect";
        model.addAttribute("employeeRole", ((Role) session.getAttribute("employeeRole")).toString());
        model.addAttribute("employeeName", session.getAttribute("employeeName"));
        model.addAttribute("employeeID", session.getAttribute("employeeID"));


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


    @PostMapping("/delete-lease-contract")
    public String deleteDamageReport(WebRequest req) {
        int leaseID = Integer.parseInt(req.getParameter("leaseID"));
        leaseService.delete(leaseID);
        return "redirect:/create-lease-contract";
    }


}
