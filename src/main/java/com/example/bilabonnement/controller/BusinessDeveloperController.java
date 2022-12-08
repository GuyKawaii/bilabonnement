package com.example.bilabonnement.controller;


import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.model.enums.Role;
import com.example.bilabonnement.service.CarService;
import com.example.bilabonnement.model.Employee;
import com.example.bilabonnement.model.LeaseContract;;
import com.example.bilabonnement.service.EmployeeService;
import com.example.bilabonnement.service.LeaseContractService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static com.example.bilabonnement.model.enums.Role.*;

@Controller
public class BusinessDeveloperController {
    CarService carService = new CarService();
    LeaseContractService leaseContractService = new LeaseContractService();

    // people with access to these pages
    Role[] employeeAccess = new Role[]{BUSINESS_DEVELOPER, ADMINISTRATION};

    @GetMapping("/finance")
    public String getCarsLeased(HttpSession session, Model model) {
        // validate employee access
        if (!EmployeeService.validEmployeeRole((Role) session.getAttribute("employeeRole"), employeeAccess))
            return "redirect:/role-redirect";
        // session navbar
        model.addAttribute("employeeRole", ((Role) session.getAttribute("employeeRole")).toString());
        model.addAttribute("employeeName", session.getAttribute("employeeName"));
        model.addAttribute("employeeID", session.getAttribute("employeeID"));

        List<LeaseContract> leaseContracts = leaseContractService.readAll();
        String name = (String) session.getAttribute("employeeName");
        int id = (int) session.getAttribute("employeeID");
        String email = (String) session.getAttribute("employeeEmail");
        double currentIncome = leaseContractService.getCurrentIncome();

        List<Car> leasedCars = carService.readAllLeasedOnDate(Date.valueOf(LocalDate.now()));
        System.out.println(Date.valueOf(LocalDate.now()));
        int numOfLeasedCars = carService.getLeasedCarsAmountOnDate(Date.valueOf(LocalDate.now()));

        // TODO Fix this after asking for definitive session/cookie usage help
        // Blocked 'cause of the undefined

        model.addAttribute("nameOfUser", name);
        model.addAttribute("idOfUser", id);
        model.addAttribute("emailOfUser", email);
        model.addAttribute("leasedCars", leasedCars);
        model.addAttribute("numberOfLeasedCars", numOfLeasedCars);
        model.addAttribute("currentIncome", currentIncome);
        model.addAttribute("unleasedCars", carService.readAllUnleasedOnDate(Date.valueOf(LocalDate.now())));
        model.addAttribute("leasedCars", carService.readAllLeasedOnDate(Date.valueOf(LocalDate.now())));

        return "business-developer";
    }

}
