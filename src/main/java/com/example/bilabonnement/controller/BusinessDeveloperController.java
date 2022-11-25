package com.example.bilabonnement.controller;


import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.service.FleetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.List;

@Controller
public class BusinessDeveloperController {
    FleetService fleetService = new FleetService();
    @GetMapping("carsLeased")
    public String getCarsLeased(HttpSession session, Model model) {
       List<Car> leasedCars = fleetService.getLeasedCarsList();
       int numOfLeasedCars = fleetService.getLeasedCarsAmount();

       model.addAttribute("leasedCars",leasedCars);
       model.addAttribute("numberOfLeasedCars",numOfLeasedCars);

        return "business-developer";
    }

    @GetMapping("income")
    public String showIncome() {

        return "incomeData";
    }

}
