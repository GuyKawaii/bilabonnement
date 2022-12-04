package com.example.bilabonnement.controller;


import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.service.CarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class BusinessDeveloperController {
    CarService carService = new CarService();
    @GetMapping("carsLeased")
    public String getCarsLeased(HttpSession session, Model model) {
       List<Car> leasedCars = carService.getLeasedCarsList();
       int numOfLeasedCars = carService.getLeasedCarsAmount();

       model.addAttribute("leasedCars",leasedCars);
       model.addAttribute("numberOfLeasedCars",numOfLeasedCars);

        return "business-developer";
    }

    @GetMapping("income")
    public String showIncome() {

        return "incomeData";
    }

}
