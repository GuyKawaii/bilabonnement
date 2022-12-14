package com.example.bilabonnement.controller;


import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.model.enums.Role;
import com.example.bilabonnement.service.CarService;
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

    // people with access to these pages todo change to only use one Role for check
    Role[] employeeAccess = new Role[]{BUSINESS_DEVELOPER};

    /**
     * @author daniel(GuyKawaii)
     * @author Mikas(CodeClod)
     */
    @GetMapping("/finance")
    public String getCarsLeased(HttpSession session, Model model) {
        // validate employee access
        if (!EmployeeService.validEmployeeRole((Role) session.getAttribute("employeeRole"), employeeAccess))
            return "redirect:/role-redirect";
        // session navbar
        model.addAttribute("employeeRole", session.getAttribute("employeeRole"));
        model.addAttribute("employeeName", session.getAttribute("employeeName"));
        model.addAttribute("employeeID", session.getAttribute("employeeID"));

        // other attributes
        model.addAttribute("leasedCars", carService.readAllLeasedOnDate(Date.valueOf(LocalDate.now())));
        model.addAttribute("numberOfLeasedCars", carService.getLeasedCarsAmountOnDate(Date.valueOf(LocalDate.now())));
        model.addAttribute("activeContractsCount", (int) leaseContractService.activeLeaseContractsByDate(Date.valueOf(LocalDate.now())));
        model.addAttribute("currentIncome", leaseContractService.getCurrentIncome());
        model.addAttribute("unleased_cars", carService.readAllUnleasedOnDate(Date.valueOf(LocalDate.now())));
        model.addAttribute("leased_cars", carService.readAllLeasedOnDate(Date.valueOf(LocalDate.now())));

        return "buisness-developer/business-developer";
    }
}
