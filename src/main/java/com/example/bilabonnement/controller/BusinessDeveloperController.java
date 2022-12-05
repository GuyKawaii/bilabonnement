package com.example.bilabonnement.controller;


import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.service.CarService;
import com.example.bilabonnement.model.Employee;
import com.example.bilabonnement.model.LeaseContract;;
import com.example.bilabonnement.service.LeaseContractService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Controller
public class BusinessDeveloperController {
    CarService carService = new CarService();
    LeaseContractService leaseContractService = new LeaseContractService();

    @GetMapping("finance")
    public String getCarsLeased(HttpSession session, Model model) {

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

        return "business-developer";
    }

}
