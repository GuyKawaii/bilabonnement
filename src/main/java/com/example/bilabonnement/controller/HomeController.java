package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.Customer;
import com.example.bilabonnement.model.Employee;
import com.example.bilabonnement.model.enums.Role;
import com.example.bilabonnement.service.CustomerService;
import com.example.bilabonnement.service.EmployeeService;
import com.example.bilabonnement.service.LeaseContractService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;

import java.sql.Date;
import java.time.LocalDate;

import static com.example.bilabonnement.model.enums.Role.*;

@Controller
public class HomeController {

    EmployeeService employeeService = new EmployeeService();
    LeaseContractService leaseService = new LeaseContractService();

    // people with access to these pages
    Role[] employeeAccess = new Role[]{DATA_REGISTRATION, DAMAGE_REPORTER, BUSINESS_DEVELOPER};

    /**
     * @author Veronica(Rhod1um)
     */
    // login page
    @GetMapping("/")
    public String index(HttpSession session) {

        // fresh session
        if (null == session.getAttribute("employeeID")) return "index";

        // ongoing session
        return "redirect:/role-redirect";
    }

    /**
     * @author daniel(GuyKawaii)
     * @author mikas(CodeClod)
     */
    @PostMapping("/login")
    public String login(WebRequest request, HttpSession session, Model model) {

        // check login credentials
        Employee employee = employeeService.login(request.getParameter("email"), request.getParameter("password"));

        // invalid password or employee
        if (employee == null) {
            model.addAttribute("invalidCredentials", true);
            return "index";

        } else {
            // add session keys
            session.setAttribute("employeeName", employee.getName());
            session.setAttribute("employeeID", employee.getEmployeeID());
            session.setAttribute("employeeRole", employee.getRole());
            session.setAttribute("employeeEmail", employee.getEmail());

            return "redirect:/";
        }
    }

    /**
     * @author Veronica(Rhod1um)
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    /**
     * @author daniel(GuyKawaii)
     */
    @GetMapping("/role-redirect")
    public String roleRedirect(HttpSession session) {
        Role role = (Role) session.getAttribute("employeeRole");

        // early return for null (switch cant use null)
        if (role == null) return "redirect:/logout";

        // role redirect
        switch (role) {
            case DATA_REGISTRATION -> {
                return "redirect:/create-lease-contract";
            }
            case DAMAGE_REPORTER -> {
                return "redirect:/create-damage-report";
            }
            case BUSINESS_DEVELOPER -> {
                return "redirect:/finance";
            }
            default -> {
                throw new RuntimeException("cannot switch on null");
            }
        }
    }

    // mappings used for multiple employees
    /**
     * @author daniel(GuyKawaii)
     */
    @GetMapping("/car-lease-contracts")
    public String carContracts(@RequestParam int vehicleID, Model model, HttpSession session) {
        // validate employee access
        if (!EmployeeService.validEmployeeRole((Role) session.getAttribute("employeeRole"), employeeAccess))
            return "redirect:/role-redirect";
        // session navbar
        model.addAttribute("employeeRole", session.getAttribute("employeeRole"));
        model.addAttribute("employeeName", session.getAttribute("employeeName"));
        model.addAttribute("employeeID", session.getAttribute("employeeID"));

        model.addAttribute("upcoming_contracts", leaseService.readUpcomingLeaseContractsByVehicleID(vehicleID, Date.valueOf(LocalDate.now())));
        model.addAttribute("active_contracts", leaseService.readActiveLeaseContracts(vehicleID, Date.valueOf(LocalDate.now())));
        model.addAttribute("passed_contracts", leaseService.readPassedLeaseContractsByVehicleID(vehicleID, Date.valueOf(LocalDate.now())));

        return "car-lease-contracts";
    }


   }
