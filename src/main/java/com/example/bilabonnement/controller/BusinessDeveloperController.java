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
        model.addAttribute("employee_name", session.getAttribute("employee_name"));
        model.addAttribute("employee_id", session.getAttribute("employee_id"));

        List<LeaseContract> leaseContracts = leaseContractService.readAll();
        String name = (String) session.getAttribute("employee_name");
        int id = (int) session.getAttribute("employee_id");
        String email = (String) session.getAttribute("employee_email");
        double currentIncome = leaseContractService.getCurrentIncome();

        List<Car> leasedCars = carService.readAllLeasedOnDate(Date.valueOf(LocalDate.now()));
        System.out.println(Date.valueOf(LocalDate.now()));
        int numOfLeasedCars = carService.getLeasedCarsAmountOnDate(Date.valueOf(LocalDate.now()));

        // TODO Fix this after asking for definitive session/cookie usage help
        // Blocked 'cause of the undefined

        model.addAttribute("name_of_user", name);
        model.addAttribute("id_of_user", id);
        model.addAttribute("email_of_user", email);
        model.addAttribute("leased_cars", leasedCars);
        model.addAttribute("number_of_leased_cars", numOfLeasedCars);
        model.addAttribute("current_income", currentIncome);
        model.addAttribute("unleased_cars", carService.readAllUnleasedOnDate(Date.valueOf(LocalDate.now())));
        model.addAttribute("leased_cars", carService.readAllLeasedOnDate(Date.valueOf(LocalDate.now())));

        return "business-developer";
    }

}
