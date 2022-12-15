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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.sql.Date;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    Role[] employeeAccess = new Role[]{DATA_REGISTRATION};

    /**
     * @author Ian(DatJustino)
     * @author Veronica(Rhod1um)
     */
    // Get LeaseContract side
    @GetMapping("/create-lease-contract")
    public String registrationPage(@RequestParam(required = false) String error, HttpSession session, Model model) {
        // validate employee access
        if (!EmployeeService.validEmployeeRole((Role) session.getAttribute("employeeRole"), employeeAccess))
            return "redirect:/role-redirect";
        // session navbar
        model.addAttribute("employeeRole", session.getAttribute("employeeRole"));
        model.addAttribute("employeeName", session.getAttribute("employeeName"));
        model.addAttribute("employeeID", session.getAttribute("employeeID"));

        // check if redirected from /make_contract because of invalid date
        if (error != null) model.addAttribute("error", error);
        else model.addAttribute("error", "na");

        // Read all possible selection choices from the database to view in the html, the cars are logically checked if
        // they are available, and if they are ready at the local datetime
        model.addAttribute("optionals", optionalService.readAll());
        model.addAttribute("leaseContracts", leaseService.readAll());
        model.addAttribute("employees", employeeService.readAllWithRole(DATA_REGISTRATION));
        model.addAttribute("customers", customerService.readAll());
        model.addAttribute("UnleasedReadyCars", carService.readAllUnleasedOnDateWithState(Date.valueOf(LocalDate.now()), READY));
        model.addAttribute("date", LocalDate.now());

        return "data-registrator/create-lease-contract";
    }


    /**
     * @author daniel(GuyKawaii)
     * @author Mikas(CodeClod)
     */
    // Make LeaseContract Postmapping
    @PostMapping("/make_contract")
    public String makeContract(WebRequest req) {
        Date startDate = Date.valueOf(req.getParameter("startDate"));
        Date endDate = Date.valueOf(req.getParameter("endDate"));

        // Date validation
        if (leaseService.invalidStartAndEndDAte(startDate, endDate))
            return ("redirect:/create-lease-contract" + "?error=dateError");
        // Contract overlap validation
        if (leaseService.hasContractOverlapForPeriod(
                Integer.parseInt(req.getParameter("vehicleID")),
                startDate,
                endDate)) {
            return ("redirect:/create-lease-contract" + "?error=activeContractError");
        }

        // get leaseOptionals
        List<Optional> leaseOptionals = leaseService.getRequestLeaseOptionals(req, optionalService.readAll());

        // create leaseContract
        leaseService.create(new LeaseContract(
                startDate,
                endDate,
                Double.parseDouble(req.getParameter("monthlyPrice").replace(',', '.')),
                Integer.parseInt(req.getParameter("customerID")),
                Integer.parseInt(req.getParameter("vehicleID")),
                Integer.parseInt(req.getParameter("employeeID")),
                leaseOptionals
        ));

        // change state if active contract
        if (startDate.before(Date.valueOf(LocalDate.now())) & endDate.after(Date.valueOf(LocalDate.now()))) {
            carService.updateState(Integer.parseInt(req.getParameter("vehicleID")), AT_CUSTOMER);
        }

        return "redirect:/create-lease-contract";
    }

    /**
     * @author daniel(GuyKawaii)
     * @author Ian(DatJustino)
     * @author Veronica(Rhod1um)
     */
    @GetMapping("/edit-lease-contract")
    public String updateLeaseContract(WebRequest req, Model model, HttpSession session) { //@RequestParam int id
        int leaseID = Integer.parseInt(req.getParameter("leaseID"));

        // session navbar
        model.addAttribute("employeeRole", session.getAttribute("employeeRole"));
        model.addAttribute("employeeName", session.getAttribute("employeeName"));
        model.addAttribute("employeeID", session.getAttribute("employeeID"));


        model.addAttribute("contract", leaseService.read(leaseID));
        model.addAttribute("leaseNonOptionals", optionalService.readNonLeaseOptionals(leaseID));
        model.addAttribute("leaseOptionals", optionalService.readLeaseOptionals(leaseID));
        model.addAttribute("cars", carService.readAll());
        model.addAttribute("vehicleID", carService.readAll());
        model.addAttribute("customers", customerService.readAll());

        return "edit-lease-contract";
    }

    /**
     * @author daniel(GuyKawaii)
     * @author Veronica(Rhod1um)
     */
    @PostMapping("/update-lease-contract")
    public String updateLease(WebRequest req, Model model) {
        int leaseID = Integer.parseInt(req.getParameter("leaseID"));

        // get optionals selected
        List<Optional> leaseOptionals = leaseService.getRequestLeaseOptionals(req, optionalService.readAll());
        // update references
        leaseService.updateOptionals(leaseOptionals, leaseID);

        // update contract
        leaseService.update(new LeaseContract(
                leaseID,
                Date.valueOf(req.getParameter("startDate")),
                Date.valueOf(req.getParameter("endDate")),
                Double.parseDouble(req.getParameter("monthlyPrice")),
                Integer.parseInt(req.getParameter("customerID")),
                Integer.parseInt(req.getParameter("vehicleID")),
                Integer.parseInt(req.getParameter("employeeID")),
                leaseOptionals)
        );
        return "redirect:/create-lease-contract";
    }

    /**
     * @author daniel(GuyKawaii)
     */
    @GetMapping("/view-cars")
    public String viewCars(Model model, HttpSession session) {
        // validate employee access
        if (!EmployeeService.validEmployeeRole((Role) session.getAttribute("employeeRole"), employeeAccess))
            return "redirect:/role-redirect";
        // session navbar
        model.addAttribute("employeeRole", session.getAttribute("employeeRole"));
        model.addAttribute("employeeName", session.getAttribute("employeeName"));

        model.addAttribute("unleasedCars", carService.readAllUnleasedOnDate(Date.valueOf(LocalDate.now())));
        model.addAttribute("leasedCars", carService.readAllLeasedOnDate(Date.valueOf(LocalDate.now())));
        model.addAttribute("states", employeeService.getEmployeeStateSelect(DATA_REGISTRATION));
        return "data-registrator/view-cars";
    }

    /**
     * @author daniel(GuyKawaii)
     */
    @GetMapping("/edit-car")
    public String editCar(@RequestParam int vehicleID, Model model, HttpSession session) {
        // validate employee access
        if (!EmployeeService.validEmployeeRole((Role) session.getAttribute("employeeRole"), employeeAccess))
            return "redirect:/role-redirect";
        model.addAttribute("employeeRole", session.getAttribute("employeeRole"));
        model.addAttribute("employeeName", session.getAttribute("employeeName"));
        model.addAttribute("employeeID", session.getAttribute("employeeID"));


        model.addAttribute("car", carService.read(vehicleID));
        model.addAttribute("states", carService.getCarStates());
        model.addAttribute("equipmentLevels", carService.getEquipmentLevels());
        model.addAttribute("locations", carService.getEquipmentLevels());

        return "data-registrator/edit-car";
    }

    /**
     * @author daniel(GuyKawaii)
     */
    @PostMapping("/update-car-state")
    public String updateCarState(WebRequest req) {

        carService.updateState(
                Integer.parseInt(req.getParameter("vehicleID")),
                valueOf(req.getParameter("state")));

        return "redirect:/view-cars";
    }

    /**
     * @author daniel(GuyKawaii)
     */
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

    /**
     * @author Ian(DatJustino)
     * @author Veronica(Rhod1um)
     */
    @PostMapping("/delete-lease-contract")
    public String deleteDamageReport(WebRequest req) {
        int leaseID = Integer.parseInt(req.getParameter("leaseID"));
        leaseService.delete(leaseID);
        return "redirect:/create-lease-contract";
    }

    /**
     * @author daniel(GuyKawaii)
     */
    @GetMapping("/edit-customer")
    public String editCustomer(Model model, @RequestParam int customerID, HttpSession session) {
        // validate employee access
        if (!EmployeeService.validEmployeeRole((Role) session.getAttribute("employeeRole"), employeeAccess))
            return "redirect:/role-redirect";
        // session navbar
        model.addAttribute("employeeRole", session.getAttribute("employeeRole"));
        model.addAttribute("employeeName", session.getAttribute("employeeName"));

        model.addAttribute("customer", customerService.read(customerID));

        return "edit-customer";
    }


}
